


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


    }
}