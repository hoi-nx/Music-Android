package com.sun.music_66.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.sun.music_66.R
import com.sun.music_66.data.dto.GenreDto
import com.sun.music_66.data.local.MusicDevice
import kotlinx.android.synthetic.main.item_music.view.*
import kotlinx.android.synthetic.main.item_song.view.*
import java.text.SimpleDateFormat

/**
 * Created by nguyenxuanhoi on 2019-07-29.
 * @author nguyen.xuan.hoi@sun-asterisk.com
 */
class MusicDeviceAdapter(val onItemClicked: (music: MusicDevice) -> Unit):RecyclerView.Adapter<RecyclerView.ViewHolder>(){
    private var data = emptyList<MusicDevice>()
    fun submitList(data: List<MusicDevice>) {
        this.data = data as ArrayList<MusicDevice>
        notifyDataSetChanged()
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder =
            ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_song, parent, false))


    override fun getItemCount(): Int =data.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if(holder is ViewHolder){
            holder.bindView(data[position])
        }
    }
    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        init {
            itemView.setOnClickListener {
                onItemClicked.invoke(data[adapterPosition])

            }
        }
        fun bindView(item: MusicDevice) {
            itemView.apply {
                text_name_song.text=item.musicName
                text_artist.text=item.artist
            }
        }
    }

}