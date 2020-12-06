package com.example.movementapp.database.repository

import android.app.Activity
import android.app.Application
import androidx.lifecycle.LiveData
import com.example.movementapp.database.ChatDatabase
import com.example.movementapp.database.dao.ChatsDao
import com.example.movementapp.database.entity.ChatEntity
import com.example.movementapp.utils.subscribeOnBackground

class ChatsRepository(application: Application) {

    private var chatsDao: ChatsDao
    private var allChats: LiveData<List<ChatEntity>>

    private val database = ChatDatabase.getInstance(application)

    init {
        chatsDao = database.chatsDao()
        allChats = chatsDao.getAllChats()
    }

    fun insert(note: ChatEntity) {
        subscribeOnBackground {
            chatsDao.insert(note)
        }
    }

    fun update(note: ChatEntity) {
        subscribeOnBackground {
            chatsDao.update(note)
        }
    }

    fun delete(note: ChatEntity) {
        subscribeOnBackground {
            chatsDao.delete(note)
        }
    }

    fun deleteAllNotes() {
        subscribeOnBackground {
            chatsDao.deleteAllChats()
        }
    }

    fun getAllChats(): LiveData<List<ChatEntity>> {
        return allChats
    }



}