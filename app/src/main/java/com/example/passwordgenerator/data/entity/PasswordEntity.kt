package com.example.passwordgenerator.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.passwordgenerator.domain.model.Password

@Entity(tableName = "passwords")
data class PasswordEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val value: String,
    val entropy: Double,
    val characterSet: String?,
    val folderId: Long? = null
) {
    companion object {
        fun Password.toEntity(): PasswordEntity {
            return PasswordEntity(
                id = id,
                value = value,
                entropy = entropy,
                characterSet = characterSet,
                folderId = folderId
            )
        }

        fun PasswordEntity.toDomainModel(): Password {
            return Password(
                value = value,
                entropy = entropy,
                characterSet = characterSet,
                folderId = folderId,
                id = id
            )
        }
    }


}