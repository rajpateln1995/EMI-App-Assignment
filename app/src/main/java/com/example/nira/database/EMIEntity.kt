package com.example.nira.database

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import java.time.Instant
import java.util.Date

@Entity(tableName = "EMI")
data class EMIEntity (

    @PrimaryKey(autoGenerate = true)
    val id: Long,

    val lenderId: Long,

    val emiAmount: Long,

    /** tenure in months */
    val tenure: Int,

    /** time in millis */
    val Date: Long,

    val paid:Boolean
)