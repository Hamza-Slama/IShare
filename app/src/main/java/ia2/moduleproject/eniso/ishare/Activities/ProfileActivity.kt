package ia2.moduleproject.eniso.ishare.Activities

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.util.Log
import android.view.Menu
import android.view.View
import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx
import ia2.moduleproject.eniso.ishare.R
import ia2.moduleproject.eniso.ishare.Utils.BottomNavigationViewHelper
import android.widget.ProgressBar
import kotlinx.android.synthetic.main.activity_profile.*
import android.content.Intent
import android.support.v4.app.FragmentActivity
import android.support.v7.widget.GridLayoutManager
import android.widget.ImageView
import ia2.moduleproject.eniso.ishare.Utils.GridImageAdapter
import android.widget.GridView
import com.android.volley.*
import com.android.volley.toolbox.HttpHeaderParser
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.nostra13.universalimageloader.core.ImageLoader
import ia2.moduleproject.eniso.ishare.Adapter.LostAdapter
import ia2.moduleproject.eniso.ishare.Model.Lost
import ia2.moduleproject.eniso.ishare.Utils.UniversalImageLoader
import kotlinx.android.synthetic.main.activity_home.*
import org.json.JSONException
import org.json.JSONObject
import java.io.UnsupportedEncodingException


class ProfileActivity : AppCompatActivity() {

    var costomAdapter: LostAdapter?=null
    private val lostList = ArrayList<Lost>()





    private val TAG = "ProfileActivity"
    private val ACTIVITY_NUM = 3


    private val mContext = this@ProfileActivity
    private var mProgressBar: ProgressBar? = null
    private var profilePhoto : ImageView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)
        Log.d(TAG, "onCreate: starting.")
//        profileProgressBar!!.visibility = View.GONE
        initImageLoader()
        setupBottomNavigationView()
        setupToolbar()
        setupActivityWidgets()
        setProfileImage()
       tempGridSetup()


      //  loadLost()

