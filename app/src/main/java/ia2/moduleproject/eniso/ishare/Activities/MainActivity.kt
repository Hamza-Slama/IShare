package ia2.moduleproject.eniso.ishare.Activities

import android.app.Activity
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
import java.io.IOException


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        imageViewPerso.setOnClickListener {
            checkPermission()
        }
    }

    val READIMAGE:Int=253

    //checkPermission is a Function that check if we can acess to the storage or not
    // if yes then run the loadImage function
    fun checkPermission(){

        if(Build.VERSION.SDK_INT>=23){
            if(ActivityCompat.checkSelfPermission(this,
                    android.Manifest.permission.READ_EXTERNAL_STORAGE)!=
                    PackageManager.PERMISSION_GRANTED){
                //call a function onRequestPermissionResult
                requestPermissions(arrayOf( android.Manifest.permission.READ_EXTERNAL_STORAGE),READIMAGE)
                return
            }
        }
        loadImage()
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        when (requestCode){
            READIMAGE ->{
                if(grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    loadImage()
                }else {
                    Toast.makeText(this,"Cannot acess your image", Toast.LENGTH_LONG).show()
                }
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    //loadImage run if the ckeckPermission == true and put into it the pick code image
    val Pick_code_image = 123

    //there is many way but the simple way is to use intent
    fun loadImage() {
        var intent = Intent (Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        startActivityForResult(intent,Pick_code_image)
    }

    // After the StartActivityForResult this function is Done
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == Pick_code_image && resultCode == Activity.RESULT_OK && data!=null){
            val selectedImage = data.data
            val filePathColum = arrayOf(MediaStore.Images.Media.DATA)
            val cursor =contentResolver.query(selectedImage,filePathColum,null,null,null)
            cursor.moveToFirst()
            val coulomIndex =cursor.getColumnIndex(filePathColum[0])
            val picturePath = cursor.getString(coulomIndex)
            cursor.close()
            imageViewPerso.setImageBitmap(BitmapFactory.decodeFile(picturePath))
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



}
