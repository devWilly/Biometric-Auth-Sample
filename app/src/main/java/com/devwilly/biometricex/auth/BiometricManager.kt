package com.devwilly.biometricex.auth

import android.annotation.TargetApi
import android.content.Context
import android.content.DialogInterface
import android.hardware.biometrics.BiometricPrompt
import android.os.Build
import android.os.CancellationSignal
import android.support.annotation.NonNull
import com.devwilly.biometricex.BiometricCallback
import com.devwilly.biometricex.R
import com.devwilly.biometricex.utils.BiometricUtils

/**
 * Created by Willy on 2019/4/3.
 */
class BiometricManager(
        private val context: Context,
        private val title: String,
        private val subTitle: String,
        private val description: String,
        private val negativeButtonText: String) {

    fun authenticate(@NonNull callback: BiometricCallback) {
        val res = context.resources

        if (title.isEmpty()) {
            callback.onBiometricAuthInternalError(res.getString(R.string.bio_dialog_title_error_text))
        }

        if (subTitle.isEmpty()) {
            callback.onBiometricAuthInternalError(res.getString(R.string.bio_dialog_sub_title_error_text))
        }

        if (description.isEmpty()) {
            callback.onBiometricAuthInternalError(res.getString(R.string.bio_dialog_description_error_text))
        }

        if (negativeButtonText.isEmpty()) {
            callback.onBiometricAuthInternalError(res.getString(R.string.bio_dialog_negative_btn_error_text))
        }

        if (BiometricUtils.isSdkVersionSupported().not()) {
            callback.onSdkVersionNotSupported()
        }

        if (BiometricUtils.isPermissionGranted(context).not()) {
            callback.onBiometricAuthPermissionNotGranted()
        }

        if (BiometricUtils.isHardwareSupported(context).not()) {
            callback.onBiometricAuthNotSupported()
        }

        if (BiometricUtils.isFingerprintAvailable(context).not()) {
            callback.onBiometricAuthNotAvailable()
        }

        displayBiometricDialog(callback)
    }

    private fun displayBiometricDialog(@NonNull callback: BiometricCallback) {
        if (BiometricUtils.isBiometricPromptEnabled()) {
            displayBiometricPrompt(callback)
        } else {
            displayBiometricPromptV23(callback)
        }
    }

    private fun displayBiometricPromptV23(@NonNull callback: BiometricCallback) {

    }

    @TargetApi(Build.VERSION_CODES.P)
    private fun displayBiometricPrompt(@NonNull callback: BiometricCallback) {
        BiometricPrompt.Builder(context)
                .setTitle(title)
                .setSubtitle(subTitle)
                .setDescription(description)
                .setNegativeButton(negativeButtonText, context.mainExecutor,
                        DialogInterface.OnClickListener { _, _ -> callback.onAuthCancelled()})
                .build()
                .authenticate(CancellationSignal(), context.mainExecutor, BiometricAuthCallbackV28(callback))
    }


    data class Builder(
            var context: Context,
            var title: String = "",
            var subTitle: String = "",
            var description: String = "",
            var negativeButtonText: String = "") {

        fun setTitle(title: String) = apply { this.title = title }

        fun setSubTitle(subTitle: String) = apply { this.subTitle = subTitle }

        fun setDescription(description: String) = apply { this.description = description }

        fun setNegativeButtonText(negativeButtonText: String) = apply { this.negativeButtonText = negativeButtonText }

        fun build(): BiometricManager = BiometricManager(context, title, subTitle, description, negativeButtonText)
    }
}