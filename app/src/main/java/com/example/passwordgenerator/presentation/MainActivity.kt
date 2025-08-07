package com.example.passwordgenerator.presentation

import android.content.ContentValues
import android.content.Context
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.passwordgenerator.databinding.ActivityMainBinding
import kotlinx.coroutines.launch
import androidx.core.content.edit

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        /*Пример файлов с паролями загружены в /assets
        * При первом запуске приложения файлы копируются в MediaStore*/

        lifecycleScope.launch {
            if (applicationContext.isFirstRun()) {
                applicationContext.copyAssetToMediaStoreIfNotExists(FILE1)
                applicationContext.copyAssetToMediaStoreIfNotExists(FILE2)
                applicationContext.copyAssetToMediaStoreIfNotExists(FILE3)
            }
        }
    }

    private suspend fun Context.copyAssetToMediaStoreIfNotExists(fileName: String) {
        val resolver = contentResolver

        // Проверка, существует ли уже файл
        val projection = arrayOf(MediaStore.Files.FileColumns.DISPLAY_NAME)
        val selection = "${MediaStore.Files.FileColumns.DISPLAY_NAME} = ?"
        val selectionArgs = arrayOf(fileName)

        val queryUri = MediaStore.Files.getContentUri("external")
        val cursor = resolver.query(queryUri, projection, selection, selectionArgs, null)

        if (cursor?.moveToFirst() == true) {
            cursor.close()
            return // Файл уже существует
        }
        cursor?.close()

        val contentValues = ContentValues().apply {
            put(MediaStore.Files.FileColumns.DISPLAY_NAME, fileName)
            put(MediaStore.Files.FileColumns.MIME_TYPE, "text/plain")
            put(
                MediaStore.Files.FileColumns.RELATIVE_PATH,
                Environment.DIRECTORY_DOCUMENTS + "/YourApp"
            ) // Подпапка в Documents
        }

        val uri = resolver.insert(
            MediaStore.Files.getContentUri("external"), contentValues
        )

        uri?.let {
            assets.open(fileName).use { input ->
                resolver.openOutputStream(it)?.use { output ->
                    input.copyTo(output)
                }
            }
        }
    }

    private fun Context.isFirstRun(): Boolean {
        val prefs = getSharedPreferences(INIT_PREFS, Context.MODE_PRIVATE)
        val isFirst = prefs.getBoolean(FIRST_RUN, true)
        if (isFirst) {
            prefs.edit { putBoolean(FIRST_RUN, false) }
        }
        return isFirst


    }

    companion object {
        private const val FILE1 = "passwords1.txt"
        private const val FILE2 = "passwords2.txt"
        private const val FILE3 = "passwords3.txt"

        private const val INIT_PREFS = "init_prefs"
        private const val FIRST_RUN = "first_run"

    }

}