package com.example.eletriccardio.ui

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.eletriccardio.R
import com.example.eletriccardio.databinding.ActivityCalcularAutonomiaBinding

class CalcularAutonomiaActivity : AppCompatActivity() {

    private lateinit var bindind : ActivityCalcularAutonomiaBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bindind = ActivityCalcularAutonomiaBinding.inflate(layoutInflater)
        setContentView(bindind.root)
        setupListeners()
        setupCachedResult()
    }

    @SuppressLint("SetTextI18n")
    private fun setupCachedResult() {
        bindind.result.text = "Resultado:"+getSharedPref().toString()
    }

    private fun setupListeners() {
        bindind.btnCalcular.setOnClickListener {
            calcular()
        }
    }

    @SuppressLint("SetTextI18n")
    fun calcular(){
        val bateria  =  bindind.bateria.text.toString().toFloat()
        val km  = bindind.km.text.toString().toFloat()
        val result =  (bateria / km)
        bindind.result.text = "Resultado:$result"
        salveSharedPref(result)
    }

    fun salveSharedPref(resultado: Float){
        val sharedPref = getPreferences(Context.MODE_PRIVATE) ?: return
        with(sharedPref.edit()){
            putFloat(getString(R.string.saved_calc), resultado)
            apply()
        }
    }

    fun getSharedPref(): Float {
        val sharredPref = getPreferences(Context.MODE_PRIVATE)
        return sharredPref.getFloat(getString(R.string.saved_calc), 0.0f)
    }

}


