package com.example.passwordgenerator.domain.repository

import androidx.lifecycle.LiveData
import com.example.passwordgenerator.domain.model.PasswordFolder

interface PasswordFolderRepository {

    suspend fun insertFolder(folder: PasswordFolder): Long
    suspend fun getAllFolders(): List<PasswordFolder>
    suspend fun deleteAllFolders()
}