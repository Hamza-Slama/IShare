package ia2.moduleproject.eniso.ishare.ui

import android.app.ProgressDialog
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.util.Log
import android.view.View
import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx
import ia2.moduleproject.eniso.ishare.R
import android.widget.ProgressBar
import android.content.Intent

import android.widget.ImageView
import android.widget.GridView
import android.widget.Toast
import com.android.volley.*
import com.android.volley.toolbox.HttpHeaderParser
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.nostra13.universalimageloader.core.ImageLoader
import ia2.moduleproject.eniso.ishare.model.SharesModel
import ia2.moduleproject.eniso.ishare.utils.*

import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import java.io.UnsupportedEncodingException


class ProfileActivity : AppCompatActivity() {

    var following_user_id=""
    var ListShares = ArrayList<SharesModel>()
//    var adpater: SharesAdapter?=null

    var adapter: GridImageAdapter? = null
    private val TAG = "ProfileActivity"
    private val ACTIVITY_NUM = 3
    var imgURLs = ArrayList<String>()

    private val mContext = this@ProfileActivity
    private var mProgressBar: ProgressBar? = null
    private var profilePhoto: ImageView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)
        Log.d(TAG, "onCreate: starting.")

        initImageLoader()
        setupBottomNavigationView()
        setupToolbar()
        setupActivityWidgets()



//        profileProgressBar!!.visibility = View.GONE
        val intent = getIntent()
        if (intent.hasExtra("call activity")) {
            val bundle = intent.extras
             following_user_id = bundle.getString("user_id")
            loadPost(following_user_id)
            val picture_path = bundle.getString("picture_path")
            val first_name = bundle.getString("first_name")
            UniversalImageLoader.setImage(picture_path!!, profilePhoto!!, mProgressBar, "")
            display_name.text = first_name
            profileName.text = first_name
            textEditProfile.text= resources.getString(R.string.follow)
            textEditProfile.setBackgroundResource(R.color.link_blue)
            var test = true
            textEditProfile.setOnClickListener {
//                var count = -1

                if (test ) {
                    println("following_user_id= $following_user_id")
                    updateFollowing("1",following_user_id)
                    textEditProfile.text = resources.getString(R.string.unfollow)
                    textEditProfile.setBackgroundResource(R.color.mcam_color_light)
                    //count++
                    test=false
                }else {
                    println("following_user_id= $following_user_id")
                    updateFollowing("2",following_user_id)
                    textEditProfile.text= resources.getString(R.string.follow)
                    textEditProfile.setBackgroundResource(R.color.link_blue)
                    test=true

                }

            }
        } else {
            loadPost(SaveSettings.userID)

            setProfileImage()
            tempGridSetup()
        }

        var gridView = findViewById<View>(R.id.gridView) as GridView
        var gridWidth = resources.displayMetrics.widthPixels
        var imageWidth = gridWidth / NUM_GRID_COLUMNS
        gridView.columnWidth = imageWidth
        adapter = GridImageAdapter(mContext, R.layout.layout_grid_imageview, "", imgURLs)
        gridView.adapter = adapter
        val npost = imgURLs.size
        tvPosts.text = npost.toString()
        //  loadLost()


    }


    private fun tempGridSetup() {
        ListShares.clear()
//
//        for (i in 0..ListShares.size - 1) {
//
//            imgURLs.add((ListShares.get(i).sharesImageURL!!))
//            adapter!!.notifyDataSetChanged()
//        }
        setupImageGrid(imgURLs)
    }

    private fun setupImageGrid(imgURLs: ArrayList<String>) {

        val gridView = findViewById<View>(R.id.gridView) as GridView
        var gridWidth = resources.displayMetrics.widthPixels
        var imageWidth = gridWidth / NUM_GRID_COLUMNS
        gridView.columnWidth = imageWidth
        val adapter = GridImageAdapter(mContext, R.layout.layout_grid_imageview, "", imgURLs)
        gridView.adapter = adapter
    }


    private fun setProfileImage() {
        Log.d(TAG, "setProfileImage: setting profile photo.")
        var imgURL = ""
        display_name.text = SaveSettings.userName
        profileName.text = SaveSettings.userName
        imgURL = SaveSettings.picture_path
//        if (ListShares.size!=0){
////            display_name.text=SaveSettings.userName
//             imgURL = ListShares.get(0).personImage!!
//        }else{
//             imgURL = "https://"+"www.androidcentral.com/sites/androidcentral.com/files/styles/xlarge/public/article_images/2016/08/ac-lloyd.jpg?itok=bb72IeLf"
//        }


        UniversalImageLoader.setImage(imgURL!!, profilePhoto!!, mProgressBar, "")
    }

    private fun setupActivityWidgets() {
        mProgressBar = findViewById<View>(R.id.profileProgressBar) as ProgressBar
        mProgressBar!!.visibility = View.GONE
        profilePhoto = findViewById<View>(R.id.profile_photo) as ImageView
    }


    /**
     * Responsible for setting up the profile toolbar
     */
    private fun setupToolbar() {
        val toolbar = findViewById<View>(R.id.profileToolBar) as Toolbar
        setSupportActionBar(toolbar)

        val profileMenu = findViewById<View>(R.id.profileMenu) as ImageView
        profileMenu.setOnClickListener(View.OnClickListener {
            Log.d(TAG, "onClick: navigating to account settings.")
            val intent = Intent(mContext, AccountSettings::class.java)
            startActivity(intent)
        })
    }

    /**
     * BottomNavigationView setup
     */
    private fun setupBottomNavigationView() {
        Log.d(TAG, "setupBottomNavigationView: setting up BottomNavigationView")
        val bottomNavigationViewEx = findViewById<View>(R.id.bottomNavViewBar) as BottomNavigationViewEx
        BottomNavigationViewHelper.setupBottomNavigationView(bottomNavigationViewEx)
        BottomNavigationViewHelper.enableNavigation(mContext, bottomNavigationViewEx)
        val menu = bottomNavigationViewEx.menu
        val menuItem = menu.getItem(ACTIVITY_NUM)
        menuItem.isChecked = true
    }
