package com.example.nira.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "LENDER")
data class LenderEntity (
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,

    val lenderName: String,

    /** interest in percent */
    val interestRate: Int
)