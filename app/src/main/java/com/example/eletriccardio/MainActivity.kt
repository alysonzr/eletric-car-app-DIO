package com.example.eletriccardio

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI.setupWithNavController
import com.example.eletriccardio.databinding.ActivityMainBinding
import com.example.eletriccardio.ui.CalcularAutonomiaActivity

class MainActivity : AppCompatActivity() {

    private lateinit var bindind : ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        bindind = ActivityMainBinding.inflate(layoutInflater)
        setContentView(bindind.root)
        setupListeners();

        val navController = findNavController(R.id.nav_host_fragment);
        setupWithNavController(bindind.bottomNavigation, navController)

    }

    private fun setupListeners() {
        bindind.fabCalcular.setOnClickListener {
            startActivity(Intent(this, CalcularAutonomiaActivity::class.java))
        }
    }

}