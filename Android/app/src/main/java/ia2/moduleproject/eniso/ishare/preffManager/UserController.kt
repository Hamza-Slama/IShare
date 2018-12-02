package ia2.moduleproject.eniso.ishare.preffManager

import android.content.Context
import android.content.Intent
import ia2.moduleproject.eniso.ishare.ui.LoginActivity
import ia2.moduleproject.eniso.ishare.utils.SaveSettings

class UserController {

    companion object {
        fun saveSettings(userID: String) {
            val editor = SharedPrefsManager.sharedPreferences!!.edit()
            editor.putString("userID", userID)
            editor.commit()

        }

        fun saveSettingsName(name: String) {
            val editor = SharedPrefsManager.sharedPreferences!!.edit()
            editor.putString("name", name)
            editor.commit()
        }

        fun saveSettingsPicture_path(picturepath: String) {
            val editor = SharedPrefsManager.sharedPreferences!!.edit()
            editor.putString("picture_path", picturepath)
            editor.commit()
        }

        fun loadSettings(context: Context) {
            var userID = SharedPrefsManager.sharedPreferences!!.getString("userID", "0")
            if (userID == "0") {
                val intent = Intent(context, LoginActivity::class.java)
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
                context!!.startActivity(intent)
            }
        }
    }
}