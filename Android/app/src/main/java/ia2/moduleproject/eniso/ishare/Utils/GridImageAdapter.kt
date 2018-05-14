package ia2.moduleproject.eniso.ishare.Utils

import android.content.Context
import android.graphics.Bitmap
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.ProgressBar

import com.nostra13.universalimageloader.core.ImageLoader
import com.nostra13.universalimageloader.core.assist.FailReason
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener

import java.util.ArrayList

import ia2.moduleproject.eniso.ishare.R




class GridImageAdapter(private val mContext: Context, private val layoutResource: Int, private val mAppend: String, private val imgURLs: ArrayList<String>) : ArrayAdapter<String>(mContext, layoutResource, imgURLs) {
    private val mInflater: LayoutInflater

    init {
        mInflater = mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
    }

    private class ViewHolder {
        internal var image: SqaureImageView? = null
        internal var mProgressBar: ProgressBar? = null
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var convertView = convertView

        /*
        Viewholder build pattern (Similar to recyclerview)
         */
        val holder: ViewHolder
        if (convertView == null) {
            convertView = mInflater.inflate(layoutResource, parent, false)
            holder = ViewHolder()
            holder.mProgressBar = convertView!!.findViewById(R.id.gridImageProgressbar) as ProgressBar
            holder.image = convertView.findViewById(R.id.gridImageView) as SqaureImageView

            convertView.tag = holder
        } else {
            holder = convertView.tag as ViewHolder
        }

        val imgURL = getItem(position)

        val imageLoader = ImageLoader.getInstance()

        imageLoader.displayImage(mAppend + imgURL!!, holder.image!!, object : ImageLoadingListener {
            override fun onLoadingStarted(imageUri: String, view: View) {
                if (holder.mProgressBar != null) {
                    holder.mProgressBar!!.visibility = View.VISIBLE
                }
            }

            override fun onLoadingFailed(imageUri: String, view: View, failReason: FailReason) {
                if (holder.mProgressBar != null) {
                    holder.mProgressBar!!.visibility = View.GONE
                }
            }

            override fun onLoadingComplete(imageUri: String, view: View, loadedImage: Bitmap) {
                if (holder.mProgressBar != null) {
                    holder.mProgressBar!!.visibility = View.GONE
                }
            }

            override fun onLoadingCancelled(imageUri: String, view: View) {
                if (holder.mProgressBar != null) {
                    holder.mProgressBar!!.visibility = View.GONE
                }
            }
        })

        return convertView
    }
}







