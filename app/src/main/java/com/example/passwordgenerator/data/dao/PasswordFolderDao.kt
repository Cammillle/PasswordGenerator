package com.example.passwordgenerator.data.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.passwordgenerator.data.entity.PasswordFolderEntity

@Dao
interface PasswordFolderDao {

    @Query("SELECT * FROM password_folders")
    fun getAllFolders(): LiveData<List<PasswordFolderEntity>>

    @Query("SELECT * FROM password_folders WHERE id = :folderId")
    suspend fun getFolderById(folderId: Int): PasswordFolderEntity?

    @Insert
    suspend fun insertFolder(folder: PasswordFolderEntity): Int

    @Query("DELETE FROM password_folders WHERE id = :folderId")
    suspend fun deleteFolder(folderId: Int)
}