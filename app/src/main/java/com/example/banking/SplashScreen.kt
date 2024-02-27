package com.example.banking

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.WindowManager
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import com.example.banking.Authentication.AuthActivity
import com.example.banking.databinding.ActivitySplashScreenBinding

class SplashScreen : AppCompatActivity() {
    lateinit var  binding: ActivitySplashScreenBinding
    lateinit var anim: Animation
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.imgLogo.setImageResource(R.drawable.logo)
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
        anim = AnimationUtils.loadAnimation(applicationContext,R.anim.slide_zoom)
        binding.imgLogo.startAnimation(anim)
        Handler().postDelayed({
            val intent = Intent(this, AuthActivity::class.java)
            startActivity(intent)
            overridePendingTransition(R.anim.slide_right,R.anim.zoom_out)
            finish()
        },3000)

    }
}