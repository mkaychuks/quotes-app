package com.example.quotes.ui.favourites.data

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [Favourites::class],
    version = 1
)
abstract class FavouritesDatabase : RoomDatabase() {
    abstract val dao: FavouritesDao
}