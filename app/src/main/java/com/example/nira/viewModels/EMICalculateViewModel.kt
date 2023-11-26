package com.example.nira.viewModels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.nira.database.AppDatabase
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class EMICalculateViewModel: ViewModel() {

    val loanAmount = MutableLiveData<Long>(5000)
    val tenureValue = MutableLiveData<Int>(3)
    val emi = MutableLiveData<Long>(0)
    val loanAvailed = MutableLiveData<Boolean>(true)

    fun fetchData(db: AppDatabase, id: Long) {
        viewModelScope.launch {
            db.getLenderDao().getLender(id).collect {
                loanAvailed.value = it.loanAvailed
            }
        }
    }


}