package com.example.nira.views

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.nira.adaptor.LendersAdaptor
import com.example.nira.database.AppDatabase
import com.example.nira.databinding.SelectLendersActivityBinding
import com.example.nira.viewModels.SelectLendersViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch

class SelectLendersActivity : AppCompatActivity() {
    private val binding = lazy { SelectLendersActivityBinding.inflate(layoutInflater) }
    private lateinit var adaptor: LendersAdaptor
    private lateinit var dataBase : AppDatabase
    private val viewModel: SelectLendersViewModel by viewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.value.root)
        initView()
        initDb()
    }

    private fun initDb() {
        dataBase = AppDatabase.getInstance(this@SelectLendersActivity)
        viewModel.fetchData(dataBase)
    }

    private fun initView() {
        adaptor = LendersAdaptor(listOf())
        binding.value.recyclerView.adapter = adaptor
        viewModel.lendersData.observe(this) {
            adaptor.setData(it)
        }

        binding.value.aboutUs.setOnClickListener {
            val intent = Intent(this, AboutUsActivity::class.java)
            startActivity(intent)
        }
    }
}