package com.azadibogolubov.weightaggregator

import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem

import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        fab.setOnClickListener { view ->
            val db = WeightDB(this)
            val sets = setsInput.text.toString().toInt()
            val reps = repsInput.text.toString().toInt()
            val name = nameInput.text.toString()

            db.insertIntoWeightTable(1, sets, reps, name)

            val weights = db.selectWeightData()

            for (weight in weights) {
                Snackbar.make(view, "Reps: ${weight.reps}, Sets: ${weight.sets}, Name: ${weight.name}", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()
            }

        }

        // Add DB handling and initialization
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }
}
