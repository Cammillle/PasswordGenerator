package com.example.passwordgenerator.data

import android.app.Application
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.passwordgenerator.data.dao.PasswordDao
import com.example.passwordgenerator.data.dao.PasswordFolderDao
import com.example.passwordgenerator.data.entity.PasswordEntity
import com.example.passwordgenerator.data.entity.PasswordFolderEntity

@Database(entities = [PasswordEntity::class, PasswordFolderEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {

    abstract fun passwordDao(): PasswordDao
    abstract fun passwordFolderDao(): PasswordFolderDao

    companion object {
        private var INSTANCE: AppDatabase? = null
        private val LOCK = Any()
        private const val DB_NAME = "password.db"

        fun getInstance(application: Application): AppDatabase {
            INSTANCE?.let {
                return it
            }
            synchronized(LOCK) {
                INSTANCE?.let {
                    return it
                }
            }
            val db = Room.databaseBuilder(
                application,
                AppDatabase::class.java,
                DB_NAME
            )

                .build()
            INSTANCE = db
            return db
        }
    }

}