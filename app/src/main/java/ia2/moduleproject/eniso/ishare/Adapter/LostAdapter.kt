package ia2.moduleproject.eniso.ishare.Adapter


/**
 * Created by hamza on 28/02/18.
 */


import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView


import com.bumptech.glide.Glide
import ia2.moduleproject.eniso.ishare.Model.Lost
import ia2.moduleproject.eniso.ishare.R


import java.util.ArrayList

class LostAdapter(private val mDataset: ArrayList<Lost>) : RecyclerView.Adapter<LostAdapter.DataObjectHolder>() {
    private var context: Context? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataObjectHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.lost_item_row, parent, false)
        val dataObjectHolder = DataObjectHolder(view)
        context = parent.context
        return dataObjectHolder
    }

    override fun onBindViewHolder(holder: DataObjectHolder, position: Int) {

        holder.title.setText(mDataset[position].title)
        holder.body.setText("${mDataset[position].id}")
        Glide
                .with(context)
                .load(mDataset[position].image)
                .centerCrop()
                .placeholder(R.mipmap.ic_launcher)
                .crossFade()
                .into(holder.image)

    }

    override fun getItemCount(): Int {
        return mDataset.size
    }

    inner class DataObjectHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        internal var title: TextView
        internal var body: TextView
        internal var image: ImageView

        init {

            title = itemView.findViewById<TextView>(R.id.title) as TextView
            body = itemView.findViewById<TextView>(R.id.tv_id) as TextView
            image = itemView.findViewById<ImageView>(R.id.image) as ImageView
        }
    }
}
