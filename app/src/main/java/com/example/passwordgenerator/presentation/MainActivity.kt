package com.example.passwordgenerator.presentation

import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import android.view.Menu
import android.view.MenuItem
import androidx.lifecycle.lifecycleScope
import com.example.passwordgenerator.R
import com.example.passwordgenerator.data.AppDatabase
import com.example.passwordgenerator.data.repository.PasswordRepositoryImpl
import com.example.passwordgenerator.databinding.ActivityMainBinding
import com.example.passwordgenerator.domain.model.Password
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    //private val passwordDao = AppDatabase.getInstance(application).passwordDao()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navController = findNavController(R.id.nav_host_fragment_content_main)

        val passwordRepository = PasswordRepositoryImpl(application)
        val passwords = listOf(Password(1, "password1", 0.0, ""), Password(2, "password2", 0.0, ""))

        lifecycleScope.launch {
            passwordRepository.exportPasswordsToFile(passwords, "passwords.txt")
            //val importedPasswords = passwordRepository.importPasswordsFromFile("passwords.txt")
            //passwordRepository.deletePasswordFile("passwords.txt")
        }




    }


}