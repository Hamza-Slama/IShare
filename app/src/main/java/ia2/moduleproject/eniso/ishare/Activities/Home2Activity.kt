package ia2.moduleproject.eniso.ishare.Activities

import android.app.ProgressDialog
import android.content.Intent
import android.os.AsyncTask
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.View
import android.widget.Toast
import com.android.volley.*
import com.android.volley.toolbox.HttpHeaderParser
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley

import ia2.moduleproject.eniso.ishare.R
import ia2.moduleproject.eniso.ishare.Utils.BottomNavigationViewHelper
import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx


import com.nostra13.universalimageloader.core.ImageLoader
import ia2.moduleproject.eniso.ishare.Adapter.SharesAdapter
import ia2.moduleproject.eniso.ishare.Fragment.MessagesFragment
import ia2.moduleproject.eniso.ishare.Fragment.HomeFragment
import ia2.moduleproject.eniso.ishare.Fragment.CameraFragment
import ia2.moduleproject.eniso.ishare.Model.SharesModel
import ia2.moduleproject.eniso.ishare.SectionsPagerAdapter
import ia2.moduleproject.eniso.ishare.Utils.Operations
import ia2.moduleproject.eniso.ishare.Utils.SaveSettings
import ia2.moduleproject.eniso.ishare.Utils.UniversalImageLoader
import kotlinx.android.synthetic.main.activity_home2.*
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import java.io.UnsupportedEncodingException
import java.net.HttpURLConnection
import java.net.URL


class Home2Activity : AppCompatActivity() {

    private val TAG = "HomeActivity"
    private val ACTIVITY_NUM = 0

    private val mContext = this@Home2Activity
//    var costomAdapter: LostAdapter?=null
//    private val lostList = ArrayList<Lost>()


    var ListShares=ArrayList<SharesModel>()
    var adpater:SharesAdapter?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val saveSettings= SaveSettings(this)
        saveSettings.loadSettings()

        setContentView(R.layout.activity_home2)

        loadLost(SaveSettings.userID)
        Toast.makeText(mContext, SaveSettings.userID, Toast.LENGTH_SHORT).show()
        Log.d(TAG, "onCreate: starting.")
        initImageLoader()
        setupBottomNavigationView()
       // setupViewPager()
//        loadLost()
        //testloading()

