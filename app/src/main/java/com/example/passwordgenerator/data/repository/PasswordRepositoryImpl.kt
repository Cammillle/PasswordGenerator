package com.example.passwordgenerator.data.repository

import android.app.Application
import com.example.passwordgenerator.data.AppDatabase
import com.example.passwordgenerator.data.entity.PasswordEntity.Companion.toEntity
import com.example.passwordgenerator.domain.model.Password
import com.example.passwordgenerator.domain.repository.PasswordRepository

class PasswordRepositoryImpl(
    private val application: Application
) : PasswordRepository {

    private val passwordDao = AppDatabase.getInstance(application).passwordDao()

    override suspend fun insert(password: Password): Long {
        return passwordDao.insert(password.toEntity())
    }

    override suspend fun insertAll(passwords: List<Password>) {
        passwordDao.insertAll(passwords.map { it.toEntity() })
    }

    override suspend fun getAll(): List<Password> {
        TODO("Not yet implemented")
    }

    override suspend fun getByFolder(folderId: Long): List<Password> {
        TODO("Not yet implemented")
    }

    override suspend fun getGenerated(): List<Password> {
        TODO("Not yet implemented")
    }

    override suspend fun delete(password: Password) {
        TODO("Not yet implemented")
    }

    override suspend fun deleteAll() {
        TODO("Not yet implemented")
    }


}