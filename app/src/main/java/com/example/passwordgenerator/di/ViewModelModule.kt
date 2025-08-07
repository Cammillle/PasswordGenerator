package com.example.passwordgenerator.di

import android.app.Application
import com.example.passwordgenerator.domain.usecase.DeletePasswordUseCase
import com.example.passwordgenerator.domain.usecase.GetAllFoldersUseCase
import com.example.passwordgenerator.domain.usecase.GetGeneratedPasswordsUseCase
import com.example.passwordgenerator.domain.usecase.GetPasswordsByFolderIdUseCase
import com.example.passwordgenerator.domain.usecase.InsertFolderUseCase
import com.example.passwordgenerator.domain.usecase.InsertPasswordUseCase
import com.example.passwordgenerator.domain.usecase.InsertPasswordsUseCase
import com.example.passwordgenerator.presentation.new_password_fragment.NewPasswordViewModelFactory
import com.example.passwordgenerator.presentation.password_list_fragment.PasswordListViewModelFactory
import dagger.Module
import dagger.Provides

@Module
class ViewModelModule {

    @Provides
    fun providePasswordListViewModelFactory(
        application: Application,
        getGeneratedPasswordsUseCase: GetGeneratedPasswordsUseCase,
        deletePasswordUseCase: DeletePasswordUseCase,
        getAllFoldersUseCase: GetAllFoldersUseCase,
        getPasswordsByFolderIdUseCase: GetPasswordsByFolderIdUseCase
    ): PasswordListViewModelFactory {
        return PasswordListViewModelFactory(
            application,
            getGeneratedPasswordsUseCase,
            deletePasswordUseCase,
            getAllFoldersUseCase,
            getPasswordsByFolderIdUseCase
        )
    }

    @Provides
    fun provideNewPasswordViewModelFactory(
        application: Application,
        insertPasswordUseCase: InsertPasswordUseCase,
        insertPasswordsUseCase: InsertPasswordsUseCase,
        insertFolderUseCase: InsertFolderUseCase
    ): NewPasswordViewModelFactory {
        return NewPasswordViewModelFactory(
            application,
            insertPasswordUseCase,
            insertPasswordsUseCase,
            insertFolderUseCase
        )
    }


}