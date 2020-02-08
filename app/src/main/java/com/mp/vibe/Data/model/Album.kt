package com.mp.vibe.Data.model

import androidx.room.ColumnInfo
import java.util.*

class Album constructor(){

    @field:ColumnInfo(name = "album_type")
    lateinit var album_type: String

    lateinit var artists: MutableList<Artist>

    lateinit var images: MutableList<Image>

    //lateinit var release_date: Date
}