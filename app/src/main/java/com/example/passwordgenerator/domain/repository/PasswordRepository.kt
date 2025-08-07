package com.example.passwordgenerator.domain.repository


import com.example.passwordgenerator.domain.model.Password

interface PasswordRepository {

    suspend fun insert(password: Password): Long
    suspend fun insertAll(passwords: List<Password>)
    suspend fun getByFolder(folderId: Long): List<Password>
    suspend fun getGenerated(): List<Password>
    suspend fun delete(password: Password)
}

