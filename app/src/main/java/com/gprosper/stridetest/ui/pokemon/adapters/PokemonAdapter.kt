package com.gprosper.stridetest.ui.pokemon.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.gprosper.stridetest.R
import com.gprosper.stridetest.data.api.model.pokemon.PokemonDetail
import com.gprosper.stridetest.databinding.ItemPokemonCellBinding

/**
 * Created by gregprosper on 20,May,2021
 */
class PokemonAdapter() : ListAdapter<PokemonDetail, PokemonAdapter.ViewHolder>(PokemonDetailsDiffUtil()) {
    var onItemSelected: ((pokemonDetail: PokemonDetail) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return ViewHolder(ItemPokemonCellBinding.inflate(inflater, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position), onItemSelected)
    }

    class ViewHolder(private val binding: ItemPokemonCellBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(pokemonDetail: PokemonDetail, onItemSelected: ((pokemonDetail: PokemonDetail) -> Unit)? = null) {
            binding.root.setOnClickListener {
                onItemSelected?.invoke(pokemonDetail);
            }
            binding.pokemonName.text = pokemonDetail.nameCap
            binding.pokemonOrder.text = binding.root.context.getString(R.string.pokemon_order_number, pokemonDetail.order)
            Glide.with(binding.pokemonImage)
                .load(pokemonDetail.sprites.frontDefault)
                .placeholder(R.drawable.who)
                .into(binding.pokemonImage)
        }
    }

    private class PokemonDetailsDiffUtil: DiffUtil.ItemCallback<PokemonDetail>() {
        override fun areItemsTheSame(oldItem: PokemonDetail, newItem: PokemonDetail): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: PokemonDetail, newItem: PokemonDetail): Boolean {
            return oldItem.id == newItem.id
        }

    }
}