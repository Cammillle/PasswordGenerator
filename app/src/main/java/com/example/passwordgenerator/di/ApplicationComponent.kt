package com.example.passwordgenerator.di

import android.app.Application
import com.example.passwordgenerator.presentation.new_password_fragment.NewPasswordFragment
import com.example.passwordgenerator.presentation.password_list_fragment.PasswordListFragment
import dagger.BindsInstance
import dagger.Component

@Component(modules = [DataModule::class, ViewModelModule::class])
interface ApplicationComponent {

    fun inject(fragment: NewPasswordFragment)

    fun inject(fragment: PasswordListFragment)

    @Component.Factory
    interface Factory {

        fun create(@BindsInstance application: Application): ApplicationComponent
    }

}