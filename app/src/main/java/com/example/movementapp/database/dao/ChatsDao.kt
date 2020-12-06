package com.example.movementapp.database.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.movementapp.database.entity.ChatEntity

@Dao
interface ChatsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(chat: ChatEntity)

    @Update
    fun update(note: ChatEntity)

    @Delete
    fun delete(note: ChatEntity)

    @Query("delete from chats")
    fun deleteAllChats()

    @Query("select * from chats")
    fun getAllChats(): LiveData<List<ChatEntity>>
}