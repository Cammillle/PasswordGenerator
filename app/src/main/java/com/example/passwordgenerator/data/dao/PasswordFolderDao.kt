package com.example.passwordgenerator.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.passwordgenerator.data.entity.PasswordFolderEntity

@Dao
interface PasswordFolderDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(folder: PasswordFolderEntity): Long

    @Query("SELECT * FROM folders ORDER BY id DESC")
    suspend fun getAll(): List<PasswordFolderEntity>

    @Query("DELETE FROM folders")
    suspend fun deleteAll()
}