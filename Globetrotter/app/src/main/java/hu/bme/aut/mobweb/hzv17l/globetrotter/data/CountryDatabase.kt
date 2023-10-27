package hu.bme.aut.mobweb.hzv17l.globetrotter.data

import android.content.Context
import androidx.room.*

@Database(entities = arrayOf(CountryData::class),version = 1)
@TypeConverters(Converters::class)
abstract class CountryDatabase: RoomDatabase() {
    abstract fun countryDao(): CountryDAO
    companion object{
        private var INSTANCE: CountryDatabase? = null

        fun getInstance(context: Context):CountryDatabase{
            if(INSTANCE == null){
                INSTANCE = Room.databaseBuilder(context.applicationContext,
                    CountryDatabase::class.java,"globetrotter.db")
                    .fallbackToDestructiveMigration()
                    .build()
            }
            return  INSTANCE!!
        }
        fun destroyInstance(){
            INSTANCE = null
        }
    }


}