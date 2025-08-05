package com.example.passwordgenerator.data

import android.content.ContentProvider
import android.content.ContentValues
import android.content.UriMatcher
import android.database.Cursor
import android.database.MatrixCursor
import android.net.Uri
import android.os.ParcelFileDescriptor
import java.io.File

class PasswordProvider : ContentProvider() {

    override fun onCreate(): Boolean {
        return true
    }
    private val uriMatcher = UriMatcher(UriMatcher.NO_MATCH).apply {
        addURI(
            AUTHORITY,
            "passwords",
            GET_PASSWORDS_QUERY
        )
    }


    override fun query(
        uri: Uri,
        projection: Array<out String>?,
        selection: String?,
        selectionArgs: Array<out String>?,
        sortOrder: String?
    ): Cursor? {
        return null
    }

    override fun getType(uri: Uri): String? {
        return "text/plain"
    }

    override fun insert(uri: Uri, values: ContentValues?): Uri? {
        return null
    }

    override fun delete(uri: Uri, selection: String?, selectionArgs: Array<out String>?): Int {
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

    override fun openFile(uri: Uri, mode: String): ParcelFileDescriptor? {
        val context = context ?: return null
        return context.contentResolver.openFileDescriptor(uri,mode)
    }

    companion object {
        const val AUTHORITY = "com.example.passwordgenerator"
        val CONTENT_URI: Uri = Uri.parse("content://$AUTHORITY/passwords")

        const val GET_PASSWORDS_QUERY = 100


    }
}