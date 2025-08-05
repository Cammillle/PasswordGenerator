package com.example.passwordgenerator.domain.repository

import android.net.Uri
import androidx.lifecycle.LiveData
import com.example.passwordgenerator.domain.model.Password

interface PasswordRepository {

    fun getAllPasswords():LiveData<List<Password>>

    fun getPasswordsByFolder(folderId: Int): LiveData<List<Password>>

    suspend fun savePassword(password: Password): Int

    suspend fun deletePassword(passwordId: Int)

    suspend fun exportPasswordsToFile(passwords: List<Password>, fileName:String) //cursor в dao

    suspend fun importPasswordsFromFile(fileName:String): List<Password> //cursor dao

    suspend fun deletePasswordFile(fileName:String)

}

