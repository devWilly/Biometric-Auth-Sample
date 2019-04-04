package com.devwilly.biometricex.auth

import android.annotation.TargetApi
import android.content.Context
import android.os.Build
import android.security.keystore.KeyGenParameterSpec
import android.security.keystore.KeyPermanentlyInvalidatedException
import android.security.keystore.KeyProperties
import android.support.annotation.NonNull
import android.support.v4.hardware.fingerprint.FingerprintManagerCompat
import android.support.v4.os.CancellationSignal
import com.devwilly.biometricex.BiometricCallback
import com.devwilly.biometricex.BiometricDialogV23
import com.devwilly.biometricex.R
import java.io.IOException
import java.security.*
import java.security.cert.CertificateException
import java.util.*
import javax.crypto.Cipher
import javax.crypto.KeyGenerator
import javax.crypto.NoSuchPaddingException
import javax.crypto.SecretKey

/**
 * Created by Willy on 2019/4/4.
 */

@TargetApi(Build.VERSION_CODES.M)
open class BiometricManagerV23(
        @NonNull private val context: Context,
        private val title: String,
        private val subTitle: String,
        private val description: String,
        private val negativeButtonText: String) {

    private val KEY_NAME = UUID.randomUUID().toString()

    private var keyStore: KeyStore? = null
    private var cipher: Cipher? = null
    private var cryptoObject: FingerprintManagerCompat.CryptoObject? = null
    private var biometricDialogV23: BiometricDialogV23? = null

    fun displayBiometricPromptV23(@NonNull callback: BiometricCallback) {
        generateKey()

        if (initCipher()) {
            cryptoObject = FingerprintManagerCompat.CryptoObject(cipher!!)

            val fingerprintManagerCompat = FingerprintManagerCompat.from(context)
            fingerprintManagerCompat.authenticate(cryptoObject, 0, CancellationSignal(),
                    object : FingerprintManagerCompat.AuthenticationCallback() {
                        override fun onAuthenticationSucceeded(result: FingerprintManagerCompat.AuthenticationResult?) {
                            super.onAuthenticationSucceeded(result)
                            dismissDialog()
                            callback.onAuthSuccessful()
                        }

                        override fun onAuthenticationError(errMsgId: Int, errString: CharSequence?) {
                            super.onAuthenticationError(errMsgId, errString)
                            updateStatus(errString.toString())
                            callback.onAuthError(errMsgId, errString)
                        }

                        override fun onAuthenticationFailed() {
                            super.onAuthenticationFailed()
                            updateStatus(context.getString(R.string.biometric_failed))
                            callback.onAuthFailed()
                        }

                        override fun onAuthenticationHelp(helpMsgId: Int, helpString: CharSequence?) {
                            super.onAuthenticationHelp(helpMsgId, helpString)
                            updateStatus(helpString.toString())
                            callback.onAuthHelp(helpMsgId, helpString)
                        }
            }, null)

            displayBiometricDialog(callback)
        }
    }

    private fun dismissDialog() {
        biometricDialogV23?.dismiss()
    }

    private fun updateStatus(status: String) {
        biometricDialogV23?.updateStatus(status)
    }

    private fun displayBiometricDialog(callback: BiometricCallback) {
        biometricDialogV23 = BiometricDialogV23(context, callback)
        biometricDialogV23?.apply {
            setTitle(title)
            setSubtitle(subTitle)
            setDescription(description)
            setNegativeButtonText(negativeButtonText)
        }?.show()
    }

    private fun generateKey() {
        try {

            keyStore = KeyStore.getInstance("AndroidKeyStore")
            keyStore?.load(null)

            val keyGenerator = KeyGenerator.getInstance(KeyProperties.KEY_ALGORITHM_AES, "AndroidKeyStore")
            keyGenerator.init(KeyGenParameterSpec.Builder(KEY_NAME, KeyProperties.PURPOSE_ENCRYPT or KeyProperties.PURPOSE_DECRYPT)
                    .setBlockModes(KeyProperties.BLOCK_MODE_CBC)
                    .setUserAuthenticationRequired(true)
                    .setEncryptionPaddings(KeyProperties.ENCRYPTION_PADDING_PKCS7)
                    .build())

            keyGenerator.generateKey()

        } catch (exc: KeyStoreException) {
            exc.printStackTrace()
        } catch (exc: NoSuchAlgorithmException) {
            exc.printStackTrace()
        } catch (exc: NoSuchProviderException) {
            exc.printStackTrace()
        } catch (exc: InvalidAlgorithmParameterException) {
            exc.printStackTrace()
        } catch (exc: CertificateException) {
            exc.printStackTrace()
        } catch (exc: IOException) {
            exc.printStackTrace()
        }
    }

    private fun initCipher(): Boolean {
        try {
            cipher = Cipher.getInstance(
                    KeyProperties.KEY_ALGORITHM_AES + "/"
                            + KeyProperties.BLOCK_MODE_CBC + "/"
                            + KeyProperties.ENCRYPTION_PADDING_PKCS7)
        } catch (e: NoSuchAlgorithmException) {
            throw RuntimeException("Failed to get Cipher", e)
        } catch (e: NoSuchPaddingException) {
            throw RuntimeException("Failed to get Cipher", e)
        }

        try {
            keyStore?.load(null)
            val key = keyStore?.getKey(KEY_NAME, null) as SecretKey
            cipher?.init(Cipher.ENCRYPT_MODE, key)
            return true
        } catch (e: KeyPermanentlyInvalidatedException) {
            return false
        } catch (e: KeyStoreException) {

            throw java.lang.RuntimeException("Failed to init Cipher", e)
        } catch (e: CertificateException) {
            throw java.lang.RuntimeException("Failed to init Cipher", e)
        } catch (e: UnrecoverableKeyException) {
            throw java.lang.RuntimeException("Failed to init Cipher", e)
        } catch (e: IOException) {
            throw java.lang.RuntimeException("Failed to init Cipher", e)
        } catch (e: NoSuchAlgorithmException) {
            throw java.lang.RuntimeException("Failed to init Cipher", e)
        } catch (e: InvalidKeyException) {
            throw java.lang.RuntimeException("Failed to init Cipher", e)
        }
    }
}