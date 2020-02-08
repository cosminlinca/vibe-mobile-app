package com.mp.vibe.Data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.mp.vibe.Data.dao.AuthResponseDao
import com.mp.vibe.Data.dao.TrackDao
import com.mp.vibe.Data.model.AuthResponse
import com.mp.vibe.Data.model.Track

@Database(entities = [AuthResponse::class,
                      Track::class],
          version = 1, exportSchema = false)
@TypeConverters(DataConvert::class)
abstract class UserDatabase : RoomDatabase(){

    abstract fun authResponseDao(): AuthResponseDao
    abstract fun tracksDao(): TrackDao

    companion object {

        private var instance: UserDatabase? = null

        fun getInstance(context: Context): UserDatabase {
            if (instance == null) {
                instance = Room.databaseBuilder(
                    context.applicationContext,
                    UserDatabase::class.java, "vibe.db")
                    .allowMainThreadQueries()
                    .fallbackToDestructiveMigration()
                    .build()
            }

            return instance!!
        }
    }
}
