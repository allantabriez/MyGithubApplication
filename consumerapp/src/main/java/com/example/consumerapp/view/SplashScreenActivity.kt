package com.example.consumerapp.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.example.consumerapp.R

class SplashScreenActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        val runnable = Runnable {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
        Handler().postDelayed(runnable, 2000)
    }
}