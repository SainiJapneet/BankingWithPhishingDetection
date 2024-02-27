package com.example.banking.Authentication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.banking.Authentication.LogIn.LoginFragment
import com.example.banking.R

class AuthActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_auth)



        getSupportActionBar()?.hide()
        loadFragment(LoginFragment())

    }
    fun loadFragment(frag: Fragment){
        val myFrag = supportFragmentManager.beginTransaction()
        myFrag.replace(R.id.authFrame,frag)
        myFrag.commit()
    }
}