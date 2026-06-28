package com.example.ucbpos.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.example.ucbpos.R
import com.example.ucbpos.model.SliderMedia

class SliderAdapter(
    private val context: Context,
    private val sliderList: List<SliderMedia>
) : RecyclerView.Adapter<SliderAdapter.SliderViewHolder>() {

    class SliderViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val image: ImageView = view.findViewById(R.id.imgSlider)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SliderViewHolder {

        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_slider, parent, false)

        return SliderViewHolder(view)
    }

    override fun onBindViewHolder(holder: SliderViewHolder, position: Int) {

        val slider = sliderList[position]

        val imageId = context.resources.getIdentifier(
            slider.mediaLink,
            "drawable",
            context.packageName
        )

        holder.image.setImageResource(imageId)
    }

    override fun getItemCount(): Int = sliderList.size
}