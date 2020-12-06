package com.example.movementapp.ui.messages

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.movementapp.database.entity.MessageEntity
import com.example.movementapp.database.repository.MessagesRepository


class MessagesViewModelFactory(private val mApplication: Application, private val chat_id: String) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return MessagesViewModel(mApplication, chat_id) as T
    }

}

class MessagesViewModel constructor(application: Application, chat_id: String) : ViewModel() {

    private val repository = MessagesRepository(application, chat_id)
    private val allMessages = repository.getAllChatsMessagesOfChat(chat_id)

    fun insert(message: MessageEntity) {
        repository.insert(message)
    }

    fun update(message: MessageEntity) {
        repository.update(message)
    }

    fun delete(message: MessageEntity) {
        repository.delete(message)
    }

    fun deleteAllNotes() {

    }

    fun getAllChats(): LiveData<List<MessageEntity>> {
        return allMessages
    }
}
