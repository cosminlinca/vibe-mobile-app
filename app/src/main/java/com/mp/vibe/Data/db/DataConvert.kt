package com.mp.vibe.Data.db

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.mp.vibe.Data.model.Artist
import com.mp.vibe.Data.model.Image

class DataConvert {
    @TypeConverter
    fun fromArtistsList(artists: List<Artist?>?): String? {
        if (artists == null) {
            return null
        }
        val gson = Gson()
        val type = object :
            TypeToken<List<Artist?>?>() {}.type
        return gson.toJson(artists, type)
    }

    @TypeConverter
    fun toArtistsList(artists: String?): List<Artist>? {
        if (artists == null) {
            return null
        }
        val gson = Gson()
        val type = object :
            TypeToken<List<Artist?>?>() {}.type
        return gson.fromJson<List<Artist>>(artists, type)
    }

    @TypeConverter
    fun fromImagesList(images: List<Image?>?): String? {
        if (images == null) {
            return null
        }
        val gson = Gson()
        val type = object :
            TypeToken<List<Image?>?>() {}.type
        return gson.toJson(images, type)
    }

    @TypeConverter
    fun toImagesList(images: String?): List<Image>? {
        if (images == null) {
            return null
        }
        val gson = Gson()
        val type = object :
            TypeToken<List<Image?>?>() {}.type
        return gson.fromJson<List<Image>>(images, type)
    }
}