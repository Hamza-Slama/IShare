package ia2.moduleproject.eniso.ishare.Activities

import android.app.Activity
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
import kotlinx.android.synthetic.main.activity_main.*
import android.graphics.Bitmap
import ia2.moduleproject.eniso.ishare.Constants.MyUserLoginAndPassword
import java.io.IOException


class LoginActivity : AppCompatActivity() {
    private val Pick_code_image = 123
    private val READIMAGE: Int = 253
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        getLoginUser()

        imageViewPerso.setOnClickListener { checkPermission() }

        loginBtn.setOnClickListener {
            RememberMe()
            Intent(this, HomeActivity::class.java).apply {
                startActivity(this)
            }
        }
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