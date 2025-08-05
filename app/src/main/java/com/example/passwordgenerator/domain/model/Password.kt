package com.example.passwordgenerator.domain.model

data class Password(
    val id: Int,
    val value: String,
    val entropy: Double,
    val characterSet: String
)
