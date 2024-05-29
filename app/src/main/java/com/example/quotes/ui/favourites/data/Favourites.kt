package com.example.quotes.ui.favourites.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Favourites(
    val title: String,
    val adviceNumber: Int,

    @PrimaryKey(autoGenerate = true)
    val id: Int = 0
)
