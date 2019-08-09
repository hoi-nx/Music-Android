package com.sun.music_66.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.sun.music_66.R
import com.sun.music_66.data.dto.CollectionDto
import com.sun.music_66.data.dto.TrackDto
import com.sun.music_66.util.LoadImage
import kotlinx.android.synthetic.main.item_song.view.*

/**
 * Created by nguyenxuanhoi on 2019-07-22.
 * @author nguyen.xuan.hoi@sun-asterisk.com
 */
class ListSongAdapter(private val onItemClicked: (track: TrackDto) -> Unit, private val onMore: (track: TrackDto) -> Unit) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private var data = mutableListOf<TrackDto>()

    fun submitList(data: List<TrackDto>) {
        this.data = data as ArrayList<TrackDto>
        notifyDataSetChanged()
    }

    fun clear() {
        data.clear()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder =
            ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_song, parent, false))

    override fun getItemCount(): Int = data.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is ViewHolder) {
            holder.bindView(data[position])
        }
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        init {
            itemView.apply {
                text_name_song.setOnClickListener {
                    text_name_song.isSelected = true
                    onItemClicked.invoke(data[adapterPosition])
                }
                more.setOnClickListener {
                    onMore.invoke(data[adapterPosition])

                }
            }

        }

        fun bindView(track: TrackDto) {
            itemView.apply {
                text_name_song.text = track.title
                LoadImage.loadImage(image_track, track.artWorkUrl)
            }
        }

    }
}