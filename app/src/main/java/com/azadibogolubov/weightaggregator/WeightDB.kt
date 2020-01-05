package com.azadibogolubov.weightaggregator

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

// DB Helper class for weight table
class WeightDB(context: Context) : SQLiteOpenHelper(context, "weightdb.db", null, DB_VERSION) {
    // Initialize the context to null
    var context : Context? = null

    // Set the context provided by the constructor
    init {
        this.context = context
    }

    // Default values
    companion object{
        const val DB_VERSION = 1
        const val CREATE_TABLE_WEIGHT = "CREATE TABLE weight(id INT, reps INT, sets, INT, totalWeight INT, name TEXT)"
        const val SELECT_ALL_WEIGHTS = "SELECT * FROM weight"
        const val TABLE_NAME = "weight"
    }

    // Create the table
    override fun onCreate(db: SQLiteDatabase?) {
        db!!.execSQL(CREATE_TABLE_WEIGHT)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    // Insert a value into the table.
    fun insertIntoWeightTable(id: Int, reps: Int, sets: Int, name: String) : Boolean {
        val contentValues = ContentValues()
        contentValues.put("id", id)
        contentValues.put("reps", reps)
        contentValues.put("sets", sets)
        contentValues.put("totalWeight", reps * sets)
        contentValues.put("name", name)

        val rowId = writableDatabase.insert(TABLE_NAME, null, contentValues)
        return rowId > 0
    }

    // Select all from the table.
    fun selectWeightData() : ArrayList<WeightModel> {
        val weights : ArrayList<WeightModel> = ArrayList()
        val cursor = readableDatabase.rawQuery(SELECT_ALL_WEIGHTS, null)

        // Start at the beginning of the cursor
        if (cursor.moveToFirst()) {
            // Iterate everything
            while (!cursor.isAfterLast) {
                val weight= WeightModel()
                weight.id = cursor.getInt(cursor.getColumnIndex("id"))
                weight.reps = cursor.getInt(cursor.getColumnIndex("reps"))
                weight.sets = cursor.getInt(cursor.getColumnIndex("sets"))
                weight.totalWeight = cursor.getInt(cursor.getColumnIndex("totalWeight"))
                weight.name = cursor.getString(cursor.getColumnIndex("name"))

                // Add the current object to the list
                weights.add(weight)

                // Continue looping
                cursor.moveToNext()
            }
        }

        cursor.close()

        return weights
    }
}