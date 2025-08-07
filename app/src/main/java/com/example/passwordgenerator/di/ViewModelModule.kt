package com.example.passwordgenerator.di

import androidx.lifecycle.ViewModel
import com.example.passwordgenerator.presentation.new_password_fragment.NewPasswordViewModel
import com.example.passwordgenerator.presentation.password_list_fragment.PasswordListViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
interface ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(NewPasswordViewModel::class)
    fun bindMainViewModel(viewModel: NewPasswordViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(PasswordListViewModel::class)
    fun bindShopItemViewModel(viewModel: PasswordListViewModel): ViewModel


}