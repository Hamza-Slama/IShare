package ia2.moduleproject.eniso.ishare.Activities

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import ia2.moduleproject.eniso.ishare.R

import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.BitmapDrawable
import android.os.AsyncTask
import android.os.Build
import android.provider.MediaStore
import android.support.v4.app.ActivityCompat
import android.util.Log
import android.view.View
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.storage.FirebaseStorage
import ia2.moduleproject.eniso.ishare.Utils.Operations
import kotlinx.android.synthetic.main.activity_register.*

import org.json.JSONObject
import java.io.BufferedReader
import java.io.ByteArrayOutputStream
import java.io.InputStream
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL
import java.net.URLEncoder
import java.text.SimpleDateFormat
import java.util.*


class RegisterActivity : AppCompatActivity() {

    var mAuth:FirebaseAuth?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        mAuth= FirebaseAuth.getInstance()
        signInAnonymously()

        ivUserImage.setOnClickListener {

            checkPermission()
        }
    }


    fun buRegisterEvent(view:View){
        btn_register.isEnabled=false
        SaveImageInFirebase()
    }


    fun signInAnonymously(){

        mAuth!!.signInAnonymously().addOnCompleteListener(this,{ task->

            Log.d("LoginInfo:",task.isSuccessful.toString())
        })
    }


    val READIMAGE:Int=253
    fun checkPermission(){

        if(Build.VERSION.SDK_INT>=23){
            if(ActivityCompat.checkSelfPermission(this,
                            android.Manifest.permission.READ_EXTERNAL_STORAGE)!=
                    PackageManager.PERMISSION_GRANTED){

                requestPermissions(arrayOf( android.Manifest.permission.READ_EXTERNAL_STORAGE),READIMAGE)
                return
            }
        }

        loadImage()
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {

        when(requestCode){
            READIMAGE->{
                if(grantResults[0]==PackageManager.PERMISSION_GRANTED){
                    loadImage()
                }else{
                    Toast.makeText(applicationContext,"Cannot access your images",Toast.LENGTH_LONG).show()
                }
            }
            else-> super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        }


    }

    val PICK_IMAGE_CODE=123
    fun loadImage(){

        var intent=Intent(Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
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
            ivUserImage.setImageBitmap(BitmapFactory.decodeFile(picturePath))
        }

    }

    fun SaveImageInFirebase(){
        var currentUser =mAuth!!.currentUser
        val email:String=currentUser!!.email.toString()
        val storage=FirebaseStorage.getInstance()
        val storgaRef=storage.getReferenceFromUrl("gs://ishare-5b609.appspot.com")
        val df=SimpleDateFormat("ddMMyyHHmmss")
        val dataobj=Date()
        val imagePath= SplitString(email) + "."+ df.format(dataobj)+ ".jpg"
        val ImageRef=storgaRef.child("images/"+imagePath )
        ivUserImage.isDrawingCacheEnabled=true
        ivUserImage.buildDrawingCache()

        val drawable=ivUserImage.drawable as BitmapDrawable
        val bitmap=drawable.bitmap
        val baos=ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG,100,baos)
        val data= baos.toByteArray()
        val uploadTask=ImageRef.putBytes(data)
        uploadTask.addOnFailureListener{
            Toast.makeText(applicationContext,"fail to upload",Toast.LENGTH_LONG).show()
        }.addOnSuccessListener { taskSnapshot ->

            var DownloadURL= taskSnapshot.downloadUrl!!.toString()

            // TODO: register to datavase

            Log.d("DownloadURL:",DownloadURL)

            val name =URLEncoder.encode(input_username.text.toString(),"utf-8")
            DownloadURL=URLEncoder.encode(DownloadURL,"utf-8")
            val url="http://192.168.1.64/IshareServer/Register.php/Register.php?first_name="+ name + "&email="+ input_email.text.toString() +  "&password="+ input_password.text.toString() +"&picture_path="+ DownloadURL
            MyAsyncTask().execute(url)
        }

    }

    fun SplitString(email:String):String{
        val split= email.split("@")
        return split[0]
    }


    // CALL HTTP
    inner class MyAsyncTask: AsyncTask<String, String, String>() {

        override fun onPreExecute() {
            //Before task started
        }
        override fun doInBackground(vararg p0: String?): String {
            try {

                val url=URL(p0[0])

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
                var json=JSONObject(values[0])
                Toast.makeText(applicationContext,json.getString("msg"),Toast.LENGTH_LONG).show()

                if (json.getString("msg")== "user is added"){
                    finish()
                }else{
                    btn_register.isEnabled=true
                }

            }catch (ex:Exception){}
        }

        override fun onPostExecute(result: String?) {

            //after task done
        }


    }


// Progress Dialog



}