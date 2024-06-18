package com.example.eletriccardio.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.net.toUri
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.eletriccardio.Car
import com.example.eletriccardio.R
import com.example.eletriccardio.databinding.ItemAdapterBinding

class CarAdapter(
    private val carros: List<Car>,
    private val isFavoriteScreen: Boolean = false
) : RecyclerView.Adapter<CarAdapter.ViewHolder>() {

    var carItemLister: (Car) -> Unit = {}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
      val inflater =  LayoutInflater.from(parent.context)
      val binding = ItemAdapterBinding.inflate(inflater, parent, false)
      return ViewHolder(binding)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        viewHolder.bind(carros[position])
    }

    override fun getItemCount() = carros.size

    open inner class ViewHolder(
        private val binding: ItemAdapterBinding,
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(car: Car) {
            binding.txtValor.text = car.preco
            binding.valorBateria.text = car.bateria
            binding.valorPotencia.text = car.potencia
            binding.valorRecarga.text = car.recarga
            if(isFavoriteScreen){
                binding.ivFavorite.setImageResource(R.drawable.ic_star_selected)
            }
            binding.ivFavorite.setOnClickListener {
                carItemLister(car)
                setupFavorite(car)
            }
            binding.imgCarro.load(car.urlPhoto) {
                placeholder(R.drawable.img_car) // Imagem padrão enquanto carrega
                error(R.drawable.img_car) // Imagem em caso de erro
                size(width = 200, height = 150)
            }
        }

        private fun ViewHolder.setupFavorite(car: Car) {
            car.isFavorite = !car.isFavorite

            if (car.isFavorite)
                binding.ivFavorite.setImageResource(R.drawable.ic_star_selected)
            else
                binding.ivFavorite.setImageResource(R.drawable.baseline_star_border_24)
        }
    }


}

