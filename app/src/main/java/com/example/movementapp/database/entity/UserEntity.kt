package com.example.movementapp.database.entity

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "users")
data class UserEntity(
    @PrimaryKey(autoGenerate = false) val _id: String,
    val name: String,
    val surname: String,
    val phone: String? = null,
    val region_code: String? = null
)
