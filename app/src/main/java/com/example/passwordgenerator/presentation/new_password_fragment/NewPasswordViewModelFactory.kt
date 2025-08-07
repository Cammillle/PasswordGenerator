package com.example.passwordgenerator.presentation.new_password_fragment

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.passwordgenerator.domain.usecase.InsertFolderUseCase
import com.example.passwordgenerator.domain.usecase.InsertPasswordUseCase
import com.example.passwordgenerator.domain.usecase.InsertPasswordsUseCase
import javax.inject.Inject

class NewPasswordViewModelFactory @Inject constructor(
    private val application: Application,
    private val insertPasswordUseCase: InsertPasswordUseCase,
    private val insertPasswordsUseCase: InsertPasswordsUseCase,
    private val insertFolderUseCase: InsertFolderUseCase
    ) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(NewPasswordViewModel::class.java)) {
            return NewPasswordViewModel(
                application,
                insertPasswordUseCase,
                insertPasswordsUseCase,
                insertFolderUseCase
                            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}