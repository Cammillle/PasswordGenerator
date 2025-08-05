package com.example.passwordgenerator.data.repository

import android.app.Application
import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.map
import com.example.passwordgenerator.data.AppDatabase
import com.example.passwordgenerator.data.entity.PasswordEntity.Companion.toDomainModel
import com.example.passwordgenerator.data.entity.PasswordEntity.Companion.toEntity
import com.example.passwordgenerator.domain.model.Password
import com.example.passwordgenerator.domain.repository.PasswordRepository

class PasswordRepositoryImpl(
    application: Application
) : PasswordRepository {

    private val passwordDao = AppDatabase.getInstance(application).passwordDao()

    override fun getAllPasswords(): LiveData<List<Password>> {
        return passwordDao.getAllPasswords().map { list ->
            list.map {
                it.toDomainModel()
            }
        }
    }

    override fun getPasswordsByFolder(folderId: Int): LiveData<List<Password>> {
        return passwordDao.getPasswordsByFolder(folderId).map { list ->
            list.map {
                it.toDomainModel()
            }
        }
    }

    override suspend fun savePassword(password: Password): Int {
        return passwordDao.insertPassword(password.toEntity())
    }

    override suspend fun deletePassword(passwordId: Int) {
        passwordDao.deletePassword(passwordId)
    }

    override suspend fun exportPasswordsToFile(passwords: List<Password>, fileUri: Uri) {
        TODO("Not yet implemented")
    }

    override suspend fun importPasswordsFromFile(fileUri: Uri): List<Password> {
        TODO("Not yet implemented")
    }
}