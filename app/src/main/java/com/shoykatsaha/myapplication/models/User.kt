package com.shoykatsaha.myapplication.models
data class User(
    var uid: String,
    val email: String,
    val name: String,
    var isAdmin: Boolean = false
)
