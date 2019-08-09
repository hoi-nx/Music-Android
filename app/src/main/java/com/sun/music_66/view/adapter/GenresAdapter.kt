package com.sun.music_66.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.sun.music_66.R
import com.sun.music_66.data.dto.GenreDto
import com.sun.music_66.util.LoadImage
import kotlinx.android.synthetic.main.item_genres.view.*

/**
 * Created by nguyenxuanhoi on 2019-07-21.
 * @author nguyen.xuan.hoi@sun-asterisk.com
 */
class GenresAdapter(val onItemClicked: (genres: GenreDto) -> Unit) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    
    private var data = emptyList<GenreDto>()
    fun submitList(data: List<GenreDto>) {
        this.data = data as ArrayList<GenreDto>
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder =
            ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_genres, parent, false))

    override fun getItemCount(): Int = data.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is ViewHolder) {
            holder.bindView(data[position])
        }
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bindView(item: GenreDto) {
            itemView.apply {
                text_title.text = item.nameGenre
                LoadImage.loadImage(image_genres,item.image)
            }
            itemView.setOnClickListener {
                onItemClicked.invoke(data[adapterPosition])

            }
        }


    }

}