package com.example.banking.Authentication.SignIn

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.banking.Authentication.SignUp.SignUpFragment
import com.example.banking.R
import com.example.banking.databinding.FragmentSigninBinding
import com.google.firebase.auth.FirebaseAuth


class SignInFragment : Fragment() {
    lateinit var binding: FragmentSigninBinding
    lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val binding = FragmentSigninBinding.inflate(inflater,container,false)
        var email = binding.edtSignInEmail.text.trim().toString()
        var pass = binding.edtSignInPassword.text.trim().toString()
        binding.txtSignUp.setOnClickListener {
            val frag = requireActivity().supportFragmentManager.beginTransaction()
            frag.replace(R.id.authFrame, SignUpFragment())
            frag.commit()
        }
        return binding.root
    }
    fun isPasswordRight(): Boolean{
        var pass = binding.edtSignInPassword.text.trim().toString()
        if(pass.equals(null)){
            binding.edtSignInPassword.error = "Enter a password"
            return false
        }else if(pass.length < 8){
            binding.edtSignInPassword.error = "Password length must be greater than 8"
            return false
        }
        return true
    }
}