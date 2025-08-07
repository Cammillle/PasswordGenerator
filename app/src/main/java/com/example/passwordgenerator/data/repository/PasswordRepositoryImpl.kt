package com.example.passwordgenerator.data.repository

import android.app.Application
import com.example.passwordgenerator.data.AppDatabase
import com.example.passwordgenerator.data.entity.PasswordEntity.Companion.toDomainModel
import com.example.passwordgenerator.data.entity.PasswordEntity.Companion.toEntity
import com.example.passwordgenerator.domain.model.Password
import com.example.passwordgenerator.domain.repository.PasswordRepository
import javax.inject.Inject

class PasswordRepositoryImpl @Inject constructor(
    application: Application
) : PasswordRepository {

    private val passwordDao = AppDatabase.getInstance(application).passwordDao()

    override suspend fun insert(password: Password): Long {
        return passwordDao.insert(password.toEntity())
    }

    override suspend fun insertAll(passwords: List<Password>) {
        passwordDao.insertAll(passwords.map { it.toEntity() })
    }

    override suspend fun getByFolder(folderId: Long): List<Password> {
        return passwordDao.getByFolder(folderId).map { it.toDomainModel() }
    }

    override suspend fun getGenerated(): List<Password> {
        return passwordDao.getGenerated().map { it.toDomainModel() }
    }

    override suspend fun delete(password: Password) {
        return passwordDao.delete(password.toEntity())
    }
}