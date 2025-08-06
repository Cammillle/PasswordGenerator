package com.example.passwordgenerator.domain.model

data class Password(
    val value: String,
    val entropy: Double,
    val folderId:Long?,
    val id: Long = UNDEFINED_ID
){
    companion object{
        const val UNDEFINED_ID = 0L
    }
}
