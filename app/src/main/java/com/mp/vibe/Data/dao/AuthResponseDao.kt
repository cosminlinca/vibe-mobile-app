package com.mp.vibe.Data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

import com.mp.vibe.Data.model.AuthResponse

@Dao
interface AuthResponseDao {

    @get:Query("SELECT * FROM auth_table")
    val all: List<AuthResponse>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(auth: AuthResponse)

    @Query("DELETE FROM auth_table")
    fun deleteAll()
}
