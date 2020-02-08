package com.mp.vibe.Data.model

import androidx.room.ColumnInfo

class Artist constructor() {

    @field:ColumnInfo(name = "artist_id")
    public var id: String = ""

    @field:ColumnInfo(name = "artist_name")
    public var name: String = ""

    @field:ColumnInfo(name = "artist_href")
    public var href: String = ""

    constructor(id: String, name: String, href: String) : this() {
        this.id = id
        this.name = name
        this.href = href
    }
}