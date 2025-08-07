package com.example.passwordgenerator.di

import android.app.Application
import com.example.passwordgenerator.data.AppDatabase
import com.example.passwordgenerator.data.dao.PasswordDao
import com.example.passwordgenerator.data.dao.PasswordFolderDao
import com.example.passwordgenerator.data.repository.PasswordFolderRepositoryImpl
import com.example.passwordgenerator.data.repository.PasswordRepositoryImpl
import com.example.passwordgenerator.domain.repository.PasswordFolderRepository
import com.example.passwordgenerator.domain.repository.PasswordRepository
import dagger.Binds
import dagger.Module
import dagger.Provides

@Module
interface DataModule {

    @ApplicationScope
    @Binds
    fun bindPasswordRepository(impl: PasswordRepositoryImpl): PasswordRepository

    @ApplicationScope
    @Binds
    fun bindPasswordFolderRepository(impl: PasswordFolderRepositoryImpl): PasswordFolderRepository

    companion object {
        @ApplicationScope
        @Provides
        fun providePasswordDao(app: Application): PasswordDao {
            return AppDatabase.getInstance(app).passwordDao()
        }

        @ApplicationScope
        @Provides
        fun providePasswordFolderDao(app: Application): PasswordFolderDao {
            return AppDatabase.getInstance(app).passwordFolderDao()
        }
    }
}