package com.example.passwordgenerator.presentation.new_password_fragment

import android.app.Application
import android.content.Context
import android.net.Uri
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.passwordgenerator.R
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
import javax.inject.Inject
import kotlin.math.log2

class NewPasswordViewModel @Inject constructor(
    application: Application,
    private val insertPasswordUseCase: InsertPasswordUseCase,
    private val insertPasswordsUseCase: InsertPasswordsUseCase,
    private val insertFolderUseCase: InsertFolderUseCase
) : AndroidViewModel(application) {

    private val _generatedPassword = MutableStateFlow("")
    val generatedPassword: StateFlow<String> = _generatedPassword.asStateFlow()

    private val _entropy = MutableStateFlow("")
    val entropy: StateFlow<String> = _entropy.asStateFlow()

    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    private val lowercase = application.getString(R.string.lower_case)
    private val uppercase = application.getString(R.string.upper_case)
    private val numbers = application.getString(R.string.numbers)
    private val special = application.getString(R.string.special)
    private var allowedChars = ""

    fun generatePassword(
        length: Int, useUppercase: Boolean, useNumbers: Boolean, useSpecial: Boolean
    ) {

        allowedChars = buildCharacterSet(useUppercase, useNumbers, useSpecial)

        if (length <= 0) {
            viewModelScope.launch {
                _eventFlow.emit(UiEvent.Error("Введите длину пароля"))
            }
            return
        }

        val password = (1..length).map { allowedChars.random() }.joinToString("")

        val entropyValue = password.length * log2(allowedChars.length.toDouble())

        _generatedPassword.value = password
        _entropy.value = String.format("%.2f", entropyValue)
    }

    private fun buildCharacterSet(
        useUppercase: Boolean, useNumbers: Boolean, useSpecial: Boolean
    ): String {
        val set = StringBuilder().append(lowercase)
        if (useUppercase) set.append(uppercase)
        if (useNumbers) set.append(numbers)
        if (useSpecial) set.append(special)
        return set.toString()
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
                    characterSet = allowedChars,
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
                val lines =
                    context.contentResolver.openInputStream(uri)?.bufferedReader()?.readLines()
                        ?.filter { it.isNotBlank() } ?: emptyList()

                val folderId =  //Загружаем папку
                    insertFolderUseCase(
                        PasswordFolder(
                            name = "Папка № ${System.currentTimeMillis()}"
                        )
                    )

                val passwordList = lines.map { line ->
                    val entropy = calculateEntropy(line)
                    Password(
                        value = line, entropy = entropy, characterSet = null, folderId = folderId
                    )
                }
                Log.d("TEST_TEST", "${passwordList.toList()}")
                insertPasswordsUseCase(passwordList)  //загружаем пароли и присваиваем им folderId

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
            if (password.any { special.contains(it) }) addAll(special.toSet())
        }.size

        return String.format("%.2f", password.length * log2(poolSize.toDouble())).toDouble()
    }

    sealed class UiEvent {
        data class Success(val message: String) : UiEvent()
        data class Error(val message: String) : UiEvent()
    }

}