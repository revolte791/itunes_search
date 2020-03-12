package com.hwx.itunessearchbox.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.hwx.itunessearchbox.R
import com.hwx.itunessearchbox.databinding.RvItemTrackBinding
import com.hwx.itunessearchbox.model.Track
import kotlinx.android.synthetic.main.rv_item_track.view.*

/**
 * Адаптер для списка трэков
 */
class TracksAdapter (
    var context: Context
) : ListAdapter<Track, TracksAdapter.TrackViewHolder>(TrackDiff) {

    private object TrackDiff : DiffUtil.ItemCallback<Track>() {

        override fun areItemsTheSame(oldItem: Track, newItem: Track): Boolean {
            return oldItem.trackName == newItem.trackName && oldItem.trackNumber == newItem.trackNumber
        }

        override fun areContentsTheSame(oldItem: Track, newItem: Track) = oldItem == newItem
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TrackViewHolder {
        val rvItemCheckLineBinding = DataBindingUtil.inflate<RvItemTrackBinding>(
            LayoutInflater.from(parent.context),
            R.layout.rv_item_track,
            parent,
            false)

        return TrackViewHolder(
            rvItemCheckLineBinding
        )
    }

    override fun onBindViewHolder(holder: TrackViewHolder, position: Int) {
        val item = getItem(position)

        Glide.with(context)
            .load(item.artworkUrl30)
            .into(holder.itemView.ivTrack)

        holder.bindData(getItem(position))
    }

    class TrackViewHolder(
        val binding: RvItemTrackBinding
    ) : RecyclerView.ViewHolder(binding.rlListItemRoot) {
        private val tvTrackTitle: TextView = itemView.findViewById(R.id.tvTrackTitle)

        fun bindData(track: Track?) {
            track?.apply {
                tvTrackTitle.text = track.trackName
            }
        }
    }
}