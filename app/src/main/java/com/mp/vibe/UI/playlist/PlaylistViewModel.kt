package com.mp.vibe.UI.playlist

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class PlaylistViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is playlist Fragment"
    }
    val text: LiveData<String> = _text
}