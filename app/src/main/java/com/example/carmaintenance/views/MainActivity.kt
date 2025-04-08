package com.example.carmaintenance.views

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.activity.viewModels
import android.util.Log
import com.example.carmaintenance.R
import com.example.carmaintenance.databinding.MainActivityBinding
import com.example.carmaintenance.viewmodel.UserViewModel

class MainActivity: AppCompatActivity() {

    private var register:Boolean = true

    private lateinit var binding:MainActivityBinding

    private val viewModel: UserViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = MainActivityBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        binding.continueButton.setOnClickListener {
            viewModel.authUser(name = binding.inputName.text.toString(), email = binding.inputEmail.text.toString(), password = binding.inputPassword.text.toString(), reg = register )


        }

        binding.textSignIn.setOnClickListener{
            if (register){
                binding.loginRegisterTitle.text = resources.getString(R.string.loginTitle)
                binding.nameText.visibility = View.GONE
                binding.inputName.visibility = View.GONE
                binding.textAccountQuestion.text = resources.getString(R.string.registerAccountQuestion)
                binding.textSignIn.text = resources.getString(R.string.textSignIn)
            }
            else {
                binding.loginRegisterTitle.text = resources.getString(R.string.registerTitle)
                binding.nameText.visibility = View.VISIBLE
                binding.inputName.visibility = View.VISIBLE
                binding.textAccountQuestion.text = resources.getString(R.string.loginAccountQuestion)
                binding.textSignIn.text = resources.getString(R.string.textLogIn)
            }
            register = !register
        }

        viewModel.authState.observe(this){ success ->
            if(success){
                Log.d(Companion.TAG, "User authenticated")
                //val vehicles = viewModel.userVehicles.value!!.map{it}.toTypedArray()
                //Log.d(TAG, "Vehicles: $vehicles")
                val intent = Intent(this, MenuActivity::class.java)
                startActivity(intent)
                finish()
            }
        }


    }

    fun validateUserFields(){

    }

    companion object {
        private const val TAG: String = "MainActivity"
    }
}