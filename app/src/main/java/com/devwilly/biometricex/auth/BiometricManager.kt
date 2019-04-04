package com.devwilly.biometricex.auth

import android.content.Context
import android.content.DialogInterface
import android.hardware.biometrics.BiometricPrompt
import android.os.Build
import android.os.CancellationSignal

/**
 * Created by Willy on 2019/4/3.
 */
class BiometricManager(
    private val context: Context,
    private val title: String,
    private val subTitle: String,
    private val description: String,
    private val negativeButtonText: String) {

    fun authenticate() {
        displayBiometricPrompt()
    }

    private fun displayBiometricPrompt() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            BiometricPrompt.Builder(context)
                .setTitle(title)
                .setSubtitle(subTitle)
                .setDescription(description)
                .setNegativeButton(negativeButtonText, context.mainExecutor, DialogInterface.OnClickListener { dialogInterface, i -> "" })
                .build()
                .authenticate(CancellationSignal(), context.mainExecutor, object : BiometricPrompt.AuthenticationCallback() {

                })
        }
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