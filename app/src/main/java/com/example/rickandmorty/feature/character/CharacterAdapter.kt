package com.example.rickandmorty.feature.character

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.RequestManager
import com.example.domain.entity.CharacterEntity
import com.example.rickandmorty.R
import com.example.rickandmorty.databinding.ItemCharacterBinding

class CharacterAdapter(
    private val glideRequestManager: RequestManager,
    private var list: List<CharacterEntity> = listOf(),
    private val action: (CharacterEntity) -> Unit
) : RecyclerView.Adapter<CharacterViewHolder>() {

    fun setData(playerList: List<CharacterEntity>) {
        list = playerList.toMutableList()
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: CharacterViewHolder, position: Int) {
        holder.bind(list[position], action, glideRequestManager)
    }

    override fun getItemCount() = list.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = CharacterViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.item_character, parent, false)
    )
}

class CharacterViewHolder(itemRvBinding: View) : RecyclerView.ViewHolder(itemRvBinding) {

    fun bind(data: CharacterEntity, action: (CharacterEntity) -> Unit, glide: RequestManager) {
        with(ItemCharacterBinding.bind(itemView)) {
            tvCharacterName.text = data.name
            glide.loadInto(data.image, ivCharacterPicture)
            clCharacter.setOnClickListener { action(data) }
        }
    }
}
