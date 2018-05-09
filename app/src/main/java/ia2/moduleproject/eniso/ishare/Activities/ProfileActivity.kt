package ia2.moduleproject.eniso.ishare.Activities

import android.app.ProgressDialog
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.util.Log
import android.view.Menu
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
import ia2.moduleproject.eniso.ishare.Adapter.SharesAdapter
import ia2.moduleproject.eniso.ishare.Model.SharesModel
import ia2.moduleproject.eniso.ishare.Utils.*
import kotlinx.android.synthetic.main.layout_center_profile.*
import kotlinx.android.synthetic.main.snippet_top_profile.*

import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import java.io.UnsupportedEncodingException


class ProfileActivity : AppCompatActivity() {




    var ListShares=ArrayList<SharesModel>()
//    var adpater: SharesAdapter?=null

    var adapter : GridImageAdapter?=null
    private val TAG = "ProfileActivity"
    private val ACTIVITY_NUM = 3
    var imgURLs = ArrayList<String>()

    private val mContext = this@ProfileActivity
    private var mProgressBar: ProgressBar? = null
    private var profilePhoto : ImageView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)
        Log.d(TAG, "onCreate: starting.")
//        profileProgressBar!!.visibility = View.GONE


        loadPost(SaveSettings.userID)
        initImageLoader()
        setupBottomNavigationView()
        setupToolbar()
        setupActivityWidgets()
        setProfileImage()
       tempGridSetup()

        var gridView = findViewById<View>(R.id.gridView) as GridView
        var gridWidth = resources.displayMetrics.widthPixels
        var imageWidth = gridWidth/NUM_GRID_COLUMNS
        gridView.columnWidth=imageWidth
       adapter = GridImageAdapter(mContext, R.layout.layout_grid_imageview, "", imgURLs)
        gridView.adapter = adapter
        val npost = imgURLs.size
        tvPosts.text= npost.toString()
      //  loadLost()


    }





    private fun tempGridSetup() {
        ListShares.clear()
      //  Toast.makeText(applicationContext,ListShares[1].sharesImageURL!!,Toast.LENGTH_SHORT).show()
        for ( i in 0 ..ListShares.size-1){

            imgURLs.add((ListShares.get(i).sharesImageURL!!))
            adapter!!.notifyDataSetChanged()
        }
//        imgURLs.add("https://pbs.twimg.com/profile_images/616076655547682816/6gMRtQyY.jpg")
//        imgURLs.add("https://i.redd.it/9bf67ygj710z.jpg")
//        imgURLs.add("https://c1.staticflickr.com/5/4276/34102458063_7be616b993_o.jpg")
//        imgURLs.add("http://i.imgur.com/EwZRpvQ.jpg")
//        imgURLs.add("http://i.imgur.com/JTb2pXP.jpg")
//        imgURLs.add("https://i.redd.it/59kjlxxf720z.jpg")
//        imgURLs.add("https://i.redd.it/59kjlxxf720z.jpg")
//        imgURLs.add("https://i.redd.it/59kjlxxf720z.jpg")
//        imgURLs.add("https://i.redd.it/59kjlxxf720z.jpg")
//        imgURLs.add("https://i.redd.it/59kjlxxf720z.jpg")
//        imgURLs.add("https://i.redd.it/59kjlxxf720z.jpg")
//        imgURLs.add("https://i.redd.it/59kjlxxf720z.jpg")
//        imgURLs.add("https://i.redd.it/59kjlxxf720z.jpg")
//        imgURLs.add("https://i.redd.it/59kjlxxf720z.jpg")
//        imgURLs.add("https://i.redd.it/59kjlxxf720z.jpg")
//        imgURLs.add("https://i.redd.it/59kjlxxf720z.jpg")
//        imgURLs.add("https://i.redd.it/59kjlxxf720z.jpg")
//        imgURLs.add("https://i.redd.it/59kjlxxf720z.jpg")
//        imgURLs.add("https://i.redd.it/pwduhknig00z.jpg")
//        imgURLs.add("https://i.redd.it/clusqsm4oxzy.jpg")
//        imgURLs.add("https://i.redd.it/svqvn7xs420z.jpg")
//        imgURLs.add("http://i.imgur.com/j4AfH6P.jpg")
//        imgURLs.add("https://i.redd.it/89cjkojkl10z.jpg")
//        imgURLs.add("https://i.redd.it/aw7pv8jq4zzy.jpg")

        setupImageGrid(imgURLs)
    }

    private fun setupImageGrid(imgURLs: ArrayList<String>) {

        val gridView = findViewById<View>(R.id.gridView) as GridView
        var gridWidth = resources.displayMetrics.widthPixels
        var imageWidth = gridWidth/NUM_GRID_COLUMNS
        gridView.columnWidth=imageWidth
        val adapter = GridImageAdapter(mContext, R.layout.layout_grid_imageview, "", imgURLs)
        gridView.adapter = adapter
    }


    private fun setProfileImage() {
        Log.d(TAG, "setProfileImage: setting profile photo.")
        var imgURL=""
        if (ListShares.size!=0){
            display_name.text=ListShares.get(0).personName
             imgURL = ListShares.get(0).personImage!!
        }else{
             imgURL = "https://"+"www.androidcentral.com/sites/androidcentral.com/files/styles/xlarge/public/article_images/2016/08/ac-lloyd.jpg?itok=bb72IeLf"
        }


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



    private fun loadPost(userid:String) {
        val url= localhost +"/IshareServer/TweetList.php?op=2&user_id="+userid+"&StartFrom=0"
        lateinit var progressDialog: ProgressDialog


            progressDialog = ProgressDialog(mContext)
            progressDialog.setMessage("Uploading Data ...")
            progressDialog.setCancelable(false)

        val jsonObjReq = object : JsonObjectRequest(Method.POST,
                url, null, Response.Listener { response ->
            try {
                // Toast.makeText(applicationContext,response.toString(),Toast.LENGTH_LONG).show()
                Toast.makeText(applicationContext,response.getString("msg"), Toast.LENGTH_LONG).show()
                if ( response.getString("msg")=="has tweet"){
                    progressDialog.show()
                    ListShares.clear()
                    val tweets = JSONArray(response.getString("info"))
                    for (i in 0..tweets.length()-1){
                        val singleTweet= tweets.getJSONObject(i)
                        println(singleTweet)
                        ListShares.add(SharesModel(singleTweet.getString("tweet_id"),singleTweet.getString("tweet_text"),
                                singleTweet.getString("tweet_picture"),singleTweet.getString("tweet_date")
                                ,singleTweet.getString("first_name"),singleTweet.getString("picture_path"),
                                singleTweet.getString("user_id")))
                         adapter!!.notifyDataSetChanged()


                    }

                    for ( i in 0 ..ListShares.size-1){

                        imgURLs.add((ListShares.get(i).sharesImageURL!!))
                        adapter!!.notifyDataSetChanged()
                    }
                    setProfileImage()
                    val npost = imgURLs.size
                    tvPosts.text= npost.toString()
                    progressDialog.dismiss()
                }

            } catch (e: JSONException) {
                e.printStackTrace()
            }
        }, Response.ErrorListener { progressDialog.dismiss()}) {
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