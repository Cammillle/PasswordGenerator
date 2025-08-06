package com.example.passwordgenerator.domain.model

data class PasswordFolder(
    val name: String,
    val id: Long = UNDEFINED_ID
) {
    companion object {
        const val UNDEFINED_ID = 0L
    }
}
