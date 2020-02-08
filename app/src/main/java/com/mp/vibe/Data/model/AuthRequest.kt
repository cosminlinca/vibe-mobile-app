package com.mp.vibe.Data.model

class AuthRequest {
    
    var client_id: String
    var response_type: String
    var redirect_uri: String

    constructor(client_id: String, response_type: String, redirect_uri: String) {
        this.client_id = client_id
        this.response_type = response_type
        this.redirect_uri = redirect_uri
    }
}