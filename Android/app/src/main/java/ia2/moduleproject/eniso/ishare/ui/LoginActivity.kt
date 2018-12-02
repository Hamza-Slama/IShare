package ia2.moduleproject.eniso.ishare.ui

import android.app.Activity
import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.BitmapFactory
import android.os.Build
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.support.v4.app.ActivityCompat
import android.widget.Toast
import ia2.moduleproject.eniso.ishare.R
import android.graphics.Bitmap
import android.util.Base64
import com.nostra13.universalimageloader.core.ImageLoader
import ia2.moduleproject.eniso.ishare.utils.MyUserLoginAndPassword
import ia2.moduleproject.eniso.ishare.utils.UniversalImageLoader
import java.io.ByteArrayOutputStream


import android.os.AsyncTask
import ia2.moduleproject.eniso.ishare.utils.SaveSettings
import ia2.moduleproject.eniso.ishare.utils.localhost
import org.json.JSONArray
import org.json.JSONObject
import java.net.HttpURLConnection
import java.net.URL

import java.io.BufferedReader
import java.io.InputStream
import java.io.InputStreamReader

class LoginActivity : AppCompatActivity() {
    private val Pick_code_image = 123
    private val READIMAGE: Int = 253
    val activity= this
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        getLoginUser()
        initImageLoader()
        imageViewPerso.setOnClickListener { checkPermission() }

        loginBtn.setOnClickListener {
//            RememberMe()
//            Intent(this, HomeActivity::class.java).apply {
//                startActivity(this)
//            }

         //   val url = "http://192.168.1.64/IshareServer/Login.php?email=c@yahoo.com&password=1234567"
            val url= localhost +"/IshareServer/Login.php?email=" + user.text.toString() +"&password="+ pass.text.toString()
            MyAsyncTask().execute(url)
        }

        link_signup.setOnClickListener {
            val intent = Intent(this,RegisterActivity::class.java)
            startActivity(intent)
        }
    }


    private fun initImageLoader() {
        val universalImageLoader = UniversalImageLoader(this)
        ImageLoader.getInstance().init(universalImageLoader.config)
    }

    //checkPermission is a Function that check if we can acess to the storage or not
    // if yes then run the loadImage function
    fun checkPermission() {

        if (Build.VERSION.SDK_INT >= 23) {
            if (ActivityCompat.checkSelfPermission(this,
                    android.Manifest.permission.READ_EXTERNAL_STORAGE) !=
                    PackageManager.PERMISSION_GRANTED) {
                //call a function onRequestPermissionResult
                requestPermissions(arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE), READIMAGE)
                return
            }
        }
        loadImage()
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        when (requestCode) {
            READIMAGE -> {
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    loadImage()
                } else {
                    Toast.makeText(this, "Cannot acess your image", Toast.LENGTH_LONG).show()
                }
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    //loadImage run if the ckeckPermission == true and put into it the pick code image


    //there is many way but the simple way is to use intent
    fun loadImage() {
        var intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        startActivityForResult(intent, Pick_code_image)
    }

    // After the StartActivityForResult this function is Done
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == Pick_code_image && resultCode == Activity.RESULT_OK && data != null) {
            val selectedImage = data.data
            val filePathColum = arrayOf(MediaStore.Images.Media.DATA)
            val cursor = contentResolver.query(selectedImage, filePathColum, null, null, null)
            cursor.moveToFirst()
            val coulomIndex = cursor.getColumnIndex(filePathColum[0])
            val picturePath = cursor.getString(coulomIndex)
            cursor.close()
            //UniversalImageLoader.setImage(BitMapToString(BitmapFactory.decodeFile(picturePath)), imageViewPerso!!, null, "")
           imageViewPerso.setImageBitmap(BitmapFactory.decodeFile(picturePath))
        }
    }


    fun saveLoginUser() {
        val infofile = getSharedPreferences(MyUserLoginAndPassword, Context.MODE_PRIVATE)
        val editor = infofile.edit()
        editor.putString("username", user!!.text.toString())
        editor.putString("pass", pass!!.text.toString())
        editor.apply()

    }

    fun getLoginUser() {

        val infofile = getSharedPreferences(MyUserLoginAndPassword, Context.MODE_PRIVATE)
        val name = infofile.getString("username", "")
        val paswword = infofile.getString("pass", "")
        user!!.setText(name)
        pass!!.setText(paswword)
    }

    fun RememberMe (){
        if (remember_me!!.isChecked == true) {
            saveLoginUser()
            getLoginUser()
        }
    }

    fun BitMapToString(bitmap: Bitmap): String {
        val baos = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos)
        val b = baos.toByteArray()
        return Base64.encodeToString(b, Base64.DEFAULT)
    }




inner class MyAsyncTask: AsyncTask<String, String, String>() {
    lateinit var progressDialog: ProgressDialog
    override fun onPreExecute() {
        //Before task started
        progressDialog = ProgressDialog(activity)
        progressDialog.setMessage("Downloading Data ...")
        progressDialog.setCancelable(false)
        progressDialog.show()
    }
    override fun doInBackground(vararg p0: String?): String {
        try {

            val url= URL(p0[0])

            val urlConnect=url.openConnection() as HttpURLConnection
            urlConnect.connectTimeout=7000
            val op=Operations()
            var inString= op.ConvertStreamToString(urlConnect.inputStream)
            //Cannot access to ui
            publishProgress(inString)
        }catch (ex:Exception){}


        return " "

    }

    override fun onProgressUpdate(vararg values: String?) {
        try{
            var json= JSONObject(values[0])


            if (json.getString("msg")== "pass login"){
                val userInfo =JSONArray(json.getString("info"))
                val userCredentails= userInfo.getJSONObject(0)

                Toast.makeText(applicationContext,"Hello "+userCredentails.getString("first_name"), Toast.LENGTH_LONG).show()
                val picture_path = userCredentails.getString("picture_path")
                val user_id= userCredentails.getString("user_id")
                val saveSettings= SaveSettings(applicationContext)

                saveSettings.saveSettingsName(userCredentails.getString("first_name"))
                saveSettings.saveSettingsPicture_path(picture_path)
                saveSettings.saveSettings(user_id)

                val intent = Intent(activity,Home2Activity::class.java)
                startActivity(intent)

                finish()
            }else{
                Toast.makeText(applicationContext,json.getString("msg"), Toast.LENGTH_LONG).show()
            }

        }catch (ex:Exception){}
    }

    override fun onPostExecute(result: String?) {
        progressDialog.dismiss()
        //after task done
    }


}

inner  class Operations{

    fun ConvertStreamToString(inputStream: InputStream):String{

        val bufferReader= BufferedReader(InputStreamReader(inputStream))
        var line:String
        var AllString:String=""

        try {
            do{
                line=bufferReader.readLine()
                if(line!=null){
                    AllString+=line
                }
            }while (line!=null)
            inputStream.close()
        }catch (ex:Exception){}



        return AllString
    }

}
}
/*  override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
         super.onActivityResult(requestCode, resultCode, data)

         if (requestCode == Pick_code_image && resultCode == Activity.RESULT_OK && null != data) {
             val selectedImage = data.data

             var bitmap: Bitmap? = null

             try {
                 bitmap = MediaStore.Images.Media.getBitmap(this.contentResolver, selectedImage)
             } catch (e: IOException) {
                 e.printStackTrace()
             }
             imageViewPerso.setImageBitmap(bitmap)
         }

     }*/