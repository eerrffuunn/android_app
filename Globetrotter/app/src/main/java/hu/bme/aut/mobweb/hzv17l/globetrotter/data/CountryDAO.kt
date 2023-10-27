package hu.bme.aut.mobweb.hzv17l.globetrotter.data

import androidx.room.*

@Dao
interface CountryDAO {
    @Query("SELECT * FROM countries")
    fun getAllCountries():List<CountryData>
    @Insert
    fun insertCountry(countrydata:CountryData)
    @Delete
    fun deleteCountry(countrydata: CountryData)
    @Update
    fun updateCountry(countryData: CountryData)


}