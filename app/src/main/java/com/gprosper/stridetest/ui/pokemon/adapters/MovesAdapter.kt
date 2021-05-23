package com.gprosper.stridetest.ui.pokemon.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.gprosper.stridetest.R
import com.gprosper.stridetest.data.api.model.pokemon.Move
import com.gprosper.stridetest.data.api.model.pokemon.VersionGroupDetails
import com.gprosper.stridetest.databinding.ItemPokemonMoveBinding

/**
 * Created by gregprosper on 23,May,2021
 */
class MovesAdapter : ListAdapter<Move, MovesAdapter.ViewHolder>(MoveDiffUtil()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return ViewHolder(ItemPokemonMoveBinding.inflate(inflater, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class ViewHolder(val binding: ItemPokemonMoveBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(move: Move) {
            binding.moveNameLabel.text = move.name
            when(move.versionGroupDetails.last().moveLearnMethod) {
                "level-up" -> {
                    binding.moveLearnLabel.text = binding.root.context.getString(R.string.learned_at_level,
                        move.versionGroupDetails.last().levelLearnedAt)
                    binding.moveLearnLabel.visibility = View.VISIBLE
                    binding.moveLearnImageContainer.visibility = View.GONE
                }
                "egg" -> {
                    binding.moveLearnImage.setImageResource(R.drawable.egg)
                    binding.moveLearnLabel.visibility = View.GONE
                    binding.moveLearnImageContainer.visibility = View.VISIBLE
                }
                "machine" -> {
                    binding.moveLearnImage.setImageResource(R.drawable.tm)
                    binding.moveLearnLabel.visibility = View.GONE
                    binding.moveLearnImageContainer.visibility = View.VISIBLE
                }
                "tutor" -> {
                    binding.moveLearnLabel.text = binding.root.context.getString(R.string.move_tutor)
                    binding.moveLearnLabel.visibility = View.VISIBLE
                    binding.moveLearnImageContainer.visibility = View.GONE
                }
            }
        }
    }

    private class MoveDiffUtil: DiffUtil.ItemCallback<Move>() {
        override fun areItemsTheSame(oldItem: Move, newItem: Move): Boolean {
            return oldItem.name == newItem.name
        }

        override fun areContentsTheSame(oldItem: Move, newItem: Move): Boolean {
            return oldItem.name == newItem.name
        }
    }
}