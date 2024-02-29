package com.example.banking.Authentication.SignIn

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
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
        binding = FragmentSigninBinding.inflate(inflater,container,false)
        auth = FirebaseAuth.getInstance()
        binding.txtSignUp.setOnClickListener {
            signUp()
        }
        binding.btnSignIn.setOnClickListener {
            signIn()
        }
        return binding.root
    }
    fun signIn(){
        if(!isEmpty()){
            binding.progressSignin.visibility = View.VISIBLE
            var email = binding.edtSignInEmail.text.trim().toString()
            var pass = binding.edtSignInPassword.text.trim().toString()
            auth?.signInWithEmailAndPassword(email,pass)?.addOnCompleteListener {
                if(it.isSuccessful){
                    Toast.makeText(requireContext(),"Signing in...",Toast.LENGTH_SHORT).show()
                }else{
                    binding.progressSignin.visibility = View.GONE
                    Toast.makeText(requireContext(),"Error : ${it.exception}",Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
    fun signUp(){
        val frag = requireActivity().supportFragmentManager.beginTransaction()
        frag.replace(R.id.authFrame, SignUpFragment())
        frag.commit()
    }
    fun isEmpty(): Boolean{
        var key = false
        if(binding.edtSignInEmail.text.trim().toString().isEmpty()){
            binding.edtSignInEmail.error = "Email is required"
            key = true
        }
        if(binding.edtSignInPassword.text.trim().toString().isEmpty()){
            binding.edtSignInPassword.error = "Password is required"
            key = true
        }
        return key
    }
}