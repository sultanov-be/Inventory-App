package com.example.inventoryapp.adapters

import androidx.recyclerview.widget.DiffUtil
import com.example.inventoryapp.model.Product

class MyDiffUtil(private val oldList:List<Product>, private val newList:List<Product>):
    DiffUtil.Callback() {

    override fun getOldListSize(): Int = oldList.size

    override fun getNewListSize(): Int = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition].id == newList[newItemPosition].id
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val (_ ,name, price) = oldList[oldItemPosition]
        val (_, new_name, new_price,) = newList[newItemPosition]

        return name == new_name && price == new_price
    }

}