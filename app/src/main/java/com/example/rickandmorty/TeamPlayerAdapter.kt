package com.example.rickandmorty

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.domain.entity.CharacterEntity
import com.example.rickandmorty.databinding.ItemCharacterBinding

class CharacterAdapter(
    private var list: List<CharacterEntity> = listOf(),
    private val action: (CharacterEntity) -> Unit
) : RecyclerView.Adapter<CharacterViewHolder>() {

    fun setData(playerList: List<CharacterEntity>) {
        list = playerList.toMutableList()
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: CharacterViewHolder, position: Int) {
        holder.bind(list[position], action)
    }

    override fun getItemCount() = list.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = CharacterViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.item_character, parent, false)
    )
}

class CharacterViewHolder(itemRvBinding: View) : RecyclerView.ViewHolder(itemRvBinding) {

    fun bind(data: CharacterEntity, action: (CharacterEntity) -> Unit) {
        with(ItemCharacterBinding.bind(itemView)) {
            tvCharacterName.text = data.name
            Glide.with(itemView)
                .load(data.image)
                .centerCrop()
                .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                .placeholder(R.drawable.character_image_placeholder)
                .into(ivCharacterPicture)
            clCharacter.setOnClickListener { action(data) }
        }
    }
}
