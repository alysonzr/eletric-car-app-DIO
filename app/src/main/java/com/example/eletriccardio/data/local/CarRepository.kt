package com.example.eletriccardio.data.local

import android.content.ContentValues
import android.content.Context
import android.provider.BaseColumns
import android.util.Log
import com.example.eletriccardio.Car
import com.example.eletriccardio.data.local.CarrosContract.CarEntry.COLUMN_NAME_BATERIA
import com.example.eletriccardio.data.local.CarrosContract.CarEntry.COLUMN_NAME_CAR_ID
import com.example.eletriccardio.data.local.CarrosContract.CarEntry.COLUMN_NAME_POTENCIA
import com.example.eletriccardio.data.local.CarrosContract.CarEntry.COLUMN_NAME_PRECO
import com.example.eletriccardio.data.local.CarrosContract.CarEntry.COLUMN_NAME_RECARGA
import com.example.eletriccardio.data.local.CarrosContract.CarEntry.COLUMN_NAME_URL_PHOTO

class CarRepository(private val context: Context) {

    fun save(car: Car) : Boolean {
        try {
            val dbHelper = CarsDbHelper(context)
            val db = dbHelper.writableDatabase

            val values = ContentValues().apply {
                put(COLUMN_NAME_CAR_ID, car.id)
                put(COLUMN_NAME_PRECO, car.preco)
                put(COLUMN_NAME_BATERIA, car.bateria)
                put(COLUMN_NAME_POTENCIA, car.potencia)
                put(COLUMN_NAME_RECARGA, car.recarga)
                put(COLUMN_NAME_URL_PHOTO, car.urlPhoto)
            }
            return if(db?.insert(CarrosContract.CarEntry.TABLE_NAME, null, values)!! >= 1)
                true
            else
                false
        } catch (ex: Exception){
            ex.message?.let { Log.e("Erro na inserção de dados", it) }
        }
        return false;
    }


    fun findCarById(id: Int) : Car{
        val dbHelper = CarsDbHelper(context)
        val db = dbHelper.writableDatabase

        //listagens das colunas a serem exibidas no resultado da Query
        val columns = arrayOf(BaseColumns._ID,
            COLUMN_NAME_CAR_ID,
            COLUMN_NAME_PRECO,
            COLUMN_NAME_BATERIA,
            COLUMN_NAME_POTENCIA,
            COLUMN_NAME_RECARGA,
            COLUMN_NAME_URL_PHOTO )

        val filter = "$COLUMN_NAME_CAR_ID = ?"
        val filterValues = arrayOf(id.toString())

        val cursor = db.query(
            CarrosContract.CarEntry.TABLE_NAME, //nome da tabela
            columns,
            filter,
            filterValues,
            null,
            null,
            null,
        )

        var itemId : Long = 0
        var preco = ""
        var bateria = ""
        var potencia = ""
        var recarga = ""
        var urlPhoto = ""

        with(cursor){
            while (moveToNext()){
                 itemId = getLong(getColumnIndexOrThrow(COLUMN_NAME_CAR_ID))
                 preco = getString(getColumnIndexOrThrow(COLUMN_NAME_PRECO))
                 bateria = getString(getColumnIndexOrThrow(COLUMN_NAME_BATERIA))
                 potencia = getString(getColumnIndexOrThrow(COLUMN_NAME_POTENCIA))
                 recarga = getString(getColumnIndexOrThrow(COLUMN_NAME_RECARGA))
                 urlPhoto = getString(getColumnIndexOrThrow(COLUMN_NAME_URL_PHOTO))
            }
        }
        cursor.close()
        return Car(
            id = itemId.toInt(),
            preco = preco,
            bateria = bateria,
            potencia = potencia,
            recarga = recarga,
            urlPhoto = urlPhoto,
            isFavorite = true
        )

    }

    fun saveIfNotExist(carro : Car){
        val car = findCarById(carro.id)
        if(car.id == ID_WHEN_NO_CAR){
            save(carro)
        }
    }

    fun getAll(): List<Car>{
        val dbHelper = CarsDbHelper(context)
        val db = dbHelper.writableDatabase

        //listagens das colunas a serem exibidas no resultado da Query
        val columns = arrayOf(BaseColumns._ID,
            COLUMN_NAME_CAR_ID,
            COLUMN_NAME_PRECO,
            COLUMN_NAME_BATERIA,
            COLUMN_NAME_POTENCIA,
            COLUMN_NAME_RECARGA,
            COLUMN_NAME_URL_PHOTO )

        val cursor = db.query(
            CarrosContract.CarEntry.TABLE_NAME, //nome da tabela
            columns,
            null,
            null,
            null,
            null,
            null,
        )

       val carros = mutableListOf<Car>()
        with(cursor){
            while (moveToNext()){
                val itemId = getLong(getColumnIndexOrThrow(COLUMN_NAME_CAR_ID))
                val preco = getString(getColumnIndexOrThrow(COLUMN_NAME_PRECO))
                val bateria = getString(getColumnIndexOrThrow(COLUMN_NAME_BATERIA))
                val potencia = getString(getColumnIndexOrThrow(COLUMN_NAME_POTENCIA))
                val recarga = getString(getColumnIndexOrThrow(COLUMN_NAME_RECARGA))
                val urlPhoto = getString(getColumnIndexOrThrow(COLUMN_NAME_URL_PHOTO))

                carros.add(
                    Car(
                        id = itemId.toInt(),
                        preco = preco,
                        bateria = bateria,
                        potencia = potencia,
                        recarga = recarga,
                        urlPhoto = urlPhoto,
                        isFavorite = true
                ))
            }
        }
        cursor.close()
        return carros
    }

    fun deleteById(id: Int){
        val dbHelper = CarsDbHelper(context)
        val db = dbHelper.writableDatabase
        try {
            val selection = "${CarrosContract.CarEntry.COLUMN_NAME_CAR_ID} = ?"
            val selectionArgs = arrayOf(id.toString())
            db.delete(CarrosContract.CarEntry.TABLE_NAME, selection, selectionArgs)
        } catch (ex: Exception){
            Log.e("Erro na exclusão", ex.message, ex)
        } finally {
            db?.close()
        }
    }

    companion object {
        const val  ID_WHEN_NO_CAR = 0
    }


}