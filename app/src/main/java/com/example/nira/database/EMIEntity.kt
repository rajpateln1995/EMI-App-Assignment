package com.example.nira.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.Instant
import java.util.Date

@Entity(tableName = "EMI")
data class EMIEntity (

    @PrimaryKey(autoGenerate = true)
    val id: Long,

    val totalLoanAmount: Long,

    /** tenure in months */
    val tenure: Int,

    /** dd/mm/yyyy */
    val startDate: String
)