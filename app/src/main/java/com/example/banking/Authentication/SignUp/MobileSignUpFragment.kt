package com.example.banking.Authentication.SignUp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.transition.Visibility
import com.example.banking.R
import com.example.banking.databinding.FragmentMobileSignUpBinding

class MobileSignUpFragment : Fragment() {

    lateinit var binding: FragmentMobileSignUpBinding
    var phn = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMobileSignUpBinding.inflate(inflater,container,false)
        binding.edtOTP.visibility = View.GONE
        binding.btnOTP.setOnClickListener {
            if(!isEmpty()){
                phn = appendCode()
                binding.edtOTP.visibility = View.VISIBLE
                binding.btnOTP.setText("Verify OTP")
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
        return "+91${binding.edtPhone.text.trim().toString()}"
    }
}