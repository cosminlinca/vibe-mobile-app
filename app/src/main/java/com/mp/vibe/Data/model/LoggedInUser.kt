package com.mp.vibe.Data.model

/**
 * Data class that captures user information for logged in users retrieved from LoginRepository
 */
data class LoggedInUser(
    val userId: String,
    val email: String,
    val firstName: String,
    val lastName: String,
    val displayName: String


)
