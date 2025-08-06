package com.example.passwordgenerator.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import com.example.passwordgenerator.domain.model.Password

@Entity(tableName = "passwords")
data class PasswordEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val value: String,
    val entropy: Double,
    val folderId: Long? = null
) {
    companion object {
        fun Password.toEntity(): PasswordEntity {
            return PasswordEntity(id, value, entropy, 0)
        }

        fun PasswordEntity.toDomainModel(): Password {
            return Password(value, entropy, folderId)
        }
    }


}