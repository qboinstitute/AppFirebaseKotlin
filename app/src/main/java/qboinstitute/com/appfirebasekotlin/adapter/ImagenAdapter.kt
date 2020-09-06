package qboinstitute.com.appfirebasekotlin.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import qboinstitute.com.appfirebasekotlin.R
import qboinstitute.com.appfirebasekotlin.model.Imagen

class ImagenAdapter(private var lstimagenes:List<Imagen>,
private val context: Context)
    : RecyclerView.Adapter<ImagenAdapter.ViewHolder>()
{
    class ViewHolder(itemview: View) : RecyclerView.ViewHolder(itemview)
    {
        val ivimgstorage: ImageView = itemview.findViewById(R.id.ivimgstorage)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImagenAdapter.ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return ViewHolder(layoutInflater.inflate(
            R.layout.image_item, parent, false))
    }

    override fun onBindViewHolder(holder: ImagenAdapter.ViewHolder, position: Int) {
        val item = lstimagenes[position]
        Picasso.get().load(item.urlimagen).into(holder.ivimgstorage)
    }

    override fun getItemCount(): Int {
        return lstimagenes.size
    }
}