//        var layoutManager = GridLayoutManager(this@ProfileActivity,3)
//        recycler_view.layoutManager = layoutManager
//        costomAdapter = LostAdapter(lostList)
//        recycler_view.adapter = costomAdapter
    }





    private fun tempGridSetup() {
        val imgURLs = ArrayList<String>()
//        imgURLs.add("https://pbs.twimg.com/profile_images/616076655547682816/6gMRtQyY.jpg")
        imgURLs.add("https://i.redd.it/9bf67ygj710z.jpg")
        imgURLs.add("https://c1.staticflickr.com/5/4276/34102458063_7be616b993_o.jpg")
        imgURLs.add("http://i.imgur.com/EwZRpvQ.jpg")
        imgURLs.add("http://i.imgur.com/JTb2pXP.jpg")
        imgURLs.add("https://i.redd.it/59kjlxxf720z.jpg")
        imgURLs.add("https://i.redd.it/59kjlxxf720z.jpg")
        imgURLs.add("https://i.redd.it/59kjlxxf720z.jpg")
        imgURLs.add("https://i.redd.it/59kjlxxf720z.jpg")
        imgURLs.add("https://i.redd.it/59kjlxxf720z.jpg")
        imgURLs.add("https://i.redd.it/59kjlxxf720z.jpg")
        imgURLs.add("https://i.redd.it/59kjlxxf720z.jpg")
        imgURLs.add("https://i.redd.it/59kjlxxf720z.jpg")
        imgURLs.add("https://i.redd.it/59kjlxxf720z.jpg")
        imgURLs.add("https://i.redd.it/59kjlxxf720z.jpg")
        imgURLs.add("https://i.redd.it/59kjlxxf720z.jpg")
        imgURLs.add("https://i.redd.it/59kjlxxf720z.jpg")
        imgURLs.add("https://i.redd.it/59kjlxxf720z.jpg")
        imgURLs.add("https://i.redd.it/pwduhknig00z.jpg")
        imgURLs.add("https://i.redd.it/clusqsm4oxzy.jpg")
        imgURLs.add("https://i.redd.it/svqvn7xs420z.jpg")
        imgURLs.add("http://i.imgur.com/j4AfH6P.jpg")
        imgURLs.add("https://i.redd.it/89cjkojkl10z.jpg")
        imgURLs.add("https://i.redd.it/aw7pv8jq4zzy.jpg")

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
        val imgURL = "www.androidcentral.com/sites/androidcentral.com/files/styles/xlarge/public/article_images/2016/08/ac-lloyd.jpg?itok=bb72IeLf"
        UniversalImageLoader.setImage(imgURL, profilePhoto!!, mProgressBar, "https://")
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




//    private fun loadLost() {
//        val url2 = "http://api.tvmaze.com/singlesearch/shows?q=lost&embed=episodes"
//
//
//        val jsonObjReq = object : JsonObjectRequest(Method.GET,
//                url2, null, Response.Listener { response ->
//            try {
//                println("------------------------------------------------------------------- response = "+response)
//                val emedded = response.getJSONObject("_embedded")
//                val episodes = emedded.getJSONArray("episodes")
//                for (i in 0 until episodes.length()) {
//
//                    val episode = episodes.getJSONObject(i)
//                    val name = episode.getString("name")
//                    Log.i("Provera Object", name)
//
//                    val image = episode.getJSONObject("image")
//                    val imageMedium = image.getString("medium")
//                    Log.i("Provera Object", imageMedium.toString())
//
//                    val id = episode.getInt("id")
//                    Log.i("Provera Object", id.toString())
//
//                    val url = episode.getString("url")
//                    Log.i("Provera Object", url)
//
//                    val season = episode.getInt("season")
//                    Log.i("Provera Object", season.toString())
//
//                    val lostItem = Lost(name, imageMedium, id)
//                    lostList.add(lostItem)
//                    costomAdapter!!.notifyDataSetChanged()
//
//
//                }
//            } catch (e: JSONException) {
//                e.printStackTrace()
//            }
//        }, Response.ErrorListener { }) {
//            override fun parseNetworkResponse(response: NetworkResponse): Response<JSONObject> {
//                try {
//                    var cacheEntry: Cache.Entry? = HttpHeaderParser.parseCacheHeaders(response)
//                    if (cacheEntry == null) {
//                        cacheEntry = Cache.Entry()
//                    }
//                    val cacheHitButRefreshed = (3 * 60 * 1000).toLong() // in 3 minutes cache will be hit, but also refreshed on background
//                    val cacheExpired = (24 * 60 * 60 * 1000).toLong() // in 24 hours this cache entry expires completely
//                    val now = System.currentTimeMillis()
//                    val softExpire = now + cacheHitButRefreshed
//                    val ttl = now + cacheExpired
//                    cacheEntry.data = response.data
//                    cacheEntry.softTtl = softExpire
//                    cacheEntry.ttl = ttl
//                    var headerValue: String?
//                    headerValue = response.headers["Date"]
//                    if (headerValue != null) {
//                        cacheEntry.serverDate = HttpHeaderParser.parseDateAsEpoch(headerValue)
//                    }
//                    headerValue = response.headers["Last-Modified"]
//                    if (headerValue != null) {
//                        cacheEntry.lastModified = HttpHeaderParser.parseDateAsEpoch(headerValue)
//                    }
//                    cacheEntry.responseHeaders = response.headers
//                    val jsonString = String(response.data)
////                            Charset( HttpHeaderParser.parseCharset(response.headers)))
////                            HttpHeaderParser.parseCacheHeaders(response)
//                    return Response.success(JSONObject(jsonString), cacheEntry)
//                } catch (e: UnsupportedEncodingException) {
//                    return Response.error(ParseError(e))
//                } catch (e: JSONException) {
//                    return Response.error(ParseError(e))
//                }
//
//            }
//
//            override fun deliverResponse(response: JSONObject) {
//                super.deliverResponse(response)
//            }
//
//            override fun deliverError(error: VolleyError) {
//                super.deliverError(error)
//            }
//
//            override fun parseNetworkError(volleyError: VolleyError): VolleyError {
//                return super.parseNetworkError(volleyError)
//            }
//        }
//
//        Volley.newRequestQueue(this).add(jsonObjReq)
//    }
private fun initImageLoader() {
    val universalImageLoader = UniversalImageLoader(mContext)
    ImageLoader.getInstance().init(universalImageLoader.config)
}
    companion object {
    private val NUM_GRID_COLUMNS = 3
    }
}