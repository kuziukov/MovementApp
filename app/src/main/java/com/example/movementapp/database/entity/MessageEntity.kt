package com.example.movementapp.database.entity

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "messages")
data class MessageEntity(
    @PrimaryKey(autoGenerate = false) var _id: String,
    var message: String,
    @Embedded(prefix = "m_chat_") var chat: ChatEntity,
    @Embedded(prefix = "m_user_") var user: UserEntity,
    var created_at: Double? = null
)
