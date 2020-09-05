package qboinstitute.com.appfirebasekotlin

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        pbautenticacion.visibility = View.GONE
        //Autenticación con email y password a Firebase
        btnloginfirebase.setOnClickListener {
            pbautenticacion.visibility = View.VISIBLE
            if(etusuario.text?.isNotEmpty()!! &&
                    etpassword.text?.isNotEmpty()!!){
                pbautenticacion.visibility = View.VISIBLE
                FirebaseAuth.getInstance().signInWithEmailAndPassword(
                    etusuario.text.toString(), etpassword.text.toString()
                ).addOnCompleteListener {
                    if(it.isSuccessful){
                        ingresarAlApp(it.result?.user?.email ?: "",
                        TipoAutenticacion.FIREBASE.name,
                        "", "")
                    }else{
                        alertarError()
                    }
                }
            }
        }
        //Autenticacion con Gmail.
        btnlogingoogle.setOnClickListener {
            pbautenticacion.visibility = View.VISIBLE
            val configlogin = GoogleSignInOptions
                .Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build()
            val cliente: GoogleSignInClient = GoogleSignIn.getClient(this, configlogin)
            startActivityForResult(cliente.signInIntent, 777)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == 777){
            val task: Task<GoogleSignInAccount>
            = GoogleSignIn.getSignedInAccountFromIntent(data)
            try{
                val cuenta : GoogleSignInAccount? = task.getResult(ApiException::class.java)
                if(cuenta != null){
                    val credencial : AuthCredential = GoogleAuthProvider
                        .getCredential(cuenta.idToken, null)
                    FirebaseAuth.getInstance().signInWithCredential(credencial)
                        .addOnCompleteListener {
                            if(it.isSuccessful){
                                ingresarAlApp(
                                    cuenta.email.toString(),
                                    TipoAutenticacion.GOOGLE.name,
                                    cuenta.displayName.toString(),
                                    cuenta.photoUrl.toString()
                                )
                            }else{
                                alertarError()
                            }
                        }
                }
            }catch (e : ApiException){
                alertarError()
            }

        }
    }


    private fun alertarError() {
        pbautenticacion.visibility = View.GONE
        Toast.makeText(applicationContext,
            "Error en la autenticación.", Toast.LENGTH_LONG).show()
    }

    private fun ingresarAlApp(email: String, tipo: String, nombre: String,
                              urlimg: String)
    {
        val intent = Intent(this, MainActivity::class.java)
            .apply {
            putExtra("email", email)
                putExtra("tipo", tipo)
                putExtra("nombre", nombre)
                putExtra("urlimg", urlimg)
        }
        startActivity(intent)
        pbautenticacion.visibility = View.GONE
    }
}