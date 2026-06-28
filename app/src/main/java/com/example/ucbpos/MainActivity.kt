


package com.example.ucbpos

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

import com.example.ucbpos.utils.JsonUtils

import com.google.gson.Gson
import com.example.ucbpos.model.HomeResponse
//import android.widget.Toast
import android.widget.TextView
import android.widget.ImageView

import androidx.core.content.ContextCompat
import androidx.core.view.WindowCompat

import androidx.viewpager2.widget.ViewPager2
import com.example.ucbpos.adapter.SliderAdapter

import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.ucbpos.adapter.HomeMenuAdapter
import com.example.ucbpos.model.MenuItem
import com.google.gson.reflect.TypeToken

import android.content.Intent
import com.example.ucbpos.activities.MoreServiceActivity


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Change Status Bar Color
        @Suppress("DEPRECATION")
        window.statusBarColor = ContextCompat.getColor(this, R.color.ucb_red)


        @Suppress("DEPRECATION")
        window.navigationBarColor = ContextCompat.getColor(this, R.color.ucb_red)

        // White status bar icons
        WindowCompat.getInsetsController(window, window.decorView)
            .isAppearanceLightStatusBars = false

        setContentView(R.layout.activity_main)




        val json = JsonUtils.loadJSONFromAsset(this, "home.json")

        if (json != null) {

            val homeResponse = Gson().fromJson(json, HomeResponse::class.java)

            val txtCompany = findViewById<TextView>(R.id.txtCompany)
            val txtSubTitle = findViewById<TextView>(R.id.txtSubTitle)
            val imgLogo = findViewById<ImageView>(R.id.imgLogo)


            val viewPager = findViewById<ViewPager2>(R.id.viewPagerSlider)



            txtCompany.text = homeResponse.appName
            txtSubTitle.text = homeResponse.appNameSubTitle

            val logoId = resources.getIdentifier(
                homeResponse.logo,
                "drawable",
                packageName
            )


            viewPager.adapter = SliderAdapter(
                this,
                homeResponse.sliderMedia
            )

            imgLogo.setImageResource(logoId)

        }
        // Load Menu JSON
        val menuJson = JsonUtils.loadJSONFromAsset(this, "menu.json")

        if (menuJson != null) {

            val listType = object : TypeToken<List<MenuItem>>() {}.type

            val menuList: List<MenuItem> =
                Gson().fromJson(menuJson, listType)

            // Show only items for Home Screen
            val homeMenu = menuList.filter {
                it.isShow && it.isShowInHome
            }

            val recyclerView = findViewById<RecyclerView>(R.id.rvHomeMenu)

            recyclerView.layoutManager = GridLayoutManager(this, 2)

            recyclerView.adapter = HomeMenuAdapter(
                this,
                homeMenu
            ) { menu ->

                when(menu.title){

                    "More" -> {

                        startActivity(
                            Intent(
                                this,
                                MoreServiceActivity::class.java
                            )
                        )
                    }

                    else -> {

                    }
                }
            }
        }


    }
}