package com.mp.vibe.UI.playlist

import android.annotation.SuppressLint
import android.app.ActivityOptions
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.google.android.material.snackbar.Snackbar
import com.mp.vibe.Data.db.UserDatabase
import com.mp.vibe.Data.repository.SpotifyRepository
import com.mp.vibe.Data.model.Track
import com.mp.vibe.Data.model.TracksResponse
import com.mp.vibe.R
import com.mp.vibe.Service.Spotify
import com.mp.vibe.UI.playlist.adapters.TrackAdapter
import com.mp.vibe.Utils.NetworkUtils
import io.reactivex.SingleObserver
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import retrofit2.Response

class PlaylistFragment : Fragment(){

    private lateinit var playlistViewModel: PlaylistViewModel
    private var songList: ArrayList<Track>? = null
    private var songListView: ListView? = null

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        playlistViewModel =
                ViewModelProviders.of(this).get(PlaylistViewModel::class.java)
        val root = inflater.inflate(com.mp.vibe.R.layout.fragment_playlist, container, false)

        // Set buttons properties
        setButtonsProperties(inflater, container, root)

        val songTextView = root.findViewById<TextView>(R.id.songNameField)
        val artistTextView = root.findViewById<TextView>(R.id.artistNameField)

        // Create list
        songList = ArrayList()
        songListView = root.findViewById(com.mp.vibe.R.id.song_list)
        songListView!!.isClickable = true;

        // Initialize spotify repo
        var spotifyRepo =
            SpotifyRepository(context!!)
        var c: Context = context as Context


        // Spotify API Tracks IDs
        val ids = listOf(
            "10igKaIKsSB6ZnWxPxPvKO",
            "1AhDOtG9vPSOmsWgNW0BEY",
            "4Of7rzpRpV1mWRbhp5rAqG",
            "2b8fOow8UzyDFAE27YhOZM",
            "4VginDwYTP2eaHJzO0QMjG",
            "7vJS1DPc3FzBtqBs8n3mW5",
            "1TfqLAPs4K3s2rJMoCokcS",
            "2F83FxNVkK6PPMHuYnwyVc",
            "3M9Apu4OZfylLTFKvgEtKa",
            "4QjVfuu7om31HBan0jlX4p",
            "30cW9fD87IgbYFl8o0lUze",
            "05OCY605lOXP7koHNBMPc2",
            "421leiR6jKlH5KDdwLYrOs")

        var idsString = "1tDk0Jot1RkxsPTPXaNM7k"
        for (id: String in ids) {
            idsString += ",$id"
        }

        // Get local DB (Created with Room)
        var localDB = UserDatabase.getInstance(context!!)

