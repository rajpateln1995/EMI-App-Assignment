package com.example.nira.views

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import com.example.nira.viewModels.EMICalculateViewModel
import com.example.nira.databinding.LoginActivityBinding
import com.example.nira.utils.constants


class LoginActivity: AppCompatActivity() {

    private val binding = lazy { LoginActivityBinding.inflate(layoutInflater) }
    private val viewModel: EMICalculateViewModel by viewModels()
    private val sharedPref = lazy { getSharedPreferences(constants.SHARED_PREFERENCE_NAME, MODE_PRIVATE) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.value.root)
        val isLoggedin = sharedPref.value.getBoolean(constants.IS_USER_LOGGED_IN, false)
        Log.i("login", isLoggedin.toString())
        if (isLoggedin){

            Log.i("login", sharedPref.value.getBoolean(constants.IS_USER_LOGGED_IN, false).toString())
            val intent = Intent(this, SelectLendersActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            startActivity(intent)
            finish()
        }
        initView()
    }

    private fun initView() {
        binding.value.loginButton.setOnClickListener {
            if (binding.value.editText.text.toString() == "1234") {
                sharedPref.value.edit().putBoolean(constants.IS_USER_LOGGED_IN, true).apply()
                Log.i("login saved", sharedPref.value.getBoolean(constants.IS_USER_LOGGED_IN, false).toString())
                Toast.makeText(this, "Login Successfull :)", Toast.LENGTH_SHORT).show()
                val intent = Intent(this, SelectLendersActivity::class.java)
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                startActivity(intent)
                finish()
            } else {
                Toast.makeText(this, "Enter Correct Pass Code", Toast.LENGTH_LONG).show()
            }
        }
    }
}