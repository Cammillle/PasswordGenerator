package com.example.passwordgenerator.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import com.example.passwordgenerator.domain.model.Password

@Entity(
    tableName = "passwords",
    foreignKeys = [ForeignKey(
        entity = PasswordFolderEntity::class,
        parentColumns = ["id"],
        childColumns = ["folderId"],
        onDelete = ForeignKey.CASCADE
    )],
    indices = [Index("folderId")]
)
data class PasswordEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val value: String,
    val entropy: Double,
    @ColumnInfo(name = "folder_id") val folderId: Long?
) {
    companion object {
        fun Password.toEntity(): PasswordEntity {
            return PasswordEntity(id.toLong(), value, entropy, 0)
        }

        fun PasswordEntity.toDomainModel(): Password {
            return Password(id.toInt(), value, entropy)
        }
    }


}