package com.example.passwordgenerator.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.passwordgenerator.domain.model.Password

@Entity(tableName = "passwords")
data class PasswordEntity(
    @PrimaryKey(autoGenerate = true) val id: Int,
    val value: String,
    val entropy: Double,
    val characterSet: String,
    @ColumnInfo(name = "folder_id") val folderId: Int
) {
    companion object{
        fun Password.toEntity(): PasswordEntity {
            return PasswordEntity(id, value, entropy, characterSet, 0)
        }

        fun PasswordEntity.toDomainModel(): Password {
            return Password(id, value, entropy, characterSet)
        }
    }


}