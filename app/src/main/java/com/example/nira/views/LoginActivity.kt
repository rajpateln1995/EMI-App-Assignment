package com.example.nira.views

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import com.example.nira.viewModels.EMICalculateViewModel
import com.example.nira.databinding.LoginActivityBinding


class LoginActivity: AppCompatActivity() {

    private val binding = lazy { LoginActivityBinding.inflate(layoutInflater) }
    private val viewModel: EMICalculateViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.value.root)
        initView()
    }

    private fun initView() {
        binding.value.loginButton.setOnClickListener {
            if (binding.value.editText.text.toString() == "1234") {
                Toast.makeText(this, "Login Successfull :)", Toast.LENGTH_SHORT).show()
                val intent = Intent(this, SelectLendersActivity::class.java)
                startActivity(intent)

            } else {
                Toast.makeText(this, "Enter Correct Pass Code", Toast.LENGTH_LONG).show()
            }
        }
    }
}