package ia2.moduleproject.eniso.ishare.Fragment


import android.os.Bundle
import android.support.annotation.Nullable
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import com.nostra13.universalimageloader.core.ImageLoader
import ia2.moduleproject.eniso.ishare.Activities.AccountSettings

import ia2.moduleproject.eniso.ishare.R
import ia2.moduleproject.eniso.ishare.Utils.UniversalImageLoader
import kotlinx.android.synthetic.main.snippet_top_editprofiletoolbar.*


class EditProfileFragment : Fragment() {

    private var mProfilePhoto: ImageView? = null

    @Nullable
    override fun onCreateView(inflater: LayoutInflater, @Nullable container: ViewGroup?, @Nullable savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_editprofile, container, false)
        mProfilePhoto = view.findViewById<View>(R.id.profile_photo) as ImageView

        initImageLoader()

        setProfileImage()
        val backArrow = view.findViewById<View>(R.id.backArrow) as ImageView
        backArrow.setOnClickListener {
            Log.d(TAG, "onClick: navigating back to 'ProfileActivity'")
            activity!!.finish()
        }
//        backArrow.setOnClickListener {
//            Log.d(TAG,"on Click navigating back to profile Acitivity ")
//            Toast.makeText(activity,"this ais a toast",Toast.LENGTH_SHORT).show()
//        }

        return view
    }

    private fun initImageLoader() {
        val universalImageLoader = UniversalImageLoader(activity!!)
        ImageLoader.getInstance().init(universalImageLoader.config)
    }

    private fun setProfileImage() {
        Log.d(TAG, "setProfileImage: setting profile image.")
        val imgURL = "www.androidcentral.com/sites/androidcentral.com/files/styles/xlarge/public/article_images/2016/08/ac-lloyd.jpg?itok=bb72IeLf"
        UniversalImageLoader.setImage(imgURL, mProfilePhoto!!, null, "https://")
    }

    companion object {

        private val TAG = "EditProfileFragment"
    }
}