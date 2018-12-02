package ia2.moduleproject.eniso.ishare.ui

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import ia2.moduleproject.eniso.ishare.R

class SpalshScreen : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_spalsh_screen)
        Handler().postDelayed({
//            Intent(this, HomeActivity::class.java).apply {
//                setFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
//                //addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
//                startActivity(this)
            Intent(this,HomeActivity::class.java).apply {
                setFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
                //addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                startActivity(this)

            }
        }, 3000)
    }
}
