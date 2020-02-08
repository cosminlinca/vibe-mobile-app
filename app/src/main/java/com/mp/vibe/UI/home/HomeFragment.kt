package com.mp.vibe.UI.home

import android.app.ActivityOptions
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.mp.vibe.Data.db.UserDatabase
import com.mp.vibe.MainActivity
import com.mp.vibe.R
import com.mp.vibe.Service.Spotify
import com.mp.vibe.UI.login.LoginActivity
import kotlin.math.sign

class HomeFragment : Fragment() {

    private lateinit var homeViewModel: HomeViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        homeViewModel =
            ViewModelProviders.of(this).get(HomeViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_home, container, false)

        val signOut: Button = root.findViewById(R.id.logoutBtn)
        // Get local DB (Created with Room)
        var localDB = UserDatabase.getInstance(context!!)
        signOut.setOnClickListener(View.OnClickListener {
            // Delete all tokens
            localDB.authResponseDao().deleteAll()

            val intent = Intent(context!!, LoginActivity::class.java)
            startActivity(intent,  ActivityOptions.makeSceneTransitionAnimation(activity!!).toBundle())
        })


        return root
    }

}