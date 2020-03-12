package com.hwx.itunessearchbox.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.hwx.itunessearchbox.Constants.SIMPE_DATE_FORMAT
import com.hwx.itunessearchbox.NetworkError
import com.hwx.itunessearchbox.NetworkSuccess
import com.hwx.itunessearchbox.R
import com.hwx.itunessearchbox.api.entity.ITunesResponse
import com.hwx.itunessearchbox.ui.adapter.TracksAdapter
import com.hwx.itunessearchbox.viewmodel.AlbumDetailsViewModel
import com.hwx.itunessearchbox.viewmodel.HomeViewModel
import kotlinx.android.synthetic.main.rv_item_album.view.*
import javax.inject.Inject

/**
 * Фрагмент со списком трэков к альбому
 */
class AlbumDetailsFragment : BaseFragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val albumDetailsViewModel: AlbumDetailsViewModel by viewModels { viewModelFactory }

    private lateinit var tracksAdapter: TracksAdapter
    private lateinit var tracksRecycler: RecyclerView
    private lateinit var tvAlbumTitleView: TextView
    private lateinit var tvAlbumCountry: TextView
    private lateinit var tvAlbumReleaseDate: TextView
    private lateinit var tvAlbumGenre: TextView
    private lateinit var ivAlbumImage: ImageView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return view ?: inflater.inflate(
            R.layout.fragment_album_details,
            container,
            false
        )

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews(view)
    }

    private fun initViews(view: View) {

        tracksAdapter = TracksAdapter(activity!!)

        view.apply {
            tracksRecycler = findViewById(R.id.rvAlbumsRecycler)
            tvAlbumTitleView = findViewById(R.id.tvAlbumTitle)
            tvAlbumCountry = findViewById(R.id.tvAlbumCountry)
            tvAlbumReleaseDate = findViewById(R.id.tvAlbumReleaseDate)
            tvAlbumGenre = findViewById(R.id.tvAlbumGenre)
            ivAlbumImage = findViewById(R.id.ivAlbumImage)

        }

        tracksRecycler.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = tracksAdapter
        }

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {

        albumDetailsViewModel.lvTracks.observe(viewLifecycleOwner, Observer {
            when(it) {
                is NetworkSuccess<ITunesResponse> -> bindData(it.data!!)
                is NetworkError -> {
                    view?.let { view ->
                        showErrorWithAction(view, it.message) {
                            hideErrorIfShowed()
                        }
                    }
                }
            }
        })

        albumDetailsViewModel.lvShowError.observe(viewLifecycleOwner, Observer {
            it?.let {
                if (it.isNotEmpty()) {
                    showErrorWithAction(view!!, it) {
                        hideErrorIfShowed()
                    }
                    albumDetailsViewModel.mlvShowErrorCompleted()
                }
            }
        })

        albumDetailsViewModel.getTracks()
        super.onActivityCreated(savedInstanceState)
    }

    private fun bindData(response: ITunesResponse) {
        val list = response.results.filter { it.wrapperType == "track" }.map{ it.toTrack() }.toList().sortedBy { it.trackNumber }
        val album = response.results.first { it.wrapperType == "collection" }.toAlbum()

        tvAlbumTitleView.text = album.collectionName
        tvAlbumCountry.text = album.country
        tvAlbumReleaseDate.text = SIMPE_DATE_FORMAT.format(album.releaseDate)
        tvAlbumGenre.text = album.primaryGenreName

        Glide.with(this)
            .load(album.artworkUrl100)
            .into(ivAlbumImage)

        tracksAdapter.submitList(list)
    }

}