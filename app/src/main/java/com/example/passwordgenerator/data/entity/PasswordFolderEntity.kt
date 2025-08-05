package com.example.passwordgenerator.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.passwordgenerator.domain.model.PasswordFolder

@Entity(tableName = "password_folders")
data class PasswordFolderEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val name: String
) {
    companion object{
        fun PasswordFolder.toEntity(): PasswordFolderEntity {
            return PasswordFolderEntity(id, name)
        }

        fun PasswordFolderEntity.toDomainModel(): PasswordFolder {
            return PasswordFolder(id, name)
        }
    }
}