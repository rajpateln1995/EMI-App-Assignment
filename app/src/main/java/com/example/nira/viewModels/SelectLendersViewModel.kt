package com.example.nira.viewModels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.nira.database.AppDatabase
import com.example.nira.database.LenderEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SelectLendersViewModel: ViewModel() {

    val lendersData = MutableLiveData<List<LenderEntity>>()

    fun fetchData(db: AppDatabase) {
        viewModelScope.launch {
            db.getLenderDao().getLenders().collect {
                lendersData.value = it
            }
        }
    }
}