


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

import com.example.ucbpos.api.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

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



//
//        val json = JsonUtils.loadJSONFromAsset(this, "home.json")
//
//        if (json != null) {
//
//            val homeResponse = Gson().fromJson(json, HomeResponse::class.java)
//
//            val txtCompany = findViewById<TextView>(R.id.txtCompany)
//            val txtSubTitle = findViewById<TextView>(R.id.txtSubTitle)
//            val imgLogo = findViewById<ImageView>(R.id.imgLogo)
//
//
//            val viewPager = findViewById<ViewPager2>(R.id.viewPagerSlider)
//
//
//
//            txtCompany.text = homeResponse.appName
//            txtSubTitle.text = homeResponse.appNameSubTitle
//
//            val logoId = resources.getIdentifier(
//                homeResponse.logo,
//                "drawable",
//                packageName
//            )
//
//
//            viewPager.adapter = SliderAdapter(
//                this,
//                homeResponse.sliderMedia
//            )
//
//            imgLogo.setImageResource(logoId)
//
//        }
//
//

        RetrofitClient.api.getHome().enqueue(object : Callback<HomeResponse> {

            override fun onResponse(
                call: Call<HomeResponse>,
                response: Response<HomeResponse>
            ) {

                if (response.isSuccessful && response.body() != null) {

                    val homeResponse = response.body()!!

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

                    imgLogo.setImageResource(logoId)

                    viewPager.adapter = SliderAdapter(
                        this@MainActivity,
                        homeResponse.sliderMedia
                    )
                }
            }

            override fun onFailure(
                call: Call<HomeResponse>,
                t: Throwable
            ) {
                t.printStackTrace()
            }
        })

        // Load Menu JSON
//        val menuJson = JsonUtils.loadJSONFromAsset(this, "menu.json")
//
//        if (menuJson != null) {
//
//            val listType = object : TypeToken<List<MenuItem>>() {}.type
//
//            val menuList: List<MenuItem> =
//                Gson().fromJson(menuJson, listType)
//
//            // Show only items for Home Screen
////            val homeMenu = menuList.filter {
////                it.isShow && it.isShowInHome
////            }
//
//            // Get all visible home items
//            val visibleHomeMenu = menuList.filter {
//                it.isShow && it.isEnable && it.isShowInHome
//            }
//
//// Show only first 3
//            val homeMenu = visibleHomeMenu
//                .take(3)
//                .toMutableList()
//
//// If there are more than 3 items, add More button
//            if (visibleHomeMenu.size > 3) {
//
//                homeMenu.add(
//                    MenuItem(
//                        id = -1,
//                        title = "More",
//                        subTitle = "",
//                        icon = "more",
//                        isShowInHome = true,
//                        isShow = true,
//                        isEnable = true
//                    )
//                )
//            }
//
//
//
//
//            val recyclerView = findViewById<RecyclerView>(R.id.rvHomeMenu)
//
//            recyclerView.layoutManager = GridLayoutManager(this, 2)
//
//            recyclerView.adapter = HomeMenuAdapter(
//                this,
//                homeMenu
//            ) { menu ->
//
//                when(menu.title){
//
//                    "More" -> {
//
//                        startActivity(
//                            Intent(
//                                this,
//                                MoreServiceActivity::class.java
//                            )
//                        )
//                    }
//
//                    else -> {
//
//                    }
//                }
//            }
//        }


        RetrofitClient.api.getMenu().enqueue(object : Callback<List<MenuItem>> {

            override fun onResponse(
                call: Call<List<MenuItem>>,
                response: Response<List<MenuItem>>
            ) {

                if (response.isSuccessful && response.body() != null) {

                    val menuList = response.body()!!

                    // Get all visible home items
                    val visibleHomeMenu = menuList.filter {
                        it.isShow && it.isEnable && it.isShowInHome
                    }

                    // Show only first 3
                    val homeMenu = visibleHomeMenu
                        .take(3)
                        .toMutableList()

                    // Add More button if needed
                    if (visibleHomeMenu.size > 3) {

                        homeMenu.add(
                            MenuItem(
                                id = -1,
                                title = "More",
                                subTitle = "",
                                icon = "more",
                                isShowInHome = true,
                                isShow = true,
                                isEnable = true
                            )
                        )
                    }

                    val recyclerView = findViewById<RecyclerView>(R.id.rvHomeMenu)

                    recyclerView.layoutManager = GridLayoutManager(this@MainActivity, 2)

                    recyclerView.adapter = HomeMenuAdapter(
                        this@MainActivity,
                        homeMenu
                    ) { menu ->

                        if (menu.id == -1) {

                            startActivity(
                                Intent(
                                    this@MainActivity,
                                    MoreServiceActivity::class.java
                                )
                            )
                        }
                    }
                }
            }

            override fun onFailure(
                call: Call<List<MenuItem>>,
                t: Throwable
            ) {

                android.util.Log.e("MENU_API", t.message ?: "Unknown Error", t)
            }
        })


    }
}