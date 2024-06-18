package com.example.eletriccardio.ui

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.eletriccardio.Car
import com.example.eletriccardio.R
import com.example.eletriccardio.data.local.CarRepository
import com.example.eletriccardio.databinding.FragmentCarBinding
import com.example.eletriccardio.databinding.FragmentFavoritosBinding
import com.example.eletriccardio.ui.adapter.CarAdapter

class FavoritosFragment : Fragment() {

    private lateinit var binding: FragmentFavoritosBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFavoritosBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupList(getAllCars())
    }

    override fun onResume() {
        super.onResume()
        setupList(getAllCars())
    }

    private fun getAllCars() : List<Car>{
        val repository = CarRepository(requireContext())
        return repository.getAll()
    }

    private fun setupList(listCars : List<Car> ) {
        val adapterCars = CarAdapter(listCars, true)
        binding.rvListaCarrosFavoritos.apply {
            adapter = adapterCars
            visibility = View.VISIBLE
        }

        adapterCars.carItemLister = { carro ->
            CarRepository(requireContext()).deleteById(carro.id)
            onResume()
        }
    }


}