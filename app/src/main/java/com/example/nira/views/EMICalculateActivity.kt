package com.example.nira.views

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.SeekBar
import android.widget.SeekBar.OnSeekBarChangeListener
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.nira.R
import com.example.nira.database.AppDatabase
import com.example.nira.database.EMIEntity
import com.example.nira.databinding.EmiCalculateActivityBinding
import com.example.nira.utils.constants
import com.example.nira.viewModels.EMICalculateViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.Calendar


class EMICalculateActivity : AppCompatActivity() {

    private val binding = lazy { EmiCalculateActivityBinding.inflate(layoutInflater) }
    private val viewModel: EMICalculateViewModel by viewModels()
    private lateinit var dataBase: AppDatabase
    private var lenderName = ""
    private var interestRate = ""
    private var lenderId = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.value.root)
        lenderName = intent.getStringExtra(constants.EXTRA_LENDER_NAME).toString()
        interestRate = intent.getStringExtra(constants.EXTRA_INTERESTRATE).toString()
        lenderId = intent.getStringExtra(constants.EXTRA_LENDER_ID).toString()
        initView()
        initViewModel()
        initDb()
    }

    private fun initDb() {
        dataBase = AppDatabase.getInstance(this)
        viewModel.fetchData(dataBase, lenderId.toLong())
    }

    private fun initViewModel() {
        viewModel.loanAmount.observe(this) {
            binding.value.loanAmountTv.text = getString(R.string.loan_amount, it.toString())
            calculateEmi()
        }

        viewModel.tenureValue.observe(this) {
            binding.value.tenureTv.text = getString(R.string.tenure, it.toString())
            calculateEmi()
        }

        viewModel.emi.observe(this) {
            binding.value.emiTv.text = it.toString()
        }

        viewModel.loanAvailed.observe(this) {
            if (it) {
                binding.value.proceedButton.text = "Show EMI's"
            } else {
                binding.value.proceedButton.text = "Proceed"
            }
        }
    }

    private fun calculateEmi() {
        val p = viewModel.loanAmount.value!!
        val r = interestRate.toFloat() / 100
        val n = viewModel.tenureValue.value!!
        viewModel.emi.value = ((p * r * Math.pow(1 + r.toDouble(), n.toDouble())) / (Math.pow(
            (1 + r).toDouble(),
            n.toDouble()
        ) - 1)).toLong()
    }

    private fun initView() {
        val step_loan = 1
        val max_loan = 100000
        val min_loan = 5000
        binding.value.loanSeekBar.max = (max_loan - min_loan) / step_loan
        binding.value.loanSeekBar.setOnSeekBarChangeListener(
            object : OnSeekBarChangeListener {
                override fun onStopTrackingTouch(seekBar: SeekBar) {
                }

                override fun onStartTrackingTouch(seekBar: SeekBar) {
                }

                override fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean) {
                    viewModel.loanAmount.value = min_loan.toLong() + progress * step_loan
                }
            }
        )

        val step_tenure = 1
        val max_tenure = 12
        val min_tenure = 3
        binding.value.tenureSeekBar.max = (max_tenure - min_tenure) / step_tenure
        binding.value.tenureSeekBar.setOnSeekBarChangeListener(
            object : OnSeekBarChangeListener {
                override fun onStopTrackingTouch(seekBar: SeekBar) {
                }

                override fun onStartTrackingTouch(seekBar: SeekBar) {
                }

                override fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean) {
                    viewModel.tenureValue.value = min_tenure + progress * step_tenure
                }
            }
        )

        binding.value.lenderNameTv.text = lenderName
        binding.value.interestRateTv.text = getString(R.string.interest_text, interestRate)

        binding.value.proceedButton.setOnClickListener {
            if (viewModel.loanAvailed.value == true) {
                openEmiActivity()
            } else {
                proceed()
            }
        }
    }

    private fun openEmiActivity() {
        val intent = Intent(this, EMIDetailsActivity::class.java)
        intent.putExtra(constants.EXTRA_LENDER_ID, lenderId)
        startActivity(intent)
    }


    // call from proceed btn click listener
    fun proceed() {
        lifecycleScope.launch {
            withContext(Dispatchers.IO) {
                dataBase.getLenderDao().updateLoanStatus(lenderId.toLong(), true)
                val emiList = ArrayList<EMIEntity>()
                val time = System.currentTimeMillis()
                val calendar = Calendar.getInstance()
                calendar.timeInMillis = time
                for (i in 1..viewModel.tenureValue.value!!) {
                    calendar.add(Calendar.MONTH,1)
                    val emi = EMIEntity(
                        id = 0,
                        lenderId = lenderId.toLong(),
                        emiAmount = viewModel.emi.value!!,
                        tenure = viewModel.tenureValue.value!!,
                        Date = calendar.timeInMillis,
                        paid = false)
                    emiList.add(emi)
                }
                dataBase.getEMIDao().saveEmi(emiList.toList())
            }
        }
    }
}