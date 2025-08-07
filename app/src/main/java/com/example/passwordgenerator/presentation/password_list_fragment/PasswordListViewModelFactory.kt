package com.example.passwordgenerator.presentation.password_list_fragment

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.passwordgenerator.domain.usecase.DeletePasswordUseCase
import com.example.passwordgenerator.domain.usecase.GetAllFoldersUseCase
import com.example.passwordgenerator.domain.usecase.GetGeneratedPasswordsUseCase
import com.example.passwordgenerator.domain.usecase.GetPasswordsByFolderIdUseCase
import javax.inject.Inject

class PasswordListViewModelFactory @Inject constructor(
    private val application: Application,
    private val getGeneratedPasswordsUseCase: GetGeneratedPasswordsUseCase,
    private val deletePasswordUseCase: DeletePasswordUseCase,
    private val getAllFoldersUseCase: GetAllFoldersUseCase,
    private val getPasswordsByFolderIdUseCase: GetPasswordsByFolderIdUseCase
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(PasswordListViewModel::class.java)) {
            return PasswordListViewModel(
                application,
                getGeneratedPasswordsUseCase,
                deletePasswordUseCase,
                getAllFoldersUseCase,
                getPasswordsByFolderIdUseCase
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}