package ia2.moduleproject.eniso.ishare.Utils

import android.content.Context
import android.graphics.Bitmap
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar

import com.nostra13.universalimageloader.cache.memory.impl.WeakMemoryCache
import com.nostra13.universalimageloader.core.DisplayImageOptions
import com.nostra13.universalimageloader.core.ImageLoader
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration
import com.nostra13.universalimageloader.core.assist.FailReason
import com.nostra13.universalimageloader.core.assist.ImageScaleType
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener

import ia2.moduleproject.eniso.ishare.R


class UniversalImageLoader(private val mContext: Context) {

    val config: ImageLoaderConfiguration
        get() {
            val defaultOptions = DisplayImageOptions.Builder()
                    .showImageOnLoading(defaultImage)
                    .showImageForEmptyUri(defaultImage)
                    .showImageOnFail(defaultImage)
                    .cacheOnDisk(true).cacheInMemory(true)
                    .cacheOnDisk(true).resetViewBeforeLoading(true)
                    .imageScaleType(ImageScaleType.EXACTLY)
                    .displayer(FadeInBitmapDisplayer(300)).build()

            return ImageLoaderConfiguration.Builder(mContext)
                    .defaultDisplayImageOptions(defaultOptions)
                    .memoryCache(WeakMemoryCache())
                    .diskCacheSize(100 * 1024 * 1024).build()
        }

    companion object {

        private val defaultImage = R.drawable.ic_android

        /**
         * this method can be sued to set images that are static. It can't be used if the images
         * are being changed in the Fragment/Activity - OR if they are being set in a list or
         * a grid
         * @param imgURL
         * @param image
         * @param mProgressBar
         * @param append
         */
        fun setImage(imgURL: String, image: ImageView, mProgressBar: ProgressBar?, append: String) {

            val imageLoader = ImageLoader.getInstance()
            imageLoader.displayImage(append + imgURL, image, object : ImageLoadingListener {
                override fun onLoadingStarted(imageUri: String, view: View) {
                    if (mProgressBar != null) {
                        mProgressBar.visibility = View.VISIBLE
                    }
                }

                override fun onLoadingFailed(imageUri: String, view: View, failReason: FailReason) {
                    if (mProgressBar != null) {
                        mProgressBar.visibility = View.GONE
                    }
                }

                override fun onLoadingComplete(imageUri: String, view: View, loadedImage: Bitmap) {
                    if (mProgressBar != null) {
                        mProgressBar.visibility = View.GONE
                    }
                }

                override fun onLoadingCancelled(imageUri: String, view: View) {
                    if (mProgressBar != null) {
                        mProgressBar.visibility = View.GONE
                    }
                }
            })
        }
    }
}