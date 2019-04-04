package com.devwilly.biometricex.auth

import android.hardware.biometrics.BiometricPrompt
import android.os.Build
import android.support.annotation.NonNull
import android.support.annotation.RequiresApi
import com.devwilly.biometricex.BiometricCallback

/**
 * Created by Willy on 2019/4/4.
 */

@RequiresApi(Build.VERSION_CODES.P)
class BiometricAuthCallbackV28(@NonNull private val callback: BiometricCallback): BiometricPrompt.AuthenticationCallback() {

    override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult?) {
        super.onAuthenticationSucceeded(result)
        callback.onAuthSuccessful()

    }

    override fun onAuthenticationError(errorCode: Int, errString: CharSequence?) {
        super.onAuthenticationError(errorCode, errString)
        callback.onAuthError(errorCode, errString)
    }

    override fun onAuthenticationFailed() {
        super.onAuthenticationFailed()
        callback.onAuthFailed()
    }

    override fun onAuthenticationHelp(helpCode: Int, helpString: CharSequence?) {
        super.onAuthenticationHelp(helpCode, helpString)
        callback.onAuthHelp(helpCode, helpString)
    }
}