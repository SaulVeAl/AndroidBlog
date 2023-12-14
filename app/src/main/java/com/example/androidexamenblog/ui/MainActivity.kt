package com.example.androidexamenblog.ui

import android.net.ConnectivityManager
import android.net.Network
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import com.agrawalsuneet.dotsloader.loaders.TrailingCircularDotsLoader
import com.example.androidexamenblog.R
import com.example.androidexamenblog.databinding.ActivityMainBinding
import com.example.androidexamenblog.utils.NetworkMonitor
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    var loader: TrailingCircularDotsLoader? = null
    var fabEntrada: ExtendedFloatingActionButton? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        loader = binding.loader
        fabEntrada = binding.fabNuevaEntrada

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.main_nav_host_fragment) as NavHostFragment
        val navController = navHostFragment.navController

        navController.addOnDestinationChangedListener { controller, destination, arguments ->

            when(destination.id){
                R.id.blogEntryListFragment -> {
                    binding.fabNuevaEntrada.visibility = View.VISIBLE
                }
                else -> {
                    binding.fabNuevaEntrada.visibility = View.GONE
                }
            }

        }

    }

    override fun onDestroy() {
        super.onDestroy()
    }
}