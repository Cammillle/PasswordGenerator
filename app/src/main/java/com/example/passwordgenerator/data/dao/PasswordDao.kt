package com.example.passwordgenerator.data.dao

import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.passwordgenerator.data.entity.PasswordEntity

@Dao
interface PasswordDao {

    @Query("SELECT * FROM passwords")
    fun getAllPasswords(): LiveData<List<PasswordEntity>>

    @Query("SELECT * FROM passwords WHERE folder_id =:folderId")
    fun getPasswordsByFolder(folderId: Int): LiveData<List<PasswordEntity>>

    @Insert
    suspend fun insertPassword(password: PasswordEntity): Long

    @Query("DELETE FROM passwords where id =:passwordId")
    suspend fun deletePassword(passwordId: Int)


    //suspend fun exportPasswordsToFile(passwords: List<PasswordEntity>, fileUri: Uri) //cursor в dao

    //suspend fun importPasswordsFromFile(fileUri: Uri): List<PasswordEntity> //cursor dao


}