package com.example.nira.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import kotlinx.coroutines.flow.Flow

@Dao
interface EMIDao {
    /** return row id of the created data */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun saveEmi(emiData: EMIEntity): Flow<Long>

}