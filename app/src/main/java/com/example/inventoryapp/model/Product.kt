package com.example.inventoryapp.model

import android.graphics.Bitmap
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "product_table")
data class Product(
    @PrimaryKey(autoGenerate = true) val id: Int,
    val name: String,
    val image: Bitmap,
    val price: Int,
    val quantity: Int,
    var isArchive:Boolean,
    @ColumnInfo(name = "supplier")val sup: String
) : Serializable