//    override fun onCreateOptionsMenu(menu: Menu): Boolean {
//        menuInflater.inflate(R.menu.profile_menu, menu)
//        return true
//    }

private fun updateFollowing(op:String , user_following: String){

//    val url = localhost + "IshareServer/UserFollowing.php?op="+op+"&user_id="+SaveSettings.userID+"&following_user_id="+user_following
    var url = localhost + "/IshareServer/UserFollowing.php?op="+op+"&user_id="+SaveSettings.userID+"&following_user_id="+user_following
    println(url)
  //  Toast.makeText(mContext,url,Toast.LENGTH_SHORT).show()
    lateinit var progressDialog: ProgressDialog


    progressDialog = ProgressDialog(mContext)
    progressDialog.setMessage("Uploading Data ...")
    progressDialog.setCancelable(false)

//Toast.makeText(mContext,"test",Toast.LENGTH_SHORT).show()
    val jsonObjReq = object : JsonObjectRequest(Method.POST,
            url, null, Response.Listener { response ->
        try {
            progressDialog.show()

             //Toast.makeText(applicationContext,response.toString(),Toast.LENGTH_LONG).show()
            if (response.getString("msg") == "following") {
                Toast.makeText(mContext,response.getString("msg"),Toast.LENGTH_SHORT).show()
                progressDialog.dismiss()
            }else if (response.getString("msg")== "unfollowing"){
                Toast.makeText(mContext,response.getString("msg"),Toast.LENGTH_SHORT).show()
                progressDialog.dismiss()
            }

        } catch (e: JSONException) {
            e.printStackTrace()
        }
    }, Response.ErrorListener {  }) {}

    Volley.newRequestQueue(this).add(jsonObjReq)
}
    private fun loadPost(userid: String) {
        val url = localhost + "/IshareServer/TweetList.php?op=2&user_id=" + userid + "&StartFrom=0"
        lateinit var progressDialog: ProgressDialog


        progressDialog = ProgressDialog(mContext)
        progressDialog.setMessage("Uploading Data ...")
        progressDialog.setCancelable(false)

        val jsonObjReq = object : JsonObjectRequest(Method.POST,
                url, null, Response.Listener { response ->
            try {
                if (response.getString("msg") == "has tweet") {
                    progressDialog.show()
                    ListShares.clear()
                    val tweets = JSONArray(response.getString("info"))
                    for (i in 0..tweets.length() - 1) {
                        val singleTweet = tweets.getJSONObject(i)
                        println(singleTweet)
                        ListShares.add(SharesModel(singleTweet.getString("tweet_id"), singleTweet.getString("tweet_text"),
                                singleTweet.getString("tweet_picture"), singleTweet.getString("tweet_date")
                                , singleTweet.getString("first_name"), singleTweet.getString("picture_path"),
                                singleTweet.getString("user_id")))
                        adapter!!.notifyDataSetChanged()


                    }

                    for (i in 0..ListShares.size - 1) {

                        imgURLs.add((ListShares.get(i).sharesImageURL!!))
                        adapter!!.notifyDataSetChanged()
                    }
                    //setProfileImage()
                    val npost = imgURLs.size
                    tvPosts.text = npost.toString()
                    progressDialog.dismiss()
                }

            } catch (e: JSONException) {
                e.printStackTrace()
            }
        }, Response.ErrorListener { progressDialog.dismiss() }) {
            override fun parseNetworkResponse(response: NetworkResponse): Response<JSONObject> {
                try {
                    var cacheEntry: Cache.Entry? = HttpHeaderParser.parseCacheHeaders(response)
                    if (cacheEntry == null) {
                        cacheEntry = Cache.Entry()
                    }
                    val cacheHitButRefreshed = (3 * 60 * 1000).toLong() // in 3 minutes cache will be hit, but also refreshed on background
                    val cacheExpired = (24 * 60 * 60 * 1000).toLong() // in 24 hours this cache entry expires completely
                    val now = System.currentTimeMillis()
                    val softExpire = now + cacheHitButRefreshed
                    val ttl = now + cacheExpired
                    cacheEntry.data = response.data
                    cacheEntry.softTtl = softExpire
                    cacheEntry.ttl = ttl
                    var headerValue: String?
                    headerValue = response.headers["Date"]
                    if (headerValue != null) {
                        cacheEntry.serverDate = HttpHeaderParser.parseDateAsEpoch(headerValue)
                    }
                    headerValue = response.headers["Last-Modified"]
                    if (headerValue != null) {
                        cacheEntry.lastModified = HttpHeaderParser.parseDateAsEpoch(headerValue)
                    }
                    cacheEntry.responseHeaders = response.headers
                    val jsonString = String(response.data)
//                            Charset( HttpHeaderParser.parseCharset(response.headers)))
//                            HttpHeaderParser.parseCacheHeaders(response)
                    return Response.success(JSONObject(jsonString), cacheEntry)
                } catch (e: UnsupportedEncodingException) {
                    return Response.error(ParseError(e))
                } catch (e: JSONException) {
                    return Response.error(ParseError(e))
                }

            }

            override fun deliverResponse(response: JSONObject) {
                super.deliverResponse(response)
            }

            override fun deliverError(error: VolleyError) {
                super.deliverError(error)
            }

            override fun parseNetworkError(volleyError: VolleyError): VolleyError {
                return super.parseNetworkError(volleyError)
            }
        }

        Volley.newRequestQueue(this).add(jsonObjReq)
    }

    private fun initImageLoader() {
        val universalImageLoader = UniversalImageLoader(mContext)
        ImageLoader.getInstance().init(universalImageLoader.config)
    }

    companion object {
        private val NUM_GRID_COLUMNS = 3
    }
}