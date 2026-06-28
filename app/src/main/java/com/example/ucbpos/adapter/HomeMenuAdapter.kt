package com.example.ucbpos.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.ucbpos.R
import com.example.ucbpos.model.MenuItem

class HomeMenuAdapter(
    private val context: Context,
    private val menuList: List<MenuItem>,
    private val onClick: (MenuItem) -> Unit
) : RecyclerView.Adapter<HomeMenuAdapter.MenuViewHolder>() {

    inner class MenuViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val imgMenu: ImageView = itemView.findViewById(R.id.imgMenuIcon)
        val txtMenu: TextView = itemView.findViewById(R.id.txtMenuTitle)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MenuViewHolder {

        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_home_menu, parent, false)

        return MenuViewHolder(view)
    }

    override fun onBindViewHolder(holder: MenuViewHolder, position: Int) {

        val menu = menuList[position]

        holder.txtMenu.text = menu.title

        // Load local drawable icon
        val imageId = context.resources.getIdentifier(
            menu.icon,
            "drawable",
            context.packageName
        )

        if (imageId != 0) {
            holder.imgMenu.setImageResource(imageId)
        } else {
            holder.imgMenu.setImageResource(R.drawable.ic_launcher_foreground)
        }

        // Click Event
        holder.itemView.setOnClickListener {
            onClick(menu)
        }
    }

    override fun getItemCount(): Int {
        return menuList.size
    }
}