package com.example.tam.models

data class Trending(
    val cat_id: String,
    val currency: String,
    val id: String,
    val image: String,
    val isWishList: Int,
    val name: String,
    val price: String,
    val rate: String,
    val shop_name: String
)