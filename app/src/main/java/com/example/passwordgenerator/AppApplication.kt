package com.example.passwordgenerator

import android.app.Application
import com.example.passwordgenerator.di.DaggerApplicationComponent

class AppApplication : Application() {
    val component by lazy {
        DaggerApplicationComponent.factory().create(this)
    }
}