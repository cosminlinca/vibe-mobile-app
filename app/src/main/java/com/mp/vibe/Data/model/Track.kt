package com.mp.vibe.Data.model

import androidx.room.*

@Entity(tableName = "track_table")
class Track {

    @PrimaryKey
    @field:ColumnInfo(name = "id")
    lateinit var id: String

    @field:ColumnInfo(name = "href")
    lateinit var href: String

    @field:ColumnInfo(name = "name")
    lateinit var name: String

    @field:ColumnInfo(name = "uri")
    lateinit var uri: String

    @field:ColumnInfo(name = "type")
    lateinit var type: String

    @Embedded
    lateinit var album: Album

    @field:ColumnInfo(name = "popularity")
    var popularity: Int = 0

    @field:ColumnInfo(name = "duration_ms")
    var duration_ms: Int = 0

    constructor(id: String, href: String, name: String, uri: String, type: String, album: Album, popularity: Int, duration_ms: Int) {
        this.id = id;
        this.href = href;
        this.name = name;
        this.uri = uri;
        this.type = type;
        this.album = album;
        this.popularity = popularity;
        this.duration_ms = duration_ms;
    }
}