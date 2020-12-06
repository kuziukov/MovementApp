package com.example.movementapp.models

import com.example.movementapp.database.entity.MessageEntity

class MessagesResponse {
    var code: Int? = null
    var status: String? = null
    var result: Messages = Messages()
}

class Messages {
    var totals: Int? = null
    var items: ArrayList<MessageEntity> = ArrayList()
}
