package qboinstitute.com.appfirebasekotlin.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.firestore.FirebaseFirestore
import qboinstitute.com.appfirebasekotlin.R


class RegistroFragment : Fragment() {

    lateinit var firestore : FirebaseFirestore
    lateinit var etnompersona: TextInputEditText
    lateinit var etapepersona: TextInputEditText
    lateinit var etedadpersona: TextInputEditText
    lateinit var btnregistrar: Button

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view : View = inflater.inflate(R.layout.fragment_registro, container, false)
        etnompersona = view.findViewById(R.id.etnompersona)
        etapepersona = view.findViewById(R.id.etapepersona)
        etedadpersona = view.findViewById(R.id.etedadpersona)
        btnregistrar = view.findViewById(R.id.btnregistrar)
        firestore = FirebaseFirestore.getInstance()
        btnregistrar.setOnClickListener {
            if(etnompersona.text?.isNotEmpty()!! &&
                etapepersona.text?.isNotEmpty()!! &&
                etedadpersona.text?.isNotEmpty()!!
                    ){
                registrarPersona()
            }
        }
        return view
    }

    private fun registrarPersona() {
        val persona = hashMapOf(
            "apellido" to etapepersona.text.toString(),
            "nombre" to etnompersona.text.toString(),
            "edad" to etedadpersona.text.toString().toInt()
        )
        firestore.collection("Persona")
            .add(persona)
            .addOnSuccessListener { documentReference ->
                Toast.makeText(context,
                "El ID del registro es: ${documentReference.id}",
                Toast.LENGTH_LONG).show()
            }
            .addOnFailureListener { e ->

            }
    }


}