package com.example.passwordgenerator.data

import android.content.ContentProvider
import android.content.ContentValues
import android.content.Context
import android.content.UriMatcher
import android.database.Cursor
import android.database.MatrixCursor
import android.net.Uri
import android.os.ParcelFileDescriptor
import com.example.passwordgenerator.data.dao.PasswordDao
import java.io.File

class PasswordProvider : ContentProvider() {
    private lateinit var context: Context

    private val uriMatcher = UriMatcher(UriMatcher.NO_MATCH).apply {
        addURI(
            AUTHORITY,
            "passwords",
            GET_PASSWORDS_QUERY
        )
    }

    override fun onCreate(): Boolean {
        context = context!!
        return true
    }

    override fun openFile(uri: Uri, mode: String): ParcelFileDescriptor? {
        val file = getFile(uri)
        return ParcelFileDescriptor.open(file, ParcelFileDescriptor.MODE_READ_WRITE)
    }

    private fun getFile(uri: Uri): File {
        val fileName = uri.lastPathSegment!!
        return File(context.filesDir, fileName)
    }


    override fun query(
        uri: Uri,
        projection: Array<out String>?,
        selection: String?,
        selectionArgs: Array<out String>?,
        sortOrder: String?
    ): Cursor? {
        if (uri.path == "/passwords") {
            val cursor = MatrixCursor(arrayOf("_display_name", "_size"))
            val file = getFile(uri)
            cursor.addRow(arrayOf(file.name, file.length()))
            return cursor
        }
        return null
    }

    override fun getType(uri: Uri): String? {
        return "text/plain"
    }

    override fun insert(uri: Uri, values: ContentValues?): Uri? {
        return null
    }

    override fun delete(uri: Uri, selection: String?, selectionArgs: Array<out String>?): Int {
        val file = getFile(uri)
        if (file.exists()) {
            file.delete()
            return 1
        }
        return 0
    }

    override fun update(
        uri: Uri,
        values: ContentValues?,
        selection: String?,
        selectionArgs: Array<out String>?
    ): Int {
        return 0
    }

    companion object {
        const val AUTHORITY = "com.example.passwordgenerator"
        val CONTENT_URI: Uri = Uri.parse("content://$AUTHORITY")

        const val GET_PASSWORDS_QUERY = 100


    }
}