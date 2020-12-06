package com.example.movementapp.database.entity

import androidx.annotation.NonNull
import androidx.room.*
import com.google.gson.annotations.SerializedName


@Entity(tableName = "chats")
data class ChatEntity(
    @ColumnInfo(name = "chat_id")
    @SerializedName("_id")
    @PrimaryKey(autoGenerate = false) var _id: String,
    var name: String,
    @Embedded(prefix = "last_") var last_message: LastMessage? = null

)


data class LastMessage(
    var _id: String,
    var message: String,
    @Embedded(prefix = "lastUser_") var user: UserEntity,
    var created_at: Double? = null
)
