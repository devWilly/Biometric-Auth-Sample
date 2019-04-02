package com.devwilly.biometricex.utils

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.support.v4.app.ActivityCompat
import android.support.v4.hardware.fingerprint.FingerprintManagerCompat


/**
 * Created by Willy on 2019/4/2.
 */
class BiometricUtils {

    companion object {
        fun isBiometricPromptEnabled(): Boolean {
            return Build.VERSION.SDK_INT >= Build.VERSION_CODES.P
        }

        fun isSdkVersionSupported(): Boolean {
            return Build.VERSION.SDK_INT >= Build.VERSION_CODES.M
        }

        fun isHardwareSupported(context: Context): Boolean {
            return FingerprintManagerCompat.from(context).isHardwareDetected
        }

        fun isFingerprintAvailable(context: Context): Boolean {
            return FingerprintManagerCompat.from(context).hasEnrolledFingerprints()
        }

        fun isPermissionGranted(context: Context): Boolean {
            return ActivityCompat.checkSelfPermission(context, Manifest.permission.USE_FINGERPRINT) == PackageManager.PERMISSION_GRANTED
        }
    }
}