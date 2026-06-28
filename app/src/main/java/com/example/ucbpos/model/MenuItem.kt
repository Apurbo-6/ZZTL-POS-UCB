package com.example.ucbpos.model

data class MenuItem(

    val id: Int,

    val title: String,

    val subTitle: String,

    val icon: String,

    val isShowInHome: Boolean,

    val isShow: Boolean,

    val isEnable: Boolean

)