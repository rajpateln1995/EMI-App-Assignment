package com.example.nira.viewModels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.nira.database.AppDatabase
import com.example.nira.database.EMIEntity
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class EMIDetailsViewModel: ViewModel() {

    val emiData = MutableLiveData<List<EMIEntity>>()
    fun fetchData(db: AppDatabase, lenderId: Long) {
        viewModelScope.launch {
            db.getEMIDao().getEmi(lenderId).collect {
                emiData.value = it
            }
        }
    }

}