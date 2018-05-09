package ia2.moduleproject.eniso.ishare.Utils

/**
 * Created by hamza on 27/04/18.
 */
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import ia2.moduleproject.eniso.ishare.Activities.LoginActivity


class SaveSettings{
    var context:Context?=null
    var sharedRef:SharedPreferences?=null
    constructor(context:Context){
        this.context=context
        sharedRef=context.getSharedPreferences("myRef",Context.MODE_PRIVATE)
    }

    fun saveSettings(userID:String){
        val editor=sharedRef!!.edit()
        editor.putString("userID",userID)
        editor.commit()
        loadSettings()
    }

    fun saveSettingsName(name : String){
        val editor = sharedRef!!.edit()
        editor.putString("name",name)
        editor.commit()
    }

    fun loadSettings(){
        userID= sharedRef!!.getString("userID","0")

        if (userID=="0"){
            val intent=Intent(context,LoginActivity::class.java)
           // intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
            context!!.startActivity(intent)
        }
    }


    companion object {
        var userID=""
        var userName=""
    }
}