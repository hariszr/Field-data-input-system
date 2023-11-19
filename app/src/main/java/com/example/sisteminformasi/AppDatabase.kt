package com.example.sisteminformasi

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.sisteminformasi.model.Users
import com.example.sisteminformasi.network.UsersDao

@Database(entities = [Users::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UsersDao
}