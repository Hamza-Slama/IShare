package ia2.moduleproject.eniso.ishare.Activities

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import ia2.moduleproject.eniso.ishare.R
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import com.android.volley.*
import com.android.volley.toolbox.HttpHeaderParser
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import ia2.moduleproject.eniso.ishare.Adapter.LostAdapter
import ia2.moduleproject.eniso.ishare.Model.Lost
import ia2.moduleproject.eniso.ishare.Utils.SaveSettings
import kotlinx.android.synthetic.main.activity_home.*
import org.json.JSONException
import org.json.JSONObject
import java.io.UnsupportedEncodingException

class HomeActivity : AppCompatActivity() {


    var costomAdapter: LostAdapter?=null
    private val lostList = ArrayList<Lost>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val saveSettings= SaveSettings(this)
        saveSettings.loadSettings()

        setContentView(R.layout.activity_home)
        loadLost()

//        var layoutManager = GridLayoutManager(this@HomeActivity,3)
        var layoutManager = LinearLayoutManager(this@HomeActivity)
        recycler_view.layoutManager = layoutManager
        costomAdapter = LostAdapter(lostList)
        recycler_view.adapter = costomAdapter
    }


    private fun loadLost() {
        val url2 = "http://api.tvmaze.com/singlesearch/shows?q=lost&embed=episodes"


        val jsonObjReq = object : JsonObjectRequest(Method.GET,
                url2, null, Response.Listener { response ->
            try {
                println("------------------------------------------------------------------- response = "+response)
                val emedded = response.getJSONObject("_embedded")
                val episodes = emedded.getJSONArray("episodes")
                for (i in 0 until episodes.length()) {

                    val episode = episodes.getJSONObject(i)
                    val name = episode.getString("name")
                    Log.i("Provera Object", name)

                    val image = episode.getJSONObject("image")
                    val imageMedium = image.getString("medium")
                    Log.i("Provera Object", imageMedium.toString())

                    val id = episode.getInt("id")
                    Log.i("Provera Object", id.toString())

                    val url = episode.getString("url")
                    Log.i("Provera Object", url)

                    val season = episode.getInt("season")
                    Log.i("Provera Object", season.toString())

                    val lostItem = Lost(name, imageMedium, id)
                    lostList.add(lostItem)
                    costomAdapter!!.notifyDataSetChanged()


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
}
