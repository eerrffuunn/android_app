package hu.bme.aut.mobweb.hzv17l.globetrotter

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import hu.bme.aut.mobweb.hzv17l.globetrotter.Network.NetworkManager
import hu.bme.aut.mobweb.hzv17l.globetrotter.adapter.CountryAdapter
import hu.bme.aut.mobweb.hzv17l.globetrotter.data.CountryData
import hu.bme.aut.mobweb.hzv17l.globetrotter.data.CountryDatabase
import hu.bme.aut.mobweb.hzv17l.globetrotter.databinding.ActivityCountryListBinding
import hu.bme.aut.mobweb.hzv17l.globetrotter.fragment.CountryDialog
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class CountryListActivity : AppCompatActivity(), CountryDialog.CountryHandler {

     lateinit var binding: ActivityCountryListBinding
    private lateinit var countryAdapter:CountryAdapter

    override fun onCreate(savedInstanceState: Bundle?) {


        binding = ActivityCountryListBinding.inflate(layoutInflater)
        setContentView(binding.root)




        setSupportActionBar(findViewById(R.id.toolbar))
        binding.toolbarLayout.title = title
        Snackbar.make(binding.root,intent.getStringExtra(MainActivity.KEY_NAME).toString(),Snackbar.LENGTH_LONG).show()
        binding.fab.setOnClickListener { CountryDialog().show(supportFragmentManager,"Dialog")

            Thread {
               var dbCountries = CountryDatabase.getInstance(this).countryDao().getAllCountries()
                runOnUiThread(){
                    countryAdapter = CountryAdapter(this,dbCountries)
                    binding.recyclerCountry.adapter = countryAdapter
                }

            }.start()

        }
        super.onCreate(savedInstanceState)

    }
    override fun onSaveInstanceState(savedInstanceState: Bundle) {
        // Save the user's current game state


        // Always call the superclass so it can save the view hierarchy state
        super.onSaveInstanceState(savedInstanceState)
    }
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_country_list, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onBackPressed() {
        Snackbar.make(binding.root,"Press LogOut",Snackbar.LENGTH_LONG).show()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId==R.id.about){
            Snackbar.make(binding.root,getString(R.string.neptun).toString(),Snackbar.LENGTH_LONG).show()
        }else if(item.itemId==R.id.map){
            Snackbar.make(binding.root,"map",Snackbar.LENGTH_LONG).show()

        }else if(item.itemId==R.id.logout){
            finish()
        }
        return super.onOptionsItemSelected(item)
    }

    override fun countryAdded(country: String) {
        NetworkManager.getCountryByName(country).enqueue(object  : Callback<List<CountryData>>{
            override fun onResponse(
                call: Call<List<CountryData>>,
                response: Response<List<CountryData>>
            ) {
                if(response.isSuccessful){
                    countryAdapter.addCountry(response.body()!![0])
                }else{
                    Snackbar.make(binding.root,"Error : "+response.message(),Snackbar.LENGTH_LONG).show()
                }
            }

            override fun onFailure(call: Call<List<CountryData>>, t: Throwable) {
                t.printStackTrace()
                Snackbar.make(binding.root,"Network reuqest error occured , check LOG",Snackbar.LENGTH_LONG).show()
            }
        })
    }
}