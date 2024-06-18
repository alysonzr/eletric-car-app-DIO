package com.example.eletriccardio.ui
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import com.example.eletriccardio.data.ApiCarsService
import com.example.eletriccardio.Car
import com.example.eletriccardio.R
import com.example.eletriccardio.data.local.CarRepository
import com.example.eletriccardio.databinding.FragmentCarBinding
import com.example.eletriccardio.ui.adapter.CarAdapter
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class CarFragment : Fragment() {

    private lateinit var binding: FragmentCarBinding
    private lateinit var carsAPI: ApiCarsService

    private var API_BASE_URL = "https://igorbag.github.io/cars-api/"

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCarBinding.inflate(layoutInflater)
        return binding.root
    }

    //Ja desenhou a tela parao usuario
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRetrofit()
        setupListeners()
    }

    override fun onResume() {
        super.onResume()
        if(checkForInternet(context)){
            getAllCars()
        } else {
            emptyState()
        }
    }

    private fun emptyState() {
        binding.progressBar.isVisible = false
        binding.recyclerView.isVisible = false
        binding.ivEmptyState.isVisible = true
        binding.tvNoWifi.isVisible = true
    }


    private fun setupAdpterCar(listCars : List<Car> ) {
        val adapterCars = CarAdapter(listCars)
        binding.recyclerView.apply {
            adapter = adapterCars
            visibility = View.VISIBLE
        }
        adapterCars.carItemLister = { carro ->
            val isSaved  = CarRepository(requireContext()).saveIfNotExist(carro)
        }
    }

    private fun setupListeners() {
        binding.fabCalcular.setOnClickListener {
            startActivity(Intent(context, CalcularAutonomiaActivity::class.java))
        }
    }

    private fun setupRetrofit(){
        val retrofit = Retrofit.Builder()
            .baseUrl(API_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        carsAPI = retrofit.create(ApiCarsService::class.java)
    }

    private fun getAllCars(){
        carsAPI.getAllCars().enqueue(object : Callback<List<Car>>{
            override fun onResponse(call: Call<List<Car>>, response: Response<List<Car>>) {
                if(response.isSuccessful){
                    binding.progressBar.isVisible = false
                    binding.ivEmptyState.isVisible = false
                    binding.tvNoWifi.isVisible = false
                    response.body()?.let {
                        setupAdpterCar(it)
                    }
                } else {
                    Toast.makeText(context, R.string.response_error, Toast.LENGTH_LONG).show()
                }
            }
            override fun onFailure(p0: Call<List<Car>>, p1: Throwable) {
                Toast.makeText(context, R.string.response_error, Toast.LENGTH_LONG).show()
            }
        })

    }

    private fun checkForInternet(context: Context?) : Boolean {
        val connectionManager = context?.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            val network = connectionManager.activeNetwork ?: return false
            val activityNetwork = connectionManager.getNetworkCapabilities(network) ?: return false

            return when {
                activityNetwork.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
                activityNetwork.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
                else -> false
            }

        } else {
            @Suppress("DEPRECATION")
            val networkInfo = connectionManager.activeNetworkInfo ?: return false
            @Suppress("DEPRECATION")
            return networkInfo.isConnected
        }
    }



}


