package com.example.movementapp.models

import com.example.movementapp.database.entity.ChatEntity

class Chats {
    var totals: Int? = null
    var items: ArrayList<ChatEntity> = ArrayList()
}

class ChatsResponse {
    var code: Int? = null
    var status: String? = null
    var result: Chats = Chats()
}
