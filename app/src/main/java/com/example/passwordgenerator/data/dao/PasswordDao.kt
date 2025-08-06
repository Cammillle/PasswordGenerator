package com.example.passwordgenerator.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.passwordgenerator.data.entity.PasswordEntity

@Dao
interface PasswordDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(password: PasswordEntity): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(passwords: List<PasswordEntity>)

    @Query("SELECT * FROM passwords ORDER BY id DESC")
    suspend fun getAll(): List<PasswordEntity>

    @Query("SELECT * FROM passwords WHERE folderId IS NULL ORDER BY id DESC")
    suspend fun getGenerated(): List<PasswordEntity>

    @Query("SELECT * FROM passwords WHERE folderId = :folderId")
    suspend fun getByFolder(folderId: Long): List<PasswordEntity>

    @Delete
    suspend fun delete(password: PasswordEntity)

    @Query("DELETE FROM passwords")
    suspend fun deleteAll()


}