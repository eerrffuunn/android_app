package hu.bme.aut.mobweb.hzv17l.globetrotter.Network

import hu.bme.aut.mobweb.hzv17l.globetrotter.data.CountryData
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path






interface CountryAPI {

    @GET("v3.1/name/{name}")
    fun getCountryByName(@Path("name") name: String) : Call<List<CountryData>>
}