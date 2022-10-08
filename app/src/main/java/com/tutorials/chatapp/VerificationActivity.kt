package com.tutorials.chatapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.firebase.auth.FirebaseAuth
import com.tutorials.chatapp.databinding.ActivityVerificationBinding

class VerificationActivity : AppCompatActivity() {
    var binding : ActivityVerificationBinding ? =null
    var auth : FirebaseAuth? =null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityVerificationBinding.inflate(layoutInflater)
        setContentView(binding!!.root)

        auth = FirebaseAuth.getInstance()
        if (auth!!.currentUser !=null){
            val intent = Intent(this@VerificationActivity,MainActivity::class.java)
            startActivity(intent)
            finish()
        }
        supportActionBar?.hide()
        binding!!.edtNumber.requestFocus()
        binding!!.btnContinue.setOnClickListener {
            val intent = Intent(this@VerificationActivity,OTPActivity::class.java)
            val phoneNumber = "+88"+binding!!.edtNumber.text.toString()
            intent.putExtra("phoneNumber",phoneNumber)
            startActivity(intent)
        }
    }
}