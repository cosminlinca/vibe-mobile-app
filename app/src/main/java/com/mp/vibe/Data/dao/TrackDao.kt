package com.mp.vibe.Data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.mp.vibe.Data.model.AuthResponse
import com.mp.vibe.Data.model.Track

@Dao
interface TrackDao {

    @get:Query("SELECT * FROM track_table")
    val getAll: List<Track>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(track: Track)

    @Query("DELETE FROM track_table")
    fun deleteAll()
}