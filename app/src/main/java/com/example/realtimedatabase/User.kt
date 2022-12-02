package com.example.realtimedatabase

data class User(var id :Int =0,var name : String ="") {
    override fun toString(): String {
        return "User(id=$id, name='$name')"
    }
}