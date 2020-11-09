package com.example.android.roomwordssample.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user_table")
class User(
        @field:ColumnInfo(name = "id")
        @field:PrimaryKey(autoGenerate = true)
        val id: Int? = null,
        @field:ColumnInfo(name = "email")
        val email: String,
        @field:ColumnInfo(name = "name")
        val name: String
)