        // Response
        val reqResponse = object : SingleObserver<Response<TracksResponse>> {
            @RequiresApi(Build.VERSION_CODES.O)
            @SuppressLint("CheckResult")
            override fun onSuccess(resp: Response<TracksResponse>) {
                if (resp.isSuccessful) {
                    Log.i("Resp Tracks", resp.message())

                   for(track: Track in resp.body()!!.tracks) {
                       songList!!.add(track)
                       // Insert track in local DB
                       localDB.tracksDao().insert(track)
                   }

                    val songAdt = TrackAdapter(c, songList)
                    songListView!!.adapter = songAdt

                    // Add on click event
                    songListView?.onItemClickListener =
                        AdapterView.OnItemClickListener { parent, view, position, id ->
                            Spotify.connect(context!!) {
                                songTextView.setText(songList!![position].name)
                                artistTextView.setText(songList!![position].album.artists[0].name)

                                Spotify.play(songList!![position].uri)
                            }
                        }

                    songListView?.onItemLongClickListener =
                        AdapterView.OnItemLongClickListener{ parent, view, position, id ->
//                            Toast.makeText(context , songList!![position]., Toast.LENGTH_LONG).show()
                            val intent = Intent(context!!, SongItemEditActivity::class.java)
                            intent.putExtra("songName", songList!![position].name)
                            intent.putExtra("artistName", songList!![position].album.artists[0].name);
                            startActivity(intent,  ActivityOptions.makeSceneTransitionAnimation(activity).toBundle())
                           return@OnItemLongClickListener true
                        }

                } else {
                    Log.e("Track Error", resp.toString())
                    // Get from local db
                    for(track: Track in localDB.tracksDao().getAll) {
                        songList!!.add(track)
                    }

                    val songAdt = TrackAdapter(c, songList)
                    songListView!!.adapter = songAdt

                    // Add on click event
                    songListView?.onItemClickListener =
                        AdapterView.OnItemClickListener { parent, view, position, id ->
                            Spotify.connect(context!!) {
                                if(NetworkUtils.isNetworkAvailable(context!!)) {
                                    songTextView.setText(songList!![position].name)
                                    artistTextView.setText(songList!![position].album.artists[0].name)

                                    Spotify.play(songList!![position].uri)
                                }
                                else {
//                                val myToast = Toast.makeText(context,"Please connect to the internet!", Toast.LENGTH_LONG)
//                                myToast.show()
                                    Snackbar.make(view, "Please connect to the internet",
                                        Snackbar.LENGTH_LONG)
                                        .setAction("Internet", null).show()
                                }
                            }
                        }

                    songListView?.onItemLongClickListener =
                        AdapterView.OnItemLongClickListener{ parent, view, position, id ->
                            //                            Toast.makeText(context , songList!![position]., Toast.LENGTH_LONG).show()
                            val intent = Intent(context!!, SongItemEditActivity::class.java)
                            intent.putExtra("songName", songList!![position].name)
                            intent.putExtra("artistName", songList!![position].album.artists[0].name);
                            startActivity(intent,  ActivityOptions.makeSceneTransitionAnimation(activity).toBundle())
                            return@OnItemLongClickListener true;
                        }
                }
            }

            override fun onSubscribe(d: Disposable) {
            }

            override fun onError(e: Throwable) {
                Log.e("Spotify request error", e.toString())
                // Get from local db
                for(track: Track in localDB.tracksDao().getAll) {
                    songList!!.add(track)
                }

                val songAdt = TrackAdapter(c, songList)
                songListView!!.adapter = songAdt

                // Add on click event
                songListView?.onItemClickListener =
                    AdapterView.OnItemClickListener { parent, view, position, id ->
                        Spotify.connect(context!!) {
                            if(NetworkUtils.isNetworkAvailable(context!!)) {
                                songTextView.setText(songList!![position].name)
                                artistTextView.setText(songList!![position].album.artists[0].name)

                                Spotify.play(songList!![position].uri)
                            }
                            else {
//                                val myToast = Toast.makeText(context,"Please connect to the internet!", Toast.LENGTH_LONG)
//                                myToast.show()
                                Snackbar.make(view, "Please connect to the internet",
                                    Snackbar.LENGTH_LONG)
                                    .setAction("Internet", null).show()
                            }
                        }
                    }

                songListView?.onItemLongClickListener =
                    AdapterView.OnItemLongClickListener{ parent, view, position, id ->
                        //                            Toast.makeText(context , songList!![position]., Toast.LENGTH_LONG).show()
                        val intent = Intent(context!!, SongItemEditActivity::class.java)
                        intent.putExtra("songName", songList!![position].name)
                        intent.putExtra("artistName", songList!![position].album.artists[0].name);
                        startActivity(intent,  ActivityOptions.makeSceneTransitionAnimation(activity).toBundle())
                        return@OnItemLongClickListener true;
                    }
            }
        }

        spotifyRepo.getTracks(idsString)
            ?.subscribeOn(Schedulers.io())
            ?.observeOn(AndroidSchedulers.mainThread())
            ?.subscribe(reqResponse)


        return root
    }

    private fun setButtonsProperties(inflater: LayoutInflater, container: ViewGroup?, root: View?) {
        val resumeBtn: Button = root!!.findViewById(R.id.resumeBtn)
        resumeBtn.setOnClickListener(View.OnClickListener {
            Spotify.resume()
        })

        val stopBtn: Button = root!!.findViewById(R.id.stopBtn)
        stopBtn.setOnClickListener(View.OnClickListener {
            Spotify.pause()
        })
    }
}