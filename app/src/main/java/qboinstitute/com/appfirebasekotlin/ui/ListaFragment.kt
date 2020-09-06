package qboinstitute.com.appfirebasekotlin.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.DocumentChange
import com.google.firebase.firestore.FirebaseFirestore
import qboinstitute.com.appfirebasekotlin.R
import qboinstitute.com.appfirebasekotlin.adapter.PersonaAdapter
import qboinstitute.com.appfirebasekotlin.model.Persona


class ListaFragment : Fragment() {

    lateinit var firestore: FirebaseFirestore
    lateinit var rvfirestore: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view : View = inflater.inflate(R.layout.fragment_lista, container, false)
        val lstpersonas : ArrayList<Persona> = ArrayList()
        rvfirestore = view.findViewById(R.id.rvfirestore)
        firestore = FirebaseFirestore.getInstance()
        firestore.collection("Persona")
            .addSnapshotListener{ snapshots, e ->
                if(e != null){
                    Toast.makeText(context, "Error en Firestore", Toast.LENGTH_LONG).show()
                }
                for(dc in snapshots!!.documentChanges){
                    if(dc.type == DocumentChange.Type.ADDED){
                        lstpersonas.add(Persona(
                            dc.document.data["nombre"].toString(),
                            dc.document.data["apellido"].toString(),
                            dc.document.data["edad"].toString().toInt()
                        ))
                    }
                }
                rvfirestore.adapter = PersonaAdapter(lstpersonas, view.context)
                rvfirestore.layoutManager = LinearLayoutManager(view.context)
            }
        return view
    }

}