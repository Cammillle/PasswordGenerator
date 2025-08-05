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
    private val application: Application
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

    override suspend fun savePassword(password: Password): Long {
        return passwordDao.insertPassword(password.toEntity())
    }

    override suspend fun deletePassword(passwordId: Int) {
        passwordDao.deletePassword(passwordId)
    }

    override suspend fun exportPasswordsToFile(passwords: List<Password>, fileName: String) {
        val uri = Uri.parse("content://com.example.passwordgenerator/passwords/$fileName")
        val outputStream = application.contentResolver.openOutputStream(uri)
        outputStream?.use { os ->
            passwords.forEach { password ->
                os.write("${password.value}\n".toByteArray())
            }
        }
    }

    override suspend fun importPasswordsFromFile(fileName: String): List<Password> {
        val uri = Uri.parse("content://com.example.passwordgenerator/passwords/$fileName")
        val inputStream = application.contentResolver.openInputStream(uri)
        return inputStream?.use { isr ->
            isr.bufferedReader().readLines().map { line ->
                Password(0, line, 0.0, "")
            }
        } ?: emptyList()
    }

    override suspend fun deletePasswordFile(fileName: String) {
        val uri = Uri.parse("content://com.example.passwordgenerator/passwords/$fileName")
        application.contentResolver.delete(uri, null, null)
    }


}