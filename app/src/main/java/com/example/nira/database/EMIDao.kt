package com.example.nira.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface EMIDao {
    /** return row id of the created data */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun saveEmi(emiData: List<EMIEntity>)

    @Query("SELECT * FROM EMI WHERE lenderId = :id")
    fun getEmi(id: Long): Flow<List<EMIEntity>>

    @Query("UPDATE EMI SET paid = :status WHERE id = :id")
    fun updateEmiStatus(id:Long, status: Boolean)

}