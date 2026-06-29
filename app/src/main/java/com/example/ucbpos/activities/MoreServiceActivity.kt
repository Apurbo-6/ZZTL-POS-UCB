package com.example.ucbpos.activities

import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.ucbpos.R
import com.example.ucbpos.adapter.HomeMenuAdapter
import com.example.ucbpos.model.MenuItem
import com.example.ucbpos.utils.JsonUtils
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

import com.example.ucbpos.api.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response



class MoreServiceActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        @Suppress("DEPRECATION")
        window.statusBarColor = ContextCompat.getColor(this, R.color.ucb_red)

        @Suppress("DEPRECATION")
        window.navigationBarColor = ContextCompat.getColor(this, R.color.ucb_red)


        setContentView(R.layout.activity_more_service)
        findViewById<ImageView>(R.id.btnBack).setOnClickListener {
            finish()
        }

        loadMoreMenu()
    }

//    private fun loadMoreMenu() {
//
//        val menuJson = JsonUtils.loadJSONFromAsset(this, "menu.json") ?: return
//
//        val listType = object : TypeToken<List<MenuItem>>() {}.type
//
//        val menuList: List<MenuItem> =
//            Gson().fromJson(menuJson, listType)
//
////        val moreMenu = menuList.filter {
////
////            it.isShow && !it.isShowInHome
////
////        }
//
//        // All visible menu items
//        val visibleMenu = menuList.filter {
//            it.isShow && it.isEnable
//        }
//
//    // Home items
//        val homeItems = visibleMenu.filter {
//            it.isShowInHome
//        }
//
//    // Items after the first 3 home items
////        val remainingHomeItems = homeItems.drop(3)
//        val remainingHomeItems = homeItems
//            .drop(3)
//            .filter { it.id != -1 }
//
//    // Items that should only appear in More Services
//        val moreOnlyItems = visibleMenu.filter {
//            !it.isShowInHome
//        }
//
//    // Final list
//        val moreMenu = remainingHomeItems + moreOnlyItems
//
//        val recyclerView = findViewById<RecyclerView>(R.id.rvMoreMenu)
//
//        recyclerView.layoutManager = GridLayoutManager(this, 2)
//
//        recyclerView.adapter = HomeMenuAdapter(
//            this,
//            moreMenu
//        ) {
//
//            // Click later
//
//        }
//    }


    private fun loadMoreMenu() {

        RetrofitClient.api.getMenu().enqueue(object : Callback<List<MenuItem>> {

            override fun onResponse(
                call: Call<List<MenuItem>>,
                response: Response<List<MenuItem>>
            ) {

                if (response.isSuccessful && response.body() != null) {

                    val menuList = response.body()!!

                    // All visible menu items
                    val visibleMenu = menuList.filter {
                        it.isShow && it.isEnable
                    }

                    // Home items
                    val homeItems = visibleMenu.filter {
                        it.isShowInHome
                    }

                    // Remaining Home items after first 3
                    val remainingHomeItems = homeItems.drop(3)

                    // Items only for More Services
                    val moreOnlyItems = visibleMenu.filter {
                        !it.isShowInHome
                    }

                    // Final list
                    val moreMenu = remainingHomeItems + moreOnlyItems

                    val recyclerView = findViewById<RecyclerView>(R.id.rvMoreMenu)

                    recyclerView.layoutManager = GridLayoutManager(this@MoreServiceActivity, 2)

                    recyclerView.adapter = HomeMenuAdapter(
                        this@MoreServiceActivity,
                        moreMenu
                    ) { menu ->

                        // Future click actions

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