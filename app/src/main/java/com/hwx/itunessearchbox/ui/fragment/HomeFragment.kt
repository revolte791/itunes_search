package com.hwx.itunessearchbox.ui.fragment

import android.os.Bundle
import android.view.*
import androidx.appcompat.widget.SearchView
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.hwx.itunessearchbox.NetworkError
import com.hwx.itunessearchbox.NetworkSuccess
import com.hwx.itunessearchbox.R
import com.hwx.itunessearchbox.api.entity.ITunesResponse
import com.hwx.itunessearchbox.onQueryTextChangeFlow
import com.hwx.itunessearchbox.ui.MainActivity
import com.hwx.itunessearchbox.ui.adapter.AlbumsAdapter
import com.hwx.itunessearchbox.viewmodel.HomeViewModel
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject


/**
 * Фрагмент со списком альбомов
 */
class HomeFragment : BaseFragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val homeViewModel: HomeViewModel by viewModels { viewModelFactory }

    private lateinit var albumsAdapter: AlbumsAdapter
    private lateinit var albumsRecycler: RecyclerView
    private lateinit var mToolbar: Toolbar



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        setHasOptionsMenu(true)
        return view ?: inflater.inflate(
            R.layout.fragment_home,
            container,
            false
        )

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews(view)
    }

    private fun initViews(view: View) {


        albumsAdapter = AlbumsAdapter(activity!!, homeViewModel)

        view.apply {
            albumsRecycler = findViewById(R.id.rvAlbumsRecycler)
            mToolbar = findViewById(R.id.toolbar)
        }

        albumsRecycler.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = albumsAdapter
        }

    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.toolbar_menu, menu)

        val mSearch = menu.findItem(R.id.action_search)
        val mSearchView: SearchView = mSearch.actionView as SearchView
        mSearchView.queryHint = "Search"

        mSearchView.onQueryTextChangeFlow()
            .debounce(1000)
            .onEach {  homeViewModel.filterAlbumsByName(it) }
            .launchIn(lifecycleScope)

        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {

        (activity!! as MainActivity).setSupportActionBar(mToolbar)


        homeViewModel.lvAlbums.observe(viewLifecycleOwner, Observer {
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

        homeViewModel.lvOnAlbumClick.observe(viewLifecycleOwner, Observer {
            it?.let {
                val action =
                    HomeFragmentDirections.actionHomeToAlbumDetails(it)
                findNavController().navigate(action)
                homeViewModel.navigateToAlbumDetailsCompleted()
            }
        })

        homeViewModel.lvShowError.observe(viewLifecycleOwner, Observer {
            it?.let {
                if (it.isNotEmpty()) {
                    showErrorWithAction(view!!, it) {
                        hideErrorIfShowed()
                    }
                    homeViewModel.mlvShowErrorCompleted()
                }
            }
        })

        super.onActivityCreated(savedInstanceState)
    }

    private fun bindData(response: ITunesResponse) {
        val list = response.results
            .filter { it.wrapperType == "collection" }
            .map{ it.toAlbum() }
            .toList()
            .sortedBy { it.collectionName[0].toUpperCase() }
        albumsAdapter.submitList(list)
    }

}