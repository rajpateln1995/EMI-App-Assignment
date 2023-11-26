package com.example.nira.viewModels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.nira.database.AppDatabase
import com.example.nira.database.LenderEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class SelectLendersViewModel: ViewModel() {

    val lendersData = MutableLiveData<List<LenderEntity>>()

    fun fetchData(db: AppDatabase) {
        viewModelScope.launch {
            with(Dispatchers.IO){
                db.getLenderDao().getLenders().collect {
                    lendersData.value = it
                }
            }

        }
    }
}