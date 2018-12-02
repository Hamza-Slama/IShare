package ia2.moduleproject.eniso.ishare.adapter

/**
 * Created by hamza on 5/9/18.
 */


/**
 * Created by hamza on 5/8/18.
 */


import android.content.Context
import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView


import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import de.hdodenhof.circleimageview.CircleImageView
import ia2.moduleproject.eniso.ishare.ui.ProfileActivity
import ia2.moduleproject.eniso.ishare.model.UserInfo
import ia2.moduleproject.eniso.ishare.R


import java.util.ArrayList

class SearchAdapter(private val mDataset: ArrayList<UserInfo>) : RecyclerView.Adapter<SearchAdapter.DataObjectHolder>() {


    private var context: Context? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataObjectHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.ticket_user_search, parent, false)
        val dataObjectHolder = DataObjectHolder(view)
        context = parent.context
        return dataObjectHolder
    }

    override fun onBindViewHolder(holder: DataObjectHolder, position: Int) {


        Glide
                .with(context)
                .load(mDataset[position].picture_path)
                .centerCrop()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .placeholder(R.mipmap.ic_launcher)
                .crossFade()
                .into(holder.profile_image)

        holder.username.setText(mDataset[position].first_name)
        holder.email.setText("${mDataset[position].email}")
        holder.profile_image.setOnClickListener {
            val intent = Intent(context,ProfileActivity::class.java)
            intent.putExtra("call activity","call activity")
            intent.putExtra("user_id",mDataset[position].user_id)
            intent.putExtra("first_name",mDataset[position].first_name)
//            intent.putExtra("email",mDataset[position].email)
            intent.putExtra("picture_path",mDataset[position].picture_path)
            context!!.startActivity(intent)

        }
    }

    override fun getItemCount(): Int {
        return mDataset.size
    }

    inner class DataObjectHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        internal var profile_image: CircleImageView
        internal var username: TextView
        internal var email: TextView



        init {
            profile_image = itemView.findViewById<CircleImageView>(R.id.profile_image) as CircleImageView
            username = itemView.findViewById<TextView>(R.id.username) as TextView
            email = itemView.findViewById<TextView>(R.id.email) as TextView

        }
    }
//    inner class MyOnClickListener : View.OnClickListener {
//        override fun onClick(v: View) {
//            val itemPosition = recyclerView.indexOfChild(v)
////            Log.e("Clicked and Position is ", itemPosition.toString())
//        }
//    }
}
