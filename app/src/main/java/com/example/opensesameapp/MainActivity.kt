package com.example.opensesameapp

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity


class MainActivity : AppCompatActivity() {

    var userPassword: String? = "Password123";
    var editTextPass: EditText? = null;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        editTextPass = findViewById<View>(R.id.userPassword) as EditText;



        val button_first = findViewById<Button>(R.id.button_first)
        button_first.setOnClickListener {
            val passwordInput = editTextPass!!.text.toString()

            if(passwordInput.equals(userPassword)){
                Toast.makeText(this, "Logged in Successfully", Toast.LENGTH_SHORT).show();
                editTextPass!!.setText("")
                //Go to next activity
                val intent = Intent(this, MainActivity2::class.java)
                intent.putExtra("password",passwordInput);
                startActivity(intent)
            }
            else{
                Toast.makeText(
                    this,
                    "Failed to Log in: ",
                    Toast.LENGTH_SHORT
                ).show();
                editTextPass!!.setText("")
            }


        }

    }

}