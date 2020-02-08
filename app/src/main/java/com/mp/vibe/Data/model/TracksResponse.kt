package com.mp.vibe.Data.model

class TracksResponse {
    var tracks: List<Track>

    init {
        tracks = ArrayList()
    }

    constructor(tracks: List<Track>) {
        this.tracks = tracks
    }
}
