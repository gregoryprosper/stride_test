package com.gprosper.stridetest.ui.pokemon

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.gprosper.stridetest.R
import com.gprosper.stridetest.data.api.model.pokemon.PokemonDetail
import com.gprosper.stridetest.databinding.FragmentPokemonDetailBinding
import com.gprosper.stridetest.ui.factory.ViewModelFactory
import com.gprosper.stridetest.ui.pokemon.adapters.MovesAdapter
import com.gprosper.stridetest.util.Resource

class PokemonDetailFragment : Fragment() {

    private val viewModel: PokemonDetailViewModel by viewModels() {
        ViewModelFactory()
    }

    private val args: PokemonDetailFragmentArgs by navArgs()

    private lateinit var binding: FragmentPokemonDetailBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPokemonDetailBinding.inflate(inflater, container, false)

        viewModel.pokemonDetail.observe(viewLifecycleOwner) {
            when(it) {
                is Resource.Loading -> {
                    binding.progressBar.visibility = View.VISIBLE
                }
                is Resource.Success -> {
                    showData(it)
                }
                is Resource.Error -> {
                    binding.progressBar.visibility = View.INVISIBLE
                    Toast.makeText(requireContext(), it.message, Toast.LENGTH_LONG).show()
                }
            }
        }

        return binding.root
    }

    override fun onStart() {
        super.onStart()
        viewModel.loadPokemonDetails(args.pokemonName)
    }

    private fun showData(it: Resource.Success<PokemonDetail>) {
        Glide.with(binding.pokemonImage)
            .load(it.data.sprites.frontDefault)
            .listener(object : RequestListener<Drawable> {
                override fun onResourceReady(
                    resource: Drawable?,
                    model: Any?,
                    target: Target<Drawable>?,
                    dataSource: DataSource?,
                    isFirstResource: Boolean
                ): Boolean {
                    binding.pokemonImageContainer.setBackgroundResource(R.drawable.pokemon_image_bg)
                    return false
                }

                override fun onLoadFailed(
                    e: GlideException?,
                    model: Any?,
                    target: Target<Drawable>?,
                    isFirstResource: Boolean
                ): Boolean {
                    return false
                }
            })
            .placeholder(R.drawable.who)
            .into(binding.pokemonImage)

        binding.pokemonName.text = it.data.nameCap
        binding.pokemonWeight.text =
            binding.root.context.getString(R.string.pokemon_weight, it.data.weight)
        binding.pokemonExp.text = binding.root.context.getString(R.string.pokemon_exp, it.data.exp)
        binding.detailsContainer.visibility = View.VISIBLE
        binding.movesContainer.visibility = View.VISIBLE
        binding.progressBar.visibility = View.INVISIBLE

        binding.movesRv.layoutManager = LinearLayoutManager(requireContext()).also {
            binding.movesRv.addItemDecoration(
                DividerItemDecoration(
                    requireContext(),
                    it.orientation
                )
            )
        }
        binding.movesRv.adapter = MovesAdapter().also { adapter ->
            adapter.submitList(it.data.levelUpMoves)
        }
    }
}