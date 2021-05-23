package com.gprosper.stridetest.ui.pokemon

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.TranslateAnimation
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.navGraphViewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.gprosper.stridetest.R
import com.gprosper.stridetest.databinding.FragmentPokemonBinding
import com.gprosper.stridetest.ui.factory.ViewModelFactory
import com.gprosper.stridetest.ui.pokemon.adapters.PokemonAdapter
import com.gprosper.stridetest.util.Resource

class PokemonFragment : Fragment() {

    private val viewModel: PokemonViewModel by navGraphViewModels(R.id.mobile_navigation) {
        ViewModelFactory()
    }

    private lateinit var binding: FragmentPokemonBinding
    private lateinit var adapter: PokemonAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPokemonBinding.inflate(inflater, container, false)

        setupRecyclerView()

        viewModel.pokemon.observe(viewLifecycleOwner) {
            when(it) {
                is Resource.Loading -> {
                    binding.progressBar.visibility = View.VISIBLE
                }
                is Resource.Success -> {
                    binding.progressBar.visibility = View.INVISIBLE
                    adapter.submitList(it.data)
                }
                is Resource.Error -> {
                    binding.progressBar.visibility = View.INVISIBLE
                    Toast.makeText(requireContext(), it.message, Toast.LENGTH_LONG).show()
                }
            }
        }

        viewModel.loadingMorePokemon.observe(viewLifecycleOwner) {
            when (it) {
                true -> {
                    showLoadingBanner()
                }
                else -> {
                    hideLoadingBanner()
                }
            }
        }

        return binding.root
    }

    private fun setupRecyclerView() {
        binding.pokemonRv.adapter = PokemonAdapter()
            .apply {
                onItemSelected = {
                    val action = PokemonFragmentDirections
                        .actionNavigationPokemonToPokemonDetailFragment(
                            title = "${it.nameCap} Details",
                            pokemonName = it.name
                        )
                    findNavController().navigate(action)
                }
            }.also { adapter = it }
        binding.pokemonRv.layoutManager = GridLayoutManager(requireContext(), 3, GridLayoutManager.VERTICAL, false)
        binding.pokemonRv.addOnScrollListener(object: RecyclerView.OnScrollListener(){
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (!recyclerView.canScrollVertically(1) && newState == RecyclerView.SCROLL_STATE_IDLE) {
                    viewModel.loadMorePokemon()
                }
            }
        })
    }

    private fun showLoadingBanner() {
        binding.loadingBanner.visibility = View.VISIBLE
        val animate = TranslateAnimation(0f,
            0f,
            binding.loadingBanner.height.toFloat(),
            0f).apply {
            duration = 250
            fillAfter = false
        }
        binding.loadingBanner.startAnimation(animate)
    }

    private fun hideLoadingBanner() {
        binding.loadingBanner.visibility = View.INVISIBLE
        val animate = TranslateAnimation(0f,
            0f,
            0f,
            binding.loadingBanner.height.toFloat()).apply {
            duration = 250
            fillAfter = false
        }
        binding.loadingBanner.startAnimation(animate)
    }
}