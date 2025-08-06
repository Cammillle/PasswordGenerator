package com.example.passwordgenerator.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.passwordgenerator.domain.model.PasswordFolder

@Entity(tableName = "folders")
data class PasswordFolderEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val name: String
) {
    companion object{
        fun PasswordFolder.toEntity(): PasswordFolderEntity {
            return PasswordFolderEntity(id.toLong(), name)
        }

        fun PasswordFolderEntity.toDomainModel(): PasswordFolder {
            return PasswordFolder(name, id)
        }
    }
}