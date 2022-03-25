package com.example.inventoryapp.adapters

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.inventoryapp.databinding.ProductItemBinding
import com.example.inventoryapp.model.Product
import com.example.inventoryapp.utils.RecyclerItemClickListener
import com.example.inventoryapp.utils.loadImage

class RecyclerAdapter(private val clickListener: RecyclerItemClickListener?) :
    RecyclerView.Adapter<RecyclerAdapter.ViewHolder>() {

    private var list = mutableListOf<Product>()

    fun setList(newList: List<Product>) {
        Log.i("TAG", "setList")
        val diffCallback = MyDiffUtil(list, newList)
        val diffResult = DiffUtil.calculateDiff(diffCallback)
        list.clear()
        list.addAll(newList)
        diffResult.dispatchUpdatesTo(this)
    }

    inner class ViewHolder(private val binding: ProductItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("SetTextI18n")
        fun setData(item: Product) {
            with(binding) {
                productName.text = item.name
                productPrice.text = "$${item.price}"
                quantity.text = "${item.quantity}шт"
                supplier.text = item.sup
                imageView.loadImage(item.image)
                dots.setOnClickListener {
                    clickListener?.dotClicked(item)
                }
            }
            itemView.setOnClickListener { clickListener?.onClick(item) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ProductItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, pos: Int) {
        holder.setData(list[pos])
    }

    override fun getItemCount(): Int {
        return list.size
    }
}