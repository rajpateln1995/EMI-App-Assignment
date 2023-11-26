package com.example.nira.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface LenderDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertLenderDetails(lenderData: List<LenderEntity>)

    @Query("SELECT * FROM LENDER")
    fun getLenders(): Flow<List<LenderEntity>>

    @Query("DELETE FROM LENDER")
    fun deleteAll()
}