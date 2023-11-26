package com.example.nira.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [EMIEntity::class, LenderEntity::class], version = 1)
abstract class AppDatabase: RoomDatabase() {

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            synchronized(this) {
                var instance = INSTANCE

                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        AppDatabase::class.java,
                        "AppDatabase"
                    ).allowMainThreadQueries().build()

                    INSTANCE = instance
                    INSTANCE!!.getLenderDao().deleteAll()
                    val lenders = listOf<LenderEntity>(LenderEntity(lenderName = "ICICI" , interestRate = 2.0, loanAvailed = false),
                        LenderEntity(lenderName = "Muthoot Finance" , interestRate = 1.3, loanAvailed = false),
                        LenderEntity(lenderName = "HDFC", interestRate = 1.5 , loanAvailed = false),
                        LenderEntity(lenderName = "IDFC" , interestRate = 1.7 , loanAvailed = false))
                    INSTANCE!!.getLenderDao().insertLenderDetails(lenders)
                }
                return instance
            }
        }
    }
    abstract fun getEMIDao(): EMIDao

    abstract fun getLenderDao(): LenderDao
}