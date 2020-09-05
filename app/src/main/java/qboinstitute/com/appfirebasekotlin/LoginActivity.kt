package qboinstitute.com.appfirebasekotlin

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
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