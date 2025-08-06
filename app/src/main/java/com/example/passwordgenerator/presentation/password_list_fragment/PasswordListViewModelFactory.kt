package com.example.passwordgenerator.presentation.password_list_fragment

import android.app.Application
import android.content.ClipboardManager
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class PasswordListViewModelFactory(
    private val application: Application,
    private val clipboardManager: ClipboardManager
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(PasswordListViewModel::class.java)) {
            return PasswordListViewModel(application, clipboardManager) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}