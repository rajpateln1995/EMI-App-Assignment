package com.example.nira.database

import android.app.Application
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
                    val lenders = listOf<LenderEntity>(LenderEntity(lenderName = "ICICI" , interestRate = 12),
                        LenderEntity(lenderName = "Muthoot Finance" , interestRate = 10),
                        LenderEntity(lenderName = "HDFC", interestRate = 9),
                        LenderEntity(lenderName = "IDFC" , interestRate = 8))
                    INSTANCE!!.getLenderDao().insertLenderDetails(lenders)
                }
                return instance
            }
        }
    }
    abstract fun getEMIDao(): EMIDao

    abstract fun getLenderDao(): LenderDao
}