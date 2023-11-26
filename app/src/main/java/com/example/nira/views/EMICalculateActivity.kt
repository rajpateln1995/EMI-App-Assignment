package com.example.nira.views

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import com.example.nira.databinding.EmiCalculateActivityBinding
import com.example.nira.viewModels.EMICalculateViewModel
import com.example.nira.utils.constants


class EMICalculateActivity : AppCompatActivity() {

    private val binding = lazy { EmiCalculateActivityBinding.inflate(layoutInflater) }
    private val viewModel: EMICalculateViewModel by viewModels()
    private var lenderName = ""
    private var interestRate = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.value.root)
        lenderName = intent.getStringExtra(constants.EXTRA_LENDER_NAME).toString()
        interestRate = intent.getStringExtra(constants.EXTRA_INTERESTRATE).toString()
    }
}