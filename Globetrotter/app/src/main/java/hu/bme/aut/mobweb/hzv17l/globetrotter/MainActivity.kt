package hu.bme.aut.mobweb.hzv17l.globetrotter

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.view.Menu
import android.widget.EditText
import hu.bme.aut.mobweb.hzv17l.globetrotter.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    companion object{
        const val KEY_NAME = "KEY_NAME"
        const val  KEY_PASSWORD="KEY_PASSWORD"

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.CancelBtn.setOnClickListener(){
            binding.emailText.setText("")
            binding.pwdText.setText("")

        }


        binding.LoginBtn.setOnClickListener(){
            if(binding.emailText.text.toString().isNotEmpty()){
                var intent = Intent()
                intent.setClass(this,CountryListActivity::class.java)
                intent.putExtra(KEY_NAME,binding.emailText.text.toString())
                intent.putExtra(KEY_PASSWORD,binding.pwdText.text.toString())
                startActivity(intent)

            }

            else {
                binding.emailText.error = "cant be empty"
            }




        }

    }

}