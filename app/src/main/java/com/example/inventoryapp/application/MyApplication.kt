package com.example.inventoryapp.application

import android.app.Application
import com.example.inventoryapp.model.room.ProductDatabase
import com.example.inventoryapp.repository.MainRepository

class MyApplication : Application() {
    private val database by lazy { ProductDatabase.getDatabase(this) }
    val repository by lazy { MainRepository(database.wordDao()) }
}