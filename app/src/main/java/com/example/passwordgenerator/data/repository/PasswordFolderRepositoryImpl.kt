package com.example.passwordgenerator.data.repository

import android.app.Application
import com.example.passwordgenerator.data.AppDatabase
import com.example.passwordgenerator.data.entity.PasswordFolderEntity.Companion.toEntity
import com.example.passwordgenerator.domain.model.PasswordFolder
import com.example.passwordgenerator.domain.repository.PasswordFolderRepository

class PasswordFolderRepositoryImpl(
    private val application: Application
) : PasswordFolderRepository {

    private val folderDao = AppDatabase.getInstance(application).passwordFolderDao()

    override suspend fun insertFolder(folder: PasswordFolder): Long {
        return folderDao.insert(folder.toEntity())
    }

    override suspend fun getAllFolders(): List<PasswordFolder> {
        TODO("Not yet implemented")
    }

    override suspend fun deleteAllFolders() {
        TODO("Not yet implemented")
    }
}