package com.example.banking.Authentication.SignUp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.banking.R
import com.example.banking.databinding.FragmentSignupBinding
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.database

class SignUpFragment : Fragment() {
    lateinit var binding: FragmentSignupBinding
    lateinit var auth: FirebaseAuth
    lateinit var firebaseDatabase: FirebaseDatabase
    lateinit var db: DatabaseReference
    var uname = ""
    var email = ""
    var pass = ""
    var pin = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_signup, container, false)
        binding = FragmentSignupBinding.inflate(inflater,container,false)
        auth = FirebaseAuth.getInstance()
        firebaseDatabase = Firebase.database
        db = firebaseDatabase.getReference("Clients")


        binding.btnSignUp.setOnClickListener {
            Toast.makeText(requireContext(),"BtnSignUp clicked",Toast.LENGTH_SHORT).show()
            if (!isEmpty()){
                if(passMatch()){
                    if(pinMatch()){
                        uname = binding.edtSignupUname.text.trim().toString()
                        email = binding.edtSignupEmail.text.trim().toString()
                        pass = binding.edtSignupPassword1.text.trim().toString()
                        pin = binding.edtSignupPIN1.text.trim().toString()
                        auth.createUserWithEmailAndPassword(email,pass).addOnCompleteListener {
                            if (it.isSuccessful){

                                db.child(uname).child("UserName").setValue(uname)
                                db.child(uname).child("PIN").setValue(pin)
                                Toast.makeText(requireContext(),"User created",Toast.LENGTH_SHORT).show()

                            }else{
                                Toast.makeText(requireContext(),"Signup Failed",Toast.LENGTH_SHORT).show()
                            }
                        }
                        db.addListenerForSingleValueEvent(object: ValueEventListener{
                            override fun onDataChange(snapshot: DataSnapshot) {
                                if(snapshot.hasChild(uname)){
                                    binding.edtSignupUname.error = "$uname already taken"
                                    Toast.makeText(requireContext(),"Username already exists",Toast.LENGTH_SHORT).show()
                                }
                            }

                            override fun onCancelled(error: DatabaseError) {}

                        })


                    }else{
                        Toast.makeText(requireContext(),"PINs don't match",Toast.LENGTH_SHORT).show()
                    }
                }else{
                    Toast.makeText(requireContext(),"Passwords don't match",Toast.LENGTH_SHORT).show()
                }

            }else{
                Toast.makeText(requireContext(),"Fill all fields",Toast.LENGTH_SHORT).show()
            }
        }

        return binding.root
    }

    fun isEmpty(): Boolean{
        var key = false;
        if(binding.edtSignupUname.text.trim().toString().isEmpty()){
            binding.edtSignupUname.error = "Username is required"
            key = true
        }
        if(binding.edtSignupEmail.text.trim().toString().isEmpty()){
            binding.edtSignupEmail.error = "Email is required"
            key = true
        }
        if(binding.edtSignupPassword1.text.trim().toString().isEmpty()){
            binding.edtSignupPassword1.error = "Password is required"
            key = true
        }
        if(binding.edtSignupPassword2.text.trim().toString().isEmpty()){
            binding.edtSignupPassword2.error = "Password is required"
            key = true
        }
        if(binding.edtSignupPIN1.text.trim().toString().isEmpty()){
            binding.edtSignupPIN1.error = "PIN is required"
            key = true
        }
        if(binding.edtSignupPIN2.text.trim().toString().isEmpty()){
            binding.edtSignupPIN2.error = "PIN is required"
            key = true
        }
        return key
    }
    fun passMatch(): Boolean{
        if(binding.edtSignupPassword1.text.trim().toString().equals(binding.edtSignupPassword2.text.trim().toString())){
            return true
        }else{
            binding.edtSignupPassword1.error = "Passwords don't match"
            binding.edtSignupPassword2.error = "Passwords don't match"
            return false
        }
    }
    fun pinMatch(): Boolean{
        if(binding.edtSignupPIN1.text.trim().toString().equals(binding.edtSignupPIN2.text.trim().toString())){
            return true
        }else{
            binding.edtSignupPIN1.error = "PINs don't match"
            binding.edtSignupPIN2.error = "PINs don't match"
            return false
        }
    }
}