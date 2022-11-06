package com.example.newsincardview

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.TestLooperManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlin.math.E

class LogInFragment:Fragment() {


    private lateinit var  auth:FirebaseAuth
    private lateinit var mGoogleSignInClient: GoogleSignInClient
    private val RC_SIGN_IN: Int=100
    private  lateinit var Email:EditText
    private lateinit var password:EditText
    private lateinit var googlelogin:LinearLayout
    private lateinit var register:TextView
    private lateinit var loginemail:TextView
    private lateinit var loginprg:ProgressBar
    private lateinit var auth2:FirebaseAuth
    private lateinit var forget:TextView

      override fun onCreateView(
           inflater: LayoutInflater,
           container: ViewGroup?,
           savedInstanceState: Bundle?
       ): View? {
           var root =inflater.inflate(R.layout.login,container,false)
           Email =root.findViewById(R.id.email)
           password =root.findViewById(R.id.pass)
           googlelogin =root.findViewById(R.id.google)
           register =root.findViewById(R.id.regis)
          loginemail =root.findViewById(R.id.logingemail)
          auth2 =FirebaseAuth.getInstance()
          forget =root.findViewById(R.id.frg)
          auth = Firebase.auth
          val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
              .requestIdToken(getString(R.string.default_web_client_id))
              .requestEmail()
              .build()
          mGoogleSignInClient = GoogleSignIn.getClient(requireContext(),gso)//getClient(this, gso)
          loginemail.setOnClickListener {
              if(Email.text.toString().equals(null)  && password.text.toString().equals(null))
              {

                  Toast.makeText(requireContext(),"please fill the all fields",Toast.LENGTH_LONG).show()
          }
              else
              {
                  create(Email.text.toString(),password.text.toString())


              }
          }
          googlelogin.setOnClickListener {

              val signInIntent = mGoogleSignInClient.signInIntent
              startActivityForResult(signInIntent, RC_SIGN_IN)

          }

        forget.setOnClickListener {
            if(Email.text.toString().equals(null)){
                Toast.makeText(requireContext(),"please enter the Email",Toast.LENGTH_LONG).show()

            }
            else {
                auth2.sendPasswordResetEmail(Email.text.toString()).addOnCompleteListener {
                    if (it.isSuccessful) {

                        Toast.makeText(requireContext(),
                            "Email sent please check also spam folder of email",
                            Toast.LENGTH_LONG).show()
                    }
                }
            }
        }
           return root

       }

    private fun create(toString: String, toString1: String) {

    auth.signInWithEmailAndPassword(toString,toString1).addOnCompleteListener {
        if(it.isSuccessful){

            Toast.makeText(requireContext(),"Successfully login",Toast.LENGTH_LONG).show()
            var intent =Intent(requireContext(),NewsClass::class.java)
            startActivity(intent)
            activity?.finish()
        }
    }.addOnFailureListener{
        Toast.makeText(requireContext(),"please check email and password",Toast.LENGTH_LONG).show()

    }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        // Result returned from launching the Intent from GoogleSignInClient.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            // The Task returned from this call is always completed, no need to attach

            val task: Task<GoogleSignInAccount> = GoogleSignIn.getSignedInAccountFromIntent(data)

            val account = task.getResult(ApiException::class.java)
            Log.e("the value of account is",account.toString())
            firebaselogin(account)
        }
    }

    private fun firebaselogin(account: GoogleSignInAccount?) {
        val cred =GoogleAuthProvider.getCredential(account?.idToken,null)

        auth.signInWithCredential(cred).addOnCompleteListener { task ->
            if(task.isSuccessful)
            {
                Log.e("successfull","Succsefuull")

               val intent =Intent(requireContext(),NewsClass::class.java)
                startActivity(intent)
                activity?.finish()


            }
            else{

            }

        }


    }




}