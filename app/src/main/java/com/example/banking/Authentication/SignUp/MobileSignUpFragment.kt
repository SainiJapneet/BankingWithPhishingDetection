package com.example.banking.Authentication.SignUp

import android.app.Activity
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.banking.Authentication.SignIn.SignInFragment
import com.example.banking.R
import com.example.banking.databinding.FragmentMobileSignUpBinding
import com.google.firebase.Firebase
import com.google.firebase.FirebaseException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthOptions
import com.google.firebase.auth.PhoneAuthProvider
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.database
import java.util.concurrent.TimeUnit

class MobileSignUpFragment : Fragment() {

    lateinit var binding: FragmentMobileSignUpBinding
    lateinit var auth: FirebaseAuth
    lateinit var firebaseDatabase: FirebaseDatabase
    lateinit var db: DatabaseReference
    lateinit var storedVerificationId: String
    lateinit var resendToken: PhoneAuthProvider.ForceResendingToken
    lateinit var callbacks: PhoneAuthProvider.OnVerificationStateChangedCallbacks
    var phn = ""
    var otp = ""
    var key = 0
    var uname = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMobileSignUpBinding.inflate(inflater,container,false)
        auth = FirebaseAuth.getInstance()
        firebaseDatabase = Firebase.database
        binding.edtOTP.visibility = View.GONE
        uname = arguments?.getString("uname")!!
        db = firebaseDatabase.getReference("Clients").child(uname)

        binding.btnOTP.setOnClickListener {
            if(key == 0){
                getOTP()
            }else{
                verifyOTP()
            }
        }
        callbacks = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks(){
            override fun onVerificationCompleted(p0: PhoneAuthCredential) {
                TODO("Not yet implemented")
            }

            override fun onVerificationFailed(p0: FirebaseException) {
                TODO("Not yet implemented")
            }

            override fun onCodeSent(p0: String, p1: PhoneAuthProvider.ForceResendingToken) {
                storedVerificationId = p0
                resendToken = p1
            }
        }
        return binding.root
    }
    fun isEmpty(): Boolean{
        var key = true
        if(binding.edtPhone.text.trim().toString().isNotEmpty()){
            key = false
        }else{
            binding.edtPhone.error = "Phone number is required"
        }
        return key
    }
    fun appendCode(): String{
        return "+91${binding.edtPhone.text.trim()}"
    }
    fun getOTP(){
        if(!isEmpty()){
            key = 1
            phn = appendCode()
            binding.edtOTP.visibility = View.VISIBLE
            binding.btnOTP.text = "Verify OTP"
            sendVerificationCode(phn)
        }
    }

    fun verifyOTP(){
        if(binding.edtOTP.text.trim().toString().isNotEmpty()){
            otp = binding.edtOTP.text.trim().toString()
            val credential: PhoneAuthCredential = PhoneAuthProvider.getCredential(storedVerificationId,otp)
            auth.signInWithCredential(credential).addOnCompleteListener{task ->
                if(task.isSuccessful){
                    db.child("Phone Number").setValue(phn)
                    val frag = requireActivity().supportFragmentManager.beginTransaction()
                    frag.replace(R.id.authFrame, SignInFragment())
                    frag.commit()
                }else{
                    if(task.exception is FirebaseAuthInvalidCredentialsException){
                        binding.edtOTP.error = "Invalid OTP"
                    }
                }
            }
        }else{
            binding.edtOTP.error = "OTP is required"
        }

    }

    fun sendVerificationCode(number: String){
        val options = PhoneAuthOptions.newBuilder(auth).setPhoneNumber(number).setTimeout(60L,TimeUnit.SECONDS)
            .setActivity(requireContext() as Activity).setCallbacks(callbacks).build()
        PhoneAuthProvider.verifyPhoneNumber(options)

    }
}