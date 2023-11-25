package com.example.nira.database

import androidx.room.Dao
import androidx.room.Insert

@Dao
interface LenderDao {

    @Insert
    fun insertLenderDetails()
}