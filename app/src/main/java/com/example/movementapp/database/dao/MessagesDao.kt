package com.example.movementapp.database.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.movementapp.database.entity.ChatEntity
import com.example.movementapp.database.entity.MessageEntity

@Dao
interface MessagesDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(message: MessageEntity)

    @Update
    fun update(message: MessageEntity)

    @Delete
    fun delete(message: MessageEntity)

    @Query("delete from messages")
    fun deleteAllMessagesOfChat()

    @Query("select * from messages where messages.m_chat_chat_id = :id")
    fun getAllMessagesFromChat(id: String): LiveData<List<MessageEntity>>
}