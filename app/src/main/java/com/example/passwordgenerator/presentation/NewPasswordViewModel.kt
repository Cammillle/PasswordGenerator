package com.example.passwordgenerator.presentation

import android.app.Application
import android.content.Context
import android.net.Uri
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.passwordgenerator.data.repository.PasswordFolderRepositoryImpl
import com.example.passwordgenerator.data.repository.PasswordRepositoryImpl
import com.example.passwordgenerator.domain.model.Password
import com.example.passwordgenerator.domain.model.PasswordFolder
import com.example.passwordgenerator.domain.usecase.InsertFolderUseCase
import com.example.passwordgenerator.domain.usecase.InsertPasswordUseCase
import com.example.passwordgenerator.domain.usecase.InsertPasswordsUseCase
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlin.math.log2


class NewPasswordViewModel(
    application: Application
) : AndroidViewModel(application) {

    private val _generatedPassword = MutableStateFlow("")
    val generatedPassword: StateFlow<String> = _generatedPassword.asStateFlow()

    private val _entropy = MutableStateFlow("")
    val entropy: StateFlow<String> = _entropy.asStateFlow()

    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    val lowercase = "abcdefghijklmnopqrstuvwxyz"   //создать строковые ресурсы
    val uppercase = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
    val numbers = "0123456789"
    val special = "!@#$%^&*()-_=+[]{}|;:,.<>?/"

    private val passwordRepository = PasswordRepositoryImpl(application)
    private val folderRepository = PasswordFolderRepositoryImpl(application)

    private val insertPasswordUseCase = InsertPasswordUseCase(passwordRepository)
    private val insertPasswordsUseCase = InsertPasswordsUseCase(passwordRepository)

    private val insertFolderUseCase = InsertFolderUseCase(folderRepository)

    fun generatePassword(
        length: Int,
        useUppercase: Boolean,
        useNumbers: Boolean,
        useSpecial: Boolean
    ) {


        var allowedChars = lowercase
        if (useUppercase) allowedChars += uppercase  //переделать контакенацию
        if (useNumbers) allowedChars += numbers
        if (useSpecial) allowedChars += special

        if (length <= 0) {
            viewModelScope.launch {
                _eventFlow.emit(UiEvent.Error("Введите длину пароля"))
            }
            return
        }

        val password = (1..length)
            .map { allowedChars.random() }
            .joinToString("")

        val entropyValue = password.length * log2(allowedChars.length.toDouble())

        _generatedPassword.value = password
        _entropy.value = String.format("%.2f", entropyValue)
    }

    fun saveGeneratedPassword() {
        val password = _generatedPassword.value
        val entropy = _entropy.value.toDoubleOrNull() ?: return
        /***???**/

        if (password.isBlank()) return

        viewModelScope.launch {
            try {
                val entity = Password(
                    value = password,
                    entropy = entropy,
                    folderId = null
                )
                insertPasswordUseCase(entity)  //Загрузка пароля в бд
                _eventFlow.emit(UiEvent.Success("Пароль сохранён"))
            } catch (e: Exception) {
                _eventFlow.emit(UiEvent.Error("Ошибка: ${e.message}"))
            }
        }
    }

    fun importPasswordsFromFile(context: Context, uri: Uri) {
        viewModelScope.launch {
            try {
                val lines = context.contentResolver.openInputStream(uri)
                    ?.bufferedReader()
                    ?.readLines()
                    ?.filter { it.isNotBlank() }
                    ?: emptyList()

                val folderId =
                    insertFolderUseCase(
                        PasswordFolder(
                            name = "Папка № ${System.currentTimeMillis()}"
                        )
                    )

                val passwordList = lines.map { line ->
                    val entropy = calculateEntropy(line)
                    Password(value = line, entropy = entropy, folderId = folderId)
                }

                //создать folder  и загрузить с паролями

                insertPasswordsUseCase(passwordList)
                _eventFlow.emit(UiEvent.Success("Пароли загружены"))
            } catch (e: Exception) {
                _eventFlow.emit(UiEvent.Error("Ошибка загрузки: ${e.message}"))
            }
        }
    }

    private fun calculateEntropy(password: String): Double {
        val poolSize = buildSet {
            if (password.any { it.isLowerCase() }) addAll('a'..'z')
            if (password.any { it.isUpperCase() }) addAll('A'..'Z')
            if (password.any { it.isDigit() }) addAll('0'..'9')
            if (password.any { special.contains(it) })
                addAll(special.toSet())
        }.size

        return String.format("%.2f", password.length * log2(poolSize.toDouble())).toDouble()
    }

    sealed class UiEvent {
        data class Success(val message: String) : UiEvent()
        data class Error(val message: String) : UiEvent()
    }

}