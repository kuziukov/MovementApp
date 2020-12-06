package com.example.movementapp.ui.chats

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.movementapp.database.entity.ChatEntity
import com.example.movementapp.database.repository.ChatsRepository


class ChatsViewModelFactory(private val mApplication: Application) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return ChatsViewModel(mApplication) as T
    }

}

class ChatsViewModel constructor(application: Application) : ViewModel() {

    private val repository = ChatsRepository(application)
    private val allChats = repository.getAllChats()

    fun insert(chat: ChatEntity) {
        repository.insert(chat)
    }

    fun update(chat: ChatEntity) {
        repository.update(chat)
    }

    fun delete(chat: ChatEntity) {
        repository.delete(chat)
    }

    fun deleteAllNotes() {
        repository.deleteAllNotes()
    }

    fun getAllChats(): LiveData<List<ChatEntity>> {
        return allChats
    }

}