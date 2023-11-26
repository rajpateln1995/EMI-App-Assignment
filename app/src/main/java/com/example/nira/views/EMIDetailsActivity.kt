package com.example.nira.views

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import com.example.nira.adaptor.EmiAdaptor
import com.example.nira.database.AppDatabase
import com.example.nira.databinding.EmiDetailsActivityBinding
import com.example.nira.utils.constants
import com.example.nira.viewModels.EMIDetailsViewModel


class EMIDetailsActivity : AppCompatActivity() {

    private val binding = lazy { EmiDetailsActivityBinding.inflate(layoutInflater) }
    private val viewModel: EMIDetailsViewModel by viewModels()
    private lateinit var dataBase : AppDatabase
    private lateinit var adaptor: EmiAdaptor
    private var lenderId = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.value.root)
        lenderId = intent.getStringExtra(constants.EXTRA_LENDER_ID).toString()
        initView()
        initDb()
        initViewModel()
    }

    private fun initDb() {
        dataBase = AppDatabase.getInstance(this)
    }

    private fun initViewModel() {
        viewModel.fetchData(dataBase, lenderId.toLong())
        viewModel.emiData.observe(this){
            adaptor.setData(it)
        }
    }

    private fun initView() {
        adaptor = EmiAdaptor(listOf())
        binding.value.emiRv.adapter = adaptor
        binding.value.closeButton.setOnClickListener {
            finish()
        }
    }
}