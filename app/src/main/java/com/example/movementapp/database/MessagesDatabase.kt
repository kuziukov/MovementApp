package com.example.movementapp.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.movementapp.database.dao.ChatsDao
import com.example.movementapp.database.dao.MessagesDao
import com.example.movementapp.database.entity.ChatEntity
import com.example.movementapp.database.entity.MessageEntity
import com.example.movementapp.utils.subscribeOnBackground

@Database(entities = [MessageEntity::class], version = 7)
abstract class MessagesDatabase : RoomDatabase() {

    abstract fun messagesDao(): MessagesDao

    companion object {
        private var instance: MessagesDatabase? = null

        @Synchronized
        fun getInstance(ctx: Context): MessagesDatabase {
            if(instance == null)
                instance = Room.databaseBuilder(ctx.applicationContext, MessagesDatabase::class.java,
                    "messages_database")
                    .fallbackToDestructiveMigration()
                    .addCallback(roomCallback)
                    .build()

            return instance!!

        }

        private val roomCallback = object : Callback() {
            override fun onCreate(db: SupportSQLiteDatabase) {
                super.onCreate(db)
                //populateDatabase(instance!!)
            }
        }

        private fun populateDatabase(db: MessagesDatabase) {
            val chatDao = db.messagesDao()
            subscribeOnBackground {
                //chatDao.insert(ChatEntity(null, "desc 1", "dsadasdassa"))
            }
        }
    }



}