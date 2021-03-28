package com.example.tam.adapter

import android.app.Activity
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityOptionsCompat
import androidx.core.util.Pair
import androidx.core.view.ViewCompat
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.tam.R
import com.example.tam.models.Category
import com.example.tam.models.WhatsNew
import kotlinx.android.synthetic.main.category_item.view.*
import kotlinx.android.synthetic.main.new_item.view.*


class NewAdapter: RecyclerView.Adapter<NewAdapter.NewViewHolder>() {

    inner class NewViewHolder(itemView: View):RecyclerView.ViewHolder(itemView)

    private val diffCallback = object : DiffUtil.ItemCallback<WhatsNew>(){
        override fun areItemsTheSame(oldItem: WhatsNew, newItem: WhatsNew): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: WhatsNew, newItem: WhatsNew): Boolean {
            return oldItem.hashCode() == newItem.hashCode()
        }
    }

    private val differ = AsyncListDiffer(this, diffCallback)

    fun submitList(list: List<WhatsNew>) = differ.submitList(list)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewViewHolder {
        return NewViewHolder(
                LayoutInflater.from(
                        parent.context
                ).inflate(
                        R.layout.new_item,
                        parent,
                        false
                )
        )
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onBindViewHolder(holder: NewViewHolder, position: Int) {

        val item = differ.currentList[position]

        holder.itemView.apply {
            Glide.with(this).load(item.image.toString()).into(product)
            name.text=item.name
            shop.text=item.shop_name
            rate.text=item.rate
            price.text=item.currency+" "+item.price

        }
    }
}