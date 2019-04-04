package com.devwilly.biometricex

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.devwilly.biometricex.auth.BiometricManager


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        findViewById<Button>(R.id.login).setOnClickListener {
            BiometricManager.Builder(this)
                .setTitle("title")
                .setSubTitle("subtitle")
                .setDescription("description")
                .setNegativeButtonText("negativeButtonText")
                .build()
                .authenticate()
        }
    }
}
