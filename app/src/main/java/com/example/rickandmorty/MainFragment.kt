package com.example.rickandmorty

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AbsListView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.domain.entity.CharacterEntity
import com.example.rickandmorty.MainFragmentDirections.Companion.toDetailFragment
import com.example.rickandmorty.databinding.FragmentMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainFragment : Fragment() {

    private lateinit var binding: FragmentMainBinding
    private lateinit var rvAdapter: CharacterAdapter
    private val viewModel: MainViewModel by viewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.getCharacters()
        viewModel.charactersLiveData.observe(viewLifecycleOwner, characterObserver())

        binding.pbCharacters.visibility = View.VISIBLE
        binding.rvMainCharacters.apply {
            layoutManager = LinearLayoutManager(context)
            rvAdapter = CharacterAdapter {
                findNavController().navigate(toDetailFragment(it.id))
            }
            adapter = rvAdapter
            addOnScrollListener(scrollListener())
        }
    }

    private fun characterObserver() = Observer<List<CharacterEntity>> {
        rvAdapter.setData(it)
        binding.pbCharacters.visibility = View.GONE
    }

    private fun scrollListener() = object : RecyclerView.OnScrollListener() {

        private var isScrolling = false
        private var currentItems = 0
        private var totalItems = 0
        private var scrolledItems = 0

        override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
            super.onScrollStateChanged(recyclerView, newState)
            if (newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL)
                isScrolling = true
        }

        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            super.onScrolled(recyclerView, dx, dy)
            (binding.rvMainCharacters.layoutManager as LinearLayoutManager?)?.let {
                currentItems = it.childCount
                totalItems = it.itemCount
                scrolledItems = it.findFirstVisibleItemPosition()
            }
            if (isScrolling && (currentItems + scrolledItems == totalItems)) {
                isScrolling = false
                viewModel.getCharacters()
                binding.pbCharacters.visibility = View.VISIBLE
            }
        }
    }
}
