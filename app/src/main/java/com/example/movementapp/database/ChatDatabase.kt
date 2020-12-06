package com.example.movementapp.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.movementapp.database.dao.ChatsDao
import com.example.movementapp.database.entity.ChatEntity
import com.example.movementapp.utils.subscribeOnBackground

@Database(entities = [ChatEntity::class], version = 9)
abstract class ChatDatabase : RoomDatabase() {

    abstract fun chatsDao(): ChatsDao

    companion object {
        private var instance: ChatDatabase? = null

        @Synchronized
        fun getInstance(ctx: Context): ChatDatabase {
            if(instance == null)
                instance = Room.databaseBuilder(ctx.applicationContext, ChatDatabase::class.java,
                    "chats_database")
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

        private fun populateDatabase(db: ChatDatabase) {
            val chatDao = db.chatsDao()
            subscribeOnBackground {
                //chatDao.insert(ChatEntity(null, "desc 1", "dsadasdassa"))
            }
        }
    }



}