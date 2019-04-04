package com.devwilly.biometricex

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.devwilly.biometricex.auth.BiometricManager


class MainActivity : AppCompatActivity(), BiometricCallback {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        findViewById<Button>(R.id.login).setOnClickListener {
            BiometricManager.Builder(this@MainActivity)
                .setTitle(getString(R.string.login))
                .setSubTitle(getString(R.string.bio_sub_title))
                .setDescription(getString(R.string.bio_description))
                .setNegativeButtonText(getString(R.string.bio_negative_button_text))
                .build()
                .authenticate(this@MainActivity)
        }
    }

    override fun onSdkVersionNotSupported() {
    }

    override fun onBiometricAuthNotSupported() {
    }

    override fun onBiometricAuthNotAvailable() {
    }

    override fun onBiometricAuthPermissionNotGranted() {
    }

    override fun onBiometricAuthInternalError(error: String) {
    }

    override fun onAuthCancelled() {
    }

    override fun onAuthSuccessful() {
    }

    override fun onAuthError(errorCode: Int, errString: CharSequence?) {
    }

    override fun onAuthFailed() {
    }

    override fun onAuthHelp(helpCode: Int, helpString: CharSequence?) {
    }

}
