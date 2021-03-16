package com.example.opensesameapp

//import java.util.Base64
import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import android.util.Base64
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import java.security.MessageDigest
import java.util.*
import javax.crypto.Cipher
import javax.crypto.spec.SecretKeySpec


class MainActivity2 : AppCompatActivity() {



    /*
    fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.activity2_main, container, false)
    }

    fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.findViewById<Button>(R.id.button_second).setOnClickListener {
            findNavController().navigate(R.id.action_SecondFragment_to_FirstFragment)
        }
    }
    //NAV BUTTON END */


    var myDB: DatabaseHelper? = null
    var btnAdd: Button? = null
    var btnView: Button? = null
    var editText: EditText? = null
    var editTextPass: EditText? = null

    var AES: String = "AES"

    //val password = intent.getStringExtra("password")


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)

        //Get Password from previous activity
        var intent = intent
        var userPassword = intent.getStringExtra("password")
        //val accountList = ArrayList<Platform>() //FOR LIST ADAPTER
        val actionBar = supportActionBar
        actionBar!!.title = "Log Out"
        actionBar.setDisplayHomeAsUpEnabled(true)

        editText = findViewById<View>(R.id.editText) as EditText
        editTextPass = findViewById<View>(R.id.editTextPass) as EditText
        btnAdd = findViewById<View>(R.id.btnAdd) as Button
        btnView = findViewById<View>(R.id.btnView) as Button
        myDB = DatabaseHelper(this)

        btnAdd!!.setOnClickListener {
            val newEntry = editText!!.text.toString()
            val newEntry2 = editTextPass!!.text.toString()
            if ((editText!!.length() != 0) && (editTextPass!!.length() !=0 ) ){ //if both fields are filled
                AddData(newEntry, encrypt(newEntry2, userPassword.toString()))
                //*********  for list adapter start ***********

                //accountList.add(Platform(newEntry, newEntry2))
                //********** for list adapter end *************
                editText!!.setText("")
                editTextPass!!.setText("")
            } else {
                Toast.makeText(
                    this,
                    "You must put something in the text field!",
                    Toast.LENGTH_LONG
                ).show()
            }
        }

        btnView!!.setOnClickListener {
            val intent2 = Intent(this@MainActivity2, ViewListContents::class.java)
            //intent2.putExtra("password", userPassword.toString());
            intent2.putExtra("password", userPassword)
            startActivity(intent2)
        }


    }

    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)
    }

    //ENCRYPT START
    @Throws(Exception::class)
    private fun encrypt(Data: String, password: String): String? {
        val key = generateKey(password)
        val c = Cipher.getInstance(AES)
        c.init(Cipher.ENCRYPT_MODE, key)
        val encVal = c.doFinal(Data.toByteArray())
        return Base64.encodeToString(encVal, Base64.DEFAULT)
    }

    @Throws(Exception::class)
    private fun generateKey(password: String): SecretKeySpec {
        val digest = MessageDigest.getInstance("SHA-256")
        val bytes = password.toByteArray(charset("UTF-8"))
        digest.update(bytes, 0, bytes.size)
        val key = digest.digest()
        return SecretKeySpec(key, "AES")
    }


    fun AddData(newEntry: String?, newEntry2: String?) {
        val insertData = myDB!!.addData(newEntry, newEntry2)
        if (insertData == true) {
            Toast.makeText(this, "Data Successfully Inserted!", Toast.LENGTH_LONG).show()
        } else {
            Toast.makeText(this, "Something went wrong :(.", Toast.LENGTH_LONG).show()
        }
    }
}