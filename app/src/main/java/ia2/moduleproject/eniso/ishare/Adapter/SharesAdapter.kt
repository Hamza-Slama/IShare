package ia2.moduleproject.eniso.ishare.Adapter


/**
 * Created by hamza on 5/8/18.
 */


import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView


import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import de.hdodenhof.circleimageview.CircleImageView
import ia2.moduleproject.eniso.ishare.Model.Lost
import ia2.moduleproject.eniso.ishare.Model.SharesModel
import ia2.moduleproject.eniso.ishare.R


import java.util.ArrayList

class SharesAdapter(private val mDataset: ArrayList<SharesModel>) : RecyclerView.Adapter<SharesAdapter.DataObjectHolder>() {


    private var context: Context? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataObjectHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.lost_item_row, parent, false)
        val dataObjectHolder = DataObjectHolder(view)
        context = parent.context
        return dataObjectHolder
    }

    override fun onBindViewHolder(holder: DataObjectHolder, position: Int) {


        Glide
                .with(context)
                .load(mDataset[position].personImage)
                .centerCrop()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .placeholder(R.mipmap.ic_launcher)
                .crossFade()
                .into(holder.imageViewPerso_profile)

        holder.tv_userName_profile.setText(mDataset[position].personName)
        holder.tv_sharesDate.setText("${mDataset[position].sharesDate}")
        holder.tv_post_comment.setText("${mDataset[position].sharesText}")


        Glide
                .with(context)
                .load(mDataset[position].sharesImageURL)
                .centerCrop()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .placeholder(R.mipmap.ic_launcher)
                .crossFade()
                .into(holder.image_shares)

    }

    override fun getItemCount(): Int {
        return mDataset.size
    }

    inner class DataObjectHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        internal var imageViewPerso_profile: CircleImageView
        internal var tv_userName_profile: TextView
        internal var tv_post_comment: TextView
        internal var tv_sharesDate: TextView
        internal var image_shares: ImageView
        internal var iv_share_like: ImageView
        internal var tv_like_count: TextView


        init {

            imageViewPerso_profile = itemView.findViewById<CircleImageView>(R.id.imageViewPerso_profile) as CircleImageView
            tv_userName_profile = itemView.findViewById<TextView>(R.id.tv_userName_profile) as TextView
            tv_post_comment = itemView.findViewById<TextView>(R.id.tv_post_comment) as TextView
            tv_sharesDate = itemView.findViewById<TextView>(R.id.tv_sharesDate) as TextView
            image_shares = itemView.findViewById<ImageView>(R.id.image_shares) as ImageView
            iv_share_like = itemView.findViewById<ImageView>(R.id.iv_share_like) as ImageView
            tv_like_count = itemView.findViewById<TextView>(R.id.tv_like_count) as TextView




        }
    }
}
