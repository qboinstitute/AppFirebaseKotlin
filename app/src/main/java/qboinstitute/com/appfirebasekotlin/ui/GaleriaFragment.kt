package qboinstitute.com.appfirebasekotlin.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.gms.tasks.Task
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.ListResult
import com.google.firebase.storage.StorageReference
import qboinstitute.com.appfirebasekotlin.R
import qboinstitute.com.appfirebasekotlin.adapter.ImagenAdapter
import qboinstitute.com.appfirebasekotlin.model.Imagen


class GaleriaFragment : Fragment() {

    lateinit var rvimagenes : RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view : View =inflater.inflate(R.layout.fragment_galeria, container, false)
        rvimagenes = view.findViewById(R.id.rvimagenes)
        listaImagenes(view)
        return view
    }

    private fun listaImagenes(view: View) {
        val storage = FirebaseStorage.getInstance()
        val storageRef = storage.reference.child("/")
        val lstimagenes : ArrayList<Imagen> = ArrayList()
        val listAllTask : Task<ListResult> = storageRef.listAll()
        listAllTask.addOnCompleteListener {result ->
            val items: List<StorageReference> = result.result!!.items
            items.forEachIndexed { index, item ->
                item.downloadUrl.addOnSuccessListener {
                    lstimagenes.add(Imagen(it.toString()))
                }.addOnCompleteListener {
                    rvimagenes.adapter = ImagenAdapter(lstimagenes,
                    view.context)
                    rvimagenes.layoutManager = LinearLayoutManager(
                        view.context)
                }

            }
        }
    }


}