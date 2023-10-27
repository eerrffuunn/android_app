package hu.bme.aut.mobweb.hzv17l.globetrotter.adapter

import android.content.ActivityNotFoundException
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.android.material.snackbar.Snackbar
import hu.bme.aut.mobweb.hzv17l.globetrotter.CountryListActivity
import hu.bme.aut.mobweb.hzv17l.globetrotter.data.CountryData
import hu.bme.aut.mobweb.hzv17l.globetrotter.data.CountryDatabase
import hu.bme.aut.mobweb.hzv17l.globetrotter.databinding.ActivityMainBinding
import hu.bme.aut.mobweb.hzv17l.globetrotter.databinding.CountryItemBinding
import hu.bme.aut.mobweb.hzv17l.globetrotter.databinding.DialogCountryNameBinding

 class CountryAdapter : RecyclerView.Adapter<CountryAdapter.ViewHOlder> {
    var countryItems = mutableListOf<CountryData>()
     val context:Context
    constructor(context: Context,dbCounties:List<CountryData> ){

        this.context=context
        countryItems.addAll(dbCounties)

    }

     inner class ViewHOlder(val binding: CountryItemBinding ):RecyclerView.ViewHolder(binding.root){

     }

     override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHOlder {
         return ViewHOlder(CountryItemBinding.inflate(LayoutInflater.from(parent.context),parent,false))
     }

     override fun onBindViewHolder(holder: ViewHOlder, position: Int) {
         val currentCountry = countryItems[position]
         holder.binding.tvCountryName.text = currentCountry.name.common
         holder.binding.tvCountryCode.text = currentCountry.cca3
         //holder.binding.ivFlag.tooltipText = currentCountry.flags.png
         Glide.with(context).load(currentCountry.flags.png).into(holder.binding.ivFlag)

         holder.binding.btnDelete.setOnClickListener(){
             deleteCountry(holder.adapterPosition)
         }
     }

     override fun getItemCount(): Int {
         return countryItems.size
     }
     private fun deleteCountry(position: Int){
         Thread{
             CountryDatabase.getInstance(context).countryDao().deleteCountry(countryItems.get(position))

             (context as CountryListActivity).runOnUiThread{
                 countryItems.removeAt(position)
                 notifyDataSetChanged()

                 notifyItemRemoved(position)
             }
         }.start()
     }
     public fun addCountry(country: CountryData){
         Thread{
             try {
                 CountryDatabase.getInstance(context).countryDao().insertCountry(country)

                 (context as CountryListActivity).runOnUiThread{
                     countryItems.add(country)
                     notifyDataSetChanged()

                     notifyItemInserted(countryItems.lastIndex)
                 }
             }catch (e:Exception){
                 Snackbar.make((context as CountryListActivity).binding.root,e.localizedMessage,Snackbar.LENGTH_LONG).show()


             }


         }.start()
     }

     }





