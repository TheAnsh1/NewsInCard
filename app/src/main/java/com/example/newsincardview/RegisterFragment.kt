package com.example.newsincardview

import android.content.Intent
import android.os.Bundle
import android.provider.ContactsContract.CommonDataKinds.Email
import android.provider.ContactsContract.CommonDataKinds.Im
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import org.w3c.dom.Text
import kotlin.math.E

class RegisterFragment:Fragment() {

    private lateinit var Email:EditText
    private lateinit var Passworrd:EditText
    private lateinit var Name:EditText
    private lateinit var Phnoe:EditText
    private lateinit var auth: FirebaseAuth
    private lateinit var database: FirebaseDatabase
    private lateinit var Final:TextView
private lateinit var prg:ProgressBar
private lateinit var img:ImageView
private lateinit var signing:TextView
private lateinit var clickhandle:LinearLayout

      override fun onCreateView(
          inflater: LayoutInflater,
          container: ViewGroup?,
          savedInstanceState: Bundle?
      ): View? {
          val root =inflater.inflate(R.layout.register,container,false)
          Email =root.findViewById(R.id.email)
          Name =root.findViewById(R.id.name)
          Passworrd =root.findViewById(R.id.pass)
          Phnoe =root.findViewById(R.id.no)
          Final =root.findViewById(R.id.lauch)
          prg =root.findViewById(R.id.prg)
          img =root.findViewById(R.id.clic)
          signing =root.findViewById(R.id.singin)
          clickhandle =root.findViewById(R.id.clickhandle)
          clickhandle.setOnClickListener{
              img.visibility =View.VISIBLE
          }


          Final.setOnClickListener {
              if (Email.text.toString().equals(null)|| Passworrd.text.toString().equals(null) || Phnoe.text.toString().equals(null)|| Name.text.toString().equals(null)){

                  Toast.makeText(requireContext(),"Please fill the all fields",Toast.LENGTH_LONG).show()
              }
             else{

                  if(img.visibility.equals(View.VISIBLE)) {

                      cre(Email.text.toString(), Passworrd.text.toString())
                  }
                  else{
                      cre(Email.text.toString(), Passworrd.text.toString())
                      Toast.makeText(requireContext(),"Please fill the all fields",Toast.LENGTH_LONG).show()
                  }
             }
          }

          auth = Firebase.auth
          database =Firebase.database
          return  root
      }

    private fun cre(toString: String, toString1: String) {
        prg.visibility =View.VISIBLE

        auth.createUserWithEmailAndPassword(toString,toString1).addOnCompleteListener {
            if(it.isSuccessful){

                save(Email.text.toString(),Passworrd.text.toString(),Phnoe.text.toString(),Name.text.toString())
            }
        }.addOnFailureListener{
            Toast.makeText(requireContext(),"error registerd"+it.message,Toast.LENGTH_LONG).show()
            prg.visibility =View.GONE
        }



    }

    private fun save(toString: String, toString1: String, toString2: String, toString3: String) {
     val myref =Firebase.database
        var my =Load(toString,toString1,toString2,toString3)
        myref.getReference(toString3).setValue(my).addOnCompleteListener {
            if(it.isSuccessful){
                Toast.makeText(requireContext(),"successfully registerd",Toast.LENGTH_LONG).show()
           prg.visibility =View.GONE
            }
        }.addOnFailureListener{
            Toast.makeText(requireContext(),"error loaded",Toast.LENGTH_LONG).show()
            prg.visibility =View.GONE
        }

    }


}