        var layoutManager = LinearLayoutManager(this@Home2Activity)
        recycler_view_viewpager.layoutManager = layoutManager
        adpater = SharesAdapter(ListShares)
        recycler_view_viewpager.adapter = adpater
    }

    private fun initImageLoader() {
        val universalImageLoader = UniversalImageLoader(mContext)
        ImageLoader.getInstance().init(universalImageLoader.config)
    }
    private fun testloading(){
        var sharesImageURL = "https://i.redd.it/59kjlxxf720z.jpg"
        ListShares.add(SharesModel("1","waw good photo  ", sharesImageURL, "12/15/2001", "hamza", "http://i.imgur.com/j4AfH6P.jpg", "2"))
        ListShares.add(SharesModel("1","waw good photo ", sharesImageURL, "12/15/2001", "hamza", "http://i.imgur.com/j4AfH6P.jpg", "2"))
        ListShares.add(SharesModel("1","waw good photo ", sharesImageURL, "12/15/2001", "hamza", "http://i.imgur.com/j4AfH6P.jpg", "2"))
        ListShares.add(SharesModel("1","waw good photo , awesome sss good ", sharesImageURL, "12/15/2001", "hamza", "http://i.imgur.com/j4AfH6P.jpg", "2"))
        ListShares.add(SharesModel("1","waw good photo ", sharesImageURL, "12/15/2001", "hamza", "http://i.imgur.com/j4AfH6P.jpg", "2"))
        ListShares.add(SharesModel("1","waw good photo ", sharesImageURL, "12/15/2001", "hamza", "http://i.imgur.com/j4AfH6P.jpg", "2"))
        ListShares.add(SharesModel("1","waw good photo ", sharesImageURL, "12/15/2001", "hamza", "http://i.imgur.com/j4AfH6P.jpg", "2"))
        ListShares.add(SharesModel("1","waw good photo ", sharesImageURL, "12/15/2001", "hamza", "http://i.imgur.com/j4AfH6P.jpg", "2"))
        ListShares.add(SharesModel("1","waw good photo ", sharesImageURL, "12/15/2001", "hamza", "http://i.imgur.com/j4AfH6P.jpg", "2"))

    }



    inner class MyAsyncTask: AsyncTask<String, String, String>() {

       // lateinit var progressDialog: ProgressDialog
        override fun onPreExecute() {
            //Before task started
        //    progressDialog = ProgressDialog(mContext)
        //    progressDialog.setMessage("Uploading Data ...")
         //   progressDialog.setCancelable(false)
         //   progressDialog.show()
        }
        override fun doInBackground(vararg p0: String?): String {
            try {

                val url= URL(p0[0])

                val urlConnect=url.openConnection() as HttpURLConnection
                urlConnect.connectTimeout=7000

                val op= Operations()

                var inString= op.ConvertStreamToString(urlConnect.inputStream)
                //Cannot access to ui
                publishProgress(inString)
            }catch (ex:Exception){}


            return " "

        }

        override fun onProgressUpdate(vararg values: String?) {
            try{
                var json= JSONObject(values[0])
                Toast.makeText(applicationContext,json.getString("msg"),Toast.LENGTH_LONG).show()


               if ( json.getString("msg")=="has tweet"){
                  //  ListTweets.clear()
                  //  ListTweets.add(Ticket("0","him","url","add","","",""))


                    // get tweets
                    val tweets = JSONArray(json.getString("info"))
                    for (i in 0..tweets.length()-1){
                        val singleTweet= tweets.getJSONObject(i)
                        ListShares.add(SharesModel(singleTweet.getString("tweet_id"),singleTweet.getString("tweet_text"),
                                singleTweet.getString("tweet_picture"),singleTweet.getString("tweet_date")
                                ,singleTweet.getString("first_name"),singleTweet.getString("picture_path"),
                                singleTweet.getString("user_id")))

                    }
                }else if ( json.getString("msg")=="no tweets"){
//                    ListTweets.clear()
//                    ListTweets.add(Ticket("0","him","url","add","","",""))

                }



                adpater!!.notifyDataSetChanged()

            }catch (ex:Exception){}
        }

        override fun onPostExecute(result: String?) {
          //  progressDialog.dismiss()
            //after task done
        }


    }


    private fun loadLost(userid:String) {
        val url="http://192.168.1.64/IshareServer/TweetList.php?op=2&user_id=1&StartFrom=0"


        val jsonObjReq = object : JsonObjectRequest(Method.POST,
                url, null, Response.Listener { response ->
            try {
                Toast.makeText(applicationContext,response.toString(),Toast.LENGTH_LONG).show()
                Toast.makeText(applicationContext,response.getString("msg"),Toast.LENGTH_LONG).show()
                if ( response.getString("msg")=="has tweet"){

                    val tweets = JSONArray(response.getString("info"))
                    for (i in 0..tweets.length()-1){
                        val singleTweet= tweets.getJSONObject(i)
                        ListShares.add(SharesModel(singleTweet.getString("tweet_id"),singleTweet.getString("tweet_text"),
                                singleTweet.getString("tweet_picture"),singleTweet.getString("tweet_date")
                                ,singleTweet.getString("first_name"),singleTweet.getString("picture_path"),
                                singleTweet.getString("user_id")))
                        adpater!!.notifyDataSetChanged()

                    }
                }

            } catch (e: JSONException) {
                e.printStackTrace()
            }
        }, Response.ErrorListener { }) {
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

    /**
     * Responsible for adding the 3 tabs: Camera, Home, Messages
     */
//    private fun setupViewPager() {
//        val adapter = SectionsPagerAdapter(supportFragmentManager)
//        adapter.addFragment(CameraFragment()) //index 0
//        adapter.addFragment(HomeFragment()) //index 1
//        adapter.addFragment(MessagesFragment()) //index 2
//        val viewPager = findViewById<View>(R.id.container) as ViewPager
//        viewPager.adapter = adapter
//
//        val tabLayout = findViewById<View>(R.id.tabs) as TabLayout
//        tabLayout.setupWithViewPager(viewPager)
//
//        tabLayout.getTabAt(0)!!.setIcon(R.drawable.ic_camera)
//        tabLayout.getTabAt(1)!!.setIcon(R.drawable.ishare_logo)
//        tabLayout.getTabAt(2)!!.setIcon(R.drawable.ic_arrow)
//    }

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
}
