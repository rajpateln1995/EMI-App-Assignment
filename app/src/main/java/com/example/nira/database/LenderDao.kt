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

    @Query("SELECT * FROM LENDER WHERE id = :id")
    fun getLender(id: Long): Flow<LenderEntity>

    @Query("DELETE FROM LENDER")
    fun deleteAll()

    @Query("UPDATE LENDER SET loanAvailed = :status WHERE id = :id")
    fun updateLoanStatus(id: Long, status: Boolean)
}