package com.example.rickandmorty.feature.character

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.RequestManager
import com.example.domain.entity.CharacterEntity
import com.example.rickandmorty.databinding.FragmentDetailBinding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class DetailFragment : Fragment() {

    @Inject
    lateinit var glideRequestManager: RequestManager
    private lateinit var binding: FragmentDetailBinding
    private val viewModel: DetailViewModel by viewModels()
    private val args: DetailFragmentArgs by navArgs()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getCharacter(args.characterId)
        viewModel.characterLiveData.observe(viewLifecycleOwner, characterObserver())
    }

    private fun characterObserver() = Observer<CharacterEntity> {
        with(binding) {
            if (it.id == 0) binding.gDetail.visibility = View.GONE
            tvCharacterDetailId.text = it.id.toString()
            tvCharacterDetailName.text = it.name
            tvCharacterDetailCreated.text = it.created
            tvCharacterDetailEspecies.text = it.species
            tvCharacterDetailGender.text = it.gender

            glideRequestManager.loadInto(it.image, ivCharacterDetailPicture)
        }
    }
}
