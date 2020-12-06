package com.example.movementapp.database.repository

import android.app.Activity
import android.app.Application
import androidx.lifecycle.LiveData
import com.example.movementapp.database.ChatDatabase
import com.example.movementapp.database.MessagesDatabase
import com.example.movementapp.database.dao.ChatsDao
import com.example.movementapp.database.dao.MessagesDao
import com.example.movementapp.database.entity.ChatEntity
import com.example.movementapp.database.entity.MessageEntity
import com.example.movementapp.utils.subscribeOnBackground

class MessagesRepository(application: Application, chat_id: String) {

    private var messagesDao: MessagesDao
    private var allMessages: LiveData<List<MessageEntity>>

    private val database = MessagesDatabase.getInstance(application)

    init {
        messagesDao = database.messagesDao()
        allMessages = messagesDao.getAllMessagesFromChat(chat_id)
    }

    fun insert(note: MessageEntity) {
        subscribeOnBackground {
            messagesDao.insert(note)
        }
    }

    fun update(note: MessageEntity) {
        subscribeOnBackground {
            messagesDao.update(note)
        }
    }

    fun delete(note: MessageEntity) {
        subscribeOnBackground {
            messagesDao.delete(note)
        }
    }

    fun deleteAllMessagesOfChat(id: String) {
        subscribeOnBackground {
            messagesDao.deleteAllMessagesOfChat()
        }
    }

    fun getAllChatsMessagesOfChat(id: String): LiveData<List<MessageEntity>> {
        return allMessages
    }



}