package com.example.yolharakatiqoidalari

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.NavigationUI
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.activity_body.*

class BodyActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_body)


//        val navController: NavController =
//            Navigation.findNavController(this, R.id.nav_host_fragment)
//        val bottomNavigationView =
//            findViewById<BottomNavigationView>(R.id.bottomNavigation)
//        NavigationUI.setupWithNavController(bottomNavigationView, navController)

//        val navHostFragment =
//            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
//
        bottomNavigation.setOnNavigationItemSelectedListener{
            when(it.itemId){
                R.id.first -> {
                    val mainFragment = MainFragment()
                    supportFragmentManager.popBackStack()
                    supportFragmentManager.beginTransaction().replace(R.id.mainLayout, mainFragment).commit()
                }
                R.id.second -> {
                    val likeFragment = LikeFragment()
                    supportFragmentManager.beginTransaction().replace(R.id.mainLayout, likeFragment).commit()
                }
                R.id.third -> {
                    val infoFragment = InfoFragment()
                    supportFragmentManager.beginTransaction().replace(R.id.mainLayout, infoFragment).commit()
                }
            }
            true
        }


        val mainFragment = MainFragment()
        supportFragmentManager.beginTransaction().add(R.id.mainLayout, mainFragment).commit()
    }
}