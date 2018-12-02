package ia2.moduleproject.eniso.ishare.ui

import android.app.ProgressDialog
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.AsyncTask
import android.os.Bundle
import android.provider.MediaStore
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import android.widget.Toast
import com.google.firebase.storage.FirebaseStorage
import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx
import ia2.moduleproject.eniso.ishare.R
import ia2.moduleproject.eniso.ishare.utils.BottomNavigationViewHelper
import ia2.moduleproject.eniso.ishare.utils.Operations
import ia2.moduleproject.eniso.ishare.utils.SaveSettings
import ia2.moduleproject.eniso.ishare.utils.localhost
import org.json.JSONObject
import java.net.URLEncoder
import java.text.SimpleDateFormat
import java.util.*
import java.io.ByteArrayOutputStream
import java.net.HttpURLConnection
import java.net.URL

class LikesActivity : AppCompatActivity() {
    private val TAG = "LikesActivity"
    private val ACTIVITY_NUM = 1
    private var DownloadURL="noImage"
    private val mContext = this@LikesActivity

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.add_ticket)
        Log.d(TAG, "onCreate: starting.")
        iv_attach.setOnClickListener {
            loadImage()
        }

        iv_post.setOnClickListener {
        val postText = URLEncoder.encode(etPost.text.toString(),"utf-8")
            val url= localhost +"/IshareServer/TweetAdd.php?user_id=" + SaveSettings.userID  +"&tweet_text=" + postText +"&tweet_picture="+ DownloadURL
        MyAsyncTask().execute(url)
        }
       setupBottomNavigationView()
    }



    val PICK_IMAGE_CODE=123
    fun loadImage(){

        var intent= Intent(Intent.ACTION_PICK,
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        startActivityForResult(intent,PICK_IMAGE_CODE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if(requestCode==PICK_IMAGE_CODE  && data!=null && resultCode == RESULT_OK){

            val selectedImage=data.data
            val filePathColum= arrayOf(MediaStore.Images.Media.DATA)
            val cursor= contentResolver.query(selectedImage,filePathColum,null,null,null)
            cursor.moveToFirst()
            val coulomIndex=cursor.getColumnIndex(filePathColum[0])
            val picturePath=cursor.getString(coulomIndex)
            cursor.close()
            UploadImage(BitmapFactory.decodeFile(picturePath))
        }

    }


    fun UploadImage(bitmap: Bitmap){
       // ListTweets.add(0,Ticket("0","him","url","loading","","",""))
        //adpater!!.notifyDataSetChanged()


        lateinit var progressDialog: ProgressDialog
        progressDialog = ProgressDialog(mContext)
        progressDialog.setMessage("Uploading Photo ...")
        progressDialog.setCancelable(false)
        progressDialog.show()


        val storage= FirebaseStorage.getInstance()
        val storgaRef=storage.getReferenceFromUrl("gs://ishare-5b609.appspot.com")
        val df= SimpleDateFormat("ddMMyyHHmmss")
        val dataobj= Date()
        val imagePath= SaveSettings.userID + "."+ df.format(dataobj)+ ".jpg"
        val ImageRef=storgaRef.child("imagePost/"+imagePath )
        val baos= ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG,100,baos)
        val data= baos.toByteArray()
        val uploadTask=ImageRef.putBytes(data)
        uploadTask.addOnFailureListener{
            Toast.makeText(mContext,"fail to upload", Toast.LENGTH_LONG).show()
            progressDialog.dismiss()
        }.addOnSuccessListener { taskSnapshot ->

            DownloadURL= taskSnapshot.downloadUrl!!.toString()
            DownloadURL= URLEncoder.encode(DownloadURL,"utf-8")
            Toast.makeText(mContext,"Photo Aded",Toast.LENGTH_SHORT).show()
            progressDialog.dismiss()
          //  ListTweets.removeAt(0)
            //adpater!!.notifyDataSetChanged()

        }
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



    inner class MyAsyncTask: AsyncTask<String, String, String>() {

        lateinit var progressDialog: ProgressDialog
        override fun onPreExecute() {
            //Before task started
            progressDialog = ProgressDialog(mContext)
            progressDialog.setMessage("Uploading Data ...")
            progressDialog.setCancelable(false)
            progressDialog.show()
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



                if (json.getString("msg")== "tweet is added"){
                   // DownloadURL="noImage"
                    val intent=Intent(mContext,Home2Activity::class.java)
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                    mContext!!.startActivity(intent)

                  //  ListTweets.removeAt(0)
                  //  adpater!!.notifyDataSetChanged()
//                }else if ( json.getString("msg")=="has tweet"){
//                  //  ListTweets.clear()
//                  //  ListTweets.add(Ticket("0","him","url","add","","",""))
//
//
//                    // get tweets
//                    val tweets = JSONArray(json.getString("info"))
//                    for (i in 0..tweets.length()-1){
//                        val singleTweet= tweets.getJSONObject(i)
//                        ListTweets.add(Ticket(singleTweet.getString("tweet_id"),singleTweet.getString("tweet_text"),
//                                singleTweet.getString("tweet_picture"),singleTweet.getString("tweet_date")
//                                ,singleTweet.getString("first_name"),singleTweet.getString("picture_path"),
//                                singleTweet.getString("user_id")))
//
//                    }
//                }else if ( json.getString("msg")=="no tweets"){
////                    ListTweets.clear()
////                    ListTweets.add(Ticket("0","him","url","add","","",""))

                }



//                adpater!!.notifyDataSetChanged()

            }catch (ex:Exception){}
        }

            override fun onPostExecute(result: String?) {
                progressDialog.dismiss()
                //after task done
            }


    }
}