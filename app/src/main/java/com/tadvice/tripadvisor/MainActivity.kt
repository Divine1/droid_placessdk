package com.tadvice.tripadvisor

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.widget.Autocomplete
import com.google.android.libraries.places.widget.AutocompleteActivity
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode

class MainActivity : AppCompatActivity() {

    private val AUTOCOMPLETE_REQUEST_CODE = 1

    var startCustomIntent = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
        Log.d("MainActivity", "1234 resultCode ${it.resultCode} ")
        Log.d("MainActivity", "1111 data ${it.data} ")
        Log.d("MainActivity", it.toString())

        Log.d("MainActivity", "ok ${Activity.RESULT_OK} error ${AutocompleteActivity.RESULT_ERROR} cancelled ${Activity.RESULT_CANCELED}")

        val place = Autocomplete.getPlaceFromIntent(it.data!!)
        Log.i("MainActivity", "Place: ${place.name}, ${place.id}")
        customActivityResult(it.resultCode,it.data)

    }

    //var startCustomIntent = null;
    //var startCustomIntent:ActivityResultLauncher<Intent!>;
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        Log.d("MainActivity", " custom onCreate ")


        // Set the fields to specify which types of place data to
        // return after the user has made a selection.
        //val fields = listOf(Place.Field.ID, Place.Field.NAME)

        // Start the autocomplete intent.
        //val intent = Autocomplete.IntentBuilder(AutocompleteActivityMode.FULLSCREEN, fields).build(this)
        //startActivityForResult(intent, AUTOCOMPLETE_REQUEST_CODE)


    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {

        menuInflater.inflate(R.menu.main_menu,menu);
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when(item.itemId){
            R.id.menu_search -> {
                val fields = listOf(Place.Field.ID, Place.Field.NAME)
                val intent = Autocomplete.IntentBuilder(AutocompleteActivityMode.FULLSCREEN, fields).build(this)

                startCustomIntent.launch(intent)
            }
        }
        return super.onOptionsItemSelected(item)
    }



    fun customActivityResult(resultCode: Int, data: Intent?) {
        when (resultCode) {
            Activity.RESULT_OK -> {
                data?.let {
                    val place = Autocomplete.getPlaceFromIntent(data)
                    Log.i("MainActivity", "Place: ${place.name}, ${place.id}")
                }
            }
            AutocompleteActivity.RESULT_ERROR -> {
                // TODO: Handle the error.
                data?.let {
                    val status = Autocomplete.getStatusFromIntent(data)
                    Log.i("MainActivity", status.statusMessage!!)
                }
            }
            Activity.RESULT_CANCELED -> {
                Log.i("MainActivity", "user cancelled the operation")
                // The user canceled the operation.
            }
        }
    }

}