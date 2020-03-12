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
import com.hwx.itunessearchbox.databinding.RvItemAlbumBinding
import com.hwx.itunessearchbox.model.Album
import com.hwx.itunessearchbox.viewmodel.HomeViewModel
import kotlinx.android.synthetic.main.rv_item_album.view.*

/**
 * Адаптер списка альбомов
 */
class AlbumsAdapter (
    var context: Context,
    private val homeViewModel: HomeViewModel
) : ListAdapter<Album, AlbumsAdapter.AlbumViewHolder>(AlbumDiff) {

    private object AlbumDiff : DiffUtil.ItemCallback<Album>() {

        override fun areItemsTheSame(oldItem: Album, newItem: Album): Boolean {
            return oldItem.artistName == newItem.artistName && oldItem.collectionName == newItem.collectionName
        }

        override fun areContentsTheSame(oldItem: Album, newItem: Album) = oldItem == newItem
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AlbumViewHolder {
        val rvItemCheckLineBinding = DataBindingUtil.inflate<RvItemAlbumBinding>(
            LayoutInflater.from(parent.context),
            R.layout.rv_item_album,
            parent,
            false)

        return AlbumViewHolder(
            rvItemCheckLineBinding
        )
    }

    override fun onBindViewHolder(holder: AlbumViewHolder, position: Int) {
        val item = getItem(position)


        holder.binding.rlListItemRoot.setOnClickListener {
            homeViewModel.onItemClick(item.collectionId)
        }

        Glide.with(context)
            .load(item.artworkUrl60)
            .into(holder.itemView.ivAlbum)

        holder.bindData(getItem(position))
    }

    class AlbumViewHolder(
        val binding: RvItemAlbumBinding
    ) : RecyclerView.ViewHolder(binding.rlListItemRoot) {
        private val tvAlbumTitle: TextView = itemView.findViewById(R.id.tvAlbumTitle)

        fun bindData(album: Album?) {
            album?.apply {
                tvAlbumTitle.text = album.collectionName
            }
        }
    }
}