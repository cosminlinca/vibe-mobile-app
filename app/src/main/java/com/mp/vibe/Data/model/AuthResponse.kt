package com.mp.vibe.Data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey

@Entity(tableName = "auth_table")
class AuthResponse constructor()
{
    @PrimaryKey
    @field:ColumnInfo(name = "access_token")
    lateinit var access_token: String

    @field:ColumnInfo(name = "token_type")
    lateinit var token_type: String

    @field:ColumnInfo(name = "expires_in")
    var expires_in: Int = 0

    @field:ColumnInfo(name = "scope")
    lateinit var scope: String

    @Ignore
    constructor(access_Token: String, token_type: String, expires_in: Int, scope: String) : this() {
        this.access_token = access_Token
        this.token_type = token_type
        this.expires_in = expires_in
        this.scope = scope
    }
}
