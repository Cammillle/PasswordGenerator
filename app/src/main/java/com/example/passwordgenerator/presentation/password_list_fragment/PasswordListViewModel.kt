package com.example.passwordgenerator.presentation.password_list_fragment

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.passwordgenerator.domain.model.Password
import com.example.passwordgenerator.domain.model.PasswordFolder
import com.example.passwordgenerator.presentation.PasswordListUiState
import com.example.passwordgenerator.domain.usecase.DeletePasswordUseCase
import com.example.passwordgenerator.domain.usecase.GetAllFoldersUseCase
import com.example.passwordgenerator.domain.usecase.GetGeneratedPasswordsUseCase
import com.example.passwordgenerator.domain.usecase.GetPasswordsByFolderIdUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class PasswordListViewModel(
    context: Application,
    private val getGeneratedPasswordsUseCase: GetGeneratedPasswordsUseCase,
    private val deletePasswordUseCase: DeletePasswordUseCase,
    private val getAllFoldersUseCase:GetAllFoldersUseCase,
    private val getPasswordsByFolderIdUseCase:GetPasswordsByFolderIdUseCase
) : AndroidViewModel(context) {

    private val _uiState = MutableStateFlow<PasswordListUiState>(PasswordListUiState.Loading)
    val uiState: StateFlow<PasswordListUiState> = _uiState.asStateFlow()

    private var currentFolder: PasswordFolder? = null

    init {
        loadRoot()
    }

    fun loadRoot() {
        viewModelScope.launch {
            _uiState.value = PasswordListUiState.Loading
            val folders = getAllFoldersUseCase()
            val passwords = getGeneratedPasswordsUseCase()
            currentFolder = null
            _uiState.value = PasswordListUiState.Root(folders, passwords)
        }
    }


    fun openFolder(folder: PasswordFolder) {
        viewModelScope.launch {
            val passwords = getPasswordsByFolderIdUseCase(folder.id)
            currentFolder = folder
            _uiState.value = PasswordListUiState.FolderContent(folder, passwords)
        }
    }

    fun onBackPressed() {
        if (currentFolder != null) {
            loadRoot()
        }
    }

    fun deletePassword(password: Password) {
        viewModelScope.launch {
            Log.d("TEST_TEST", password.toString())
            deletePasswordUseCase(password)
            refreshCurrentState()
        }
    }


    private fun refreshCurrentState() {
        if (currentFolder == null) {
            loadRoot()
        } else {
            openFolder(currentFolder!!)
        }
    }

    fun exportPasswords(onFileReady: (List<String>) -> Unit) {
        viewModelScope.launch {
            val exportData: List<Password> = when (val state = _uiState.value) {
                is PasswordListUiState.Root -> state.passwords
                is PasswordListUiState.FolderContent -> state.passwords
                else -> emptyList()
            }
            val lines = exportData.map { it.value }
            onFileReady(lines)
        }
    }


}