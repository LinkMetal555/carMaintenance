package com.example.carmaintenance.views

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.carmaintenance.R
import com.example.carmaintenance.databinding.MenuLayoutBinding

class MenuActivity:AppCompatActivity() {
    private lateinit var binding: MenuLayoutBinding
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = MenuLayoutBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.container) as NavHostFragment

        navController = navHostFragment.navController

        binding.bottomNav.setupWithNavController(navController)


    }
}