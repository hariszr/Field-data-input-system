package com.example.sisteminformasi.network

import androidx.room.Dao
import androidx.room.Query
@Dao
interface UsersDao {
    @Query("SELECT * FROM database_users WHERE username = :username AND kodwil = :password")
    fun getUserByUsernameAndPassword(username: String, password: String): UsersDao?
}