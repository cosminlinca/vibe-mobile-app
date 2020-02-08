package com.mp.vibe.UI.playlist

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.mp.vibe.Data.model.Track
import com.mp.vibe.Data.repository.WebRepository
import com.mp.vibe.R
import io.reactivex.SingleObserver
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_song_item_edit.*
import kotlinx.android.synthetic.main.fragment_playlist.artistNameField
import kotlinx.android.synthetic.main.fragment_playlist.songNameField
import retrofit2.Response

class SongItemEditActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_song_item_edit)

        val songName: String = intent.getStringExtra("songName")
        val artistName: String = intent.getStringExtra("artistName")

        songNameField.setText(songName)
        artistNameField.setText(artistName)

        var webRepo =
            WebRepository()

        addSongBtn.setOnClickListener(View.OnClickListener {

        val reqResponse = object : SingleObserver<Response<Track>> {
            override fun onSuccess(t: Response<Track>) {
                //Finish with succes, so go back
                finish()
            }

            override fun onSubscribe(d: Disposable) {
            }

            override fun onError(e: Throwable) {
                finish()
            }

        }

        webRepo.addSong(songName, artistName)
            ?.subscribeOn(Schedulers.io())
            ?.observeOn(AndroidSchedulers.mainThread())
            ?.subscribe(reqResponse)

        })

    }
}
