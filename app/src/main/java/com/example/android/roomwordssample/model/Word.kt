package com.example.android.roomwordssample.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "word_table")
class Word(
        @field:ColumnInfo(name = "word")
        @field:PrimaryKey
        val word: String,
        @field:ColumnInfo(name = "details")
        var details: String)