package com.example.passwordgenerator.domain.repository

import androidx.lifecycle.LiveData
import com.example.passwordgenerator.domain.model.PasswordFolder

interface PasswordFolderRepository {

    fun getAllFolders(): LiveData<List<PasswordFolder>>

    suspend fun getFolderById(folderId: Int): PasswordFolder?

    suspend fun saveFolder(folder: PasswordFolder): Int

    suspend fun deleteFolder(folderId: Int)
}