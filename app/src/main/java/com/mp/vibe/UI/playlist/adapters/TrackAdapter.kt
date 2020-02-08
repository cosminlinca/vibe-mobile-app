package com.mp.vibe.UI.playlist.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.os.StrictMode
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.LinearLayout
import android.widget.TextView
import com.mp.vibe.Data.model.Track
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.track.view.*


class TrackAdapter(c: Context, theSongs: ArrayList<Track>?) : BaseAdapter() {

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    private var context: Context? = c
    private var songs: ArrayList<Track>? = theSongs
    private var songInf: LayoutInflater? = null

    init {
        songInf = LayoutInflater.from(c)
    }

    override fun getCount(): Int {
        return songs!!.size
    }

    override fun getItem(arg0: Int): Any? {
        return songs?.get(arg0)
    }

    @SuppressLint("ViewHolder")
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View? {
        val policy = StrictMode.ThreadPolicy.Builder().permitAll().build()
        StrictMode.setThreadPolicy(policy)

        //map to song layout
        val songLay = songInf!!.inflate(com.mp.vibe.R.layout.track, parent, false) as LinearLayout
        //get song using position
        val currSong = songs!![position]
        val songTitle: TextView? = songLay.song_title

        //get title and artist strings
        songTitle?.text = currSong.name

        songLay.song_artist.text = currSong.album.artists?.get(0)?.name

        Picasso.with(context!!)
            .load(currSong.album.images.get(0).url)
            .resize(50,50)
            .centerCrop()
            .into(songLay.image)

        songLay.tag = position

        return songLay
    }
}