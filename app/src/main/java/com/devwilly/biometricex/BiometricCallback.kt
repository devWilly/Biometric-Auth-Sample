package com.devwilly.biometricex

/**
 * Created by Willy on 2019/4/4.
 */
interface BiometricCallback {

    fun onSdkVersionNotSupported()

    fun onBiometricAuthNotSupported()

    fun onBiometricAuthNotAvailable()

    fun onBiometricAuthPermissionNotGranted()

    fun onBiometricAuthInternalError(error: String)

    fun onAuthSuccessful()

    fun onAuthError(errorCode: Int, errString: CharSequence?)

    fun onAuthFailed()

    fun onAuthHelp(helpCode: Int, helpString: CharSequence?)

    fun onAuthCancelled()
}