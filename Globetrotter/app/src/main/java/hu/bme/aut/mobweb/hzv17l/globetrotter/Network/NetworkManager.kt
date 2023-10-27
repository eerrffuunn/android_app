package hu.bme.aut.mobweb.hzv17l.globetrotter.Network

import hu.bme.aut.mobweb.hzv17l.globetrotter.data.CountryData
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object NetworkManager {
    private const val SERVICE_URL = "https://restcountries.com/"
    val retrofit = Retrofit.Builder()
        .baseUrl(SERVICE_URL).addConverterFactory(GsonConverterFactory.create())
        .build()

    val countryAPI = retrofit.create(CountryAPI::class.java)

    fun getCountryByName(name : String): Call<List<CountryData>> {
        return countryAPI.getCountryByName(name)
    }
}