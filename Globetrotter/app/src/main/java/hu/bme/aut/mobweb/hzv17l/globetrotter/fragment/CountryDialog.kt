package hu.bme.aut.mobweb.hzv17l.globetrotter.fragment

import android.app.Dialog
import android.content.Context
import androidx.appcompat.app.AlertDialog

import android.os.Bundle
import android.widget.EditText
import androidx.fragment.app.DialogFragment
import hu.bme.aut.mobweb.hzv17l.globetrotter.data.CountryData
import hu.bme.aut.mobweb.hzv17l.globetrotter.data.Flags
import hu.bme.aut.mobweb.hzv17l.globetrotter.data.Name
import hu.bme.aut.mobweb.hzv17l.globetrotter.databinding.DialogCountryNameBinding


class CountryDialog: DialogFragment() {
    interface  CountryHandler{
        fun countryAdded(country: String)
    }
    lateinit var countryHandler:CountryHandler

    override fun onAttach(context: Context) {

        super.onAttach(context)
        if(context is CountryHandler){
            countryHandler=context
        }else {
            throw RuntimeException(
                "The Activity is not implementing the countryHandler interface.")
        }
    }

    lateinit var etCountryName:EditText

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        val dialogBuilder=AlertDialog.Builder(requireContext())
        dialogBuilder.setTitle("country dialog")

        val dialogBinding = DialogCountryNameBinding.inflate(layoutInflater)
        dialogBuilder.setView(dialogBinding.root)
        etCountryName=dialogBinding.etCountryName
        dialogBuilder.setPositiveButton("Ok") {
                dialog, which ->
        }
        dialogBuilder.setNegativeButton("Cancel") {
                dialog, which ->
        }
        return dialogBuilder.create()
    }

    override fun onResume() {
        super.onResume()
        val positiveButton = (dialog as AlertDialog).getButton(Dialog.BUTTON_POSITIVE)
        positiveButton.setOnClickListener(){
            if(etCountryName.text.isNotEmpty()){
                countryHandler.countryAdded(
                     etCountryName.text.toString())
            }else{
                etCountryName.error = "cant be empty"
            }


        }
    }
}