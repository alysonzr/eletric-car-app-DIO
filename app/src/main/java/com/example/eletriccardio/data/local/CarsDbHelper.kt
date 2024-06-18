package com.example.eletriccardio.data.local

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper


class CarsDbHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DB_VERSION) {

    companion object{
        private const val DB_VERSION = 1
        const val DATABASE_NAME = "DbCar.db"
    }

    override fun onCreate(sqLiteDatabase: SQLiteDatabase?) {
        sqLiteDatabase?.execSQL(CarrosContract.SQL_TABLE)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL(CarrosContract.SQL_DELETE_ENRTIES)
        onCreate(db)
    }

    override fun onDowngrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        onUpgrade(db, oldVersion, newVersion)
    }
}