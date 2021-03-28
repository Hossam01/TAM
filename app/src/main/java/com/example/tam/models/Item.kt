package com.example.tam.models

data class Item(
    val category: List<Category>,
    val trending: List<Trending>,
    val whats_new: List<WhatsNew>
)