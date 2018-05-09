package ia2.moduleproject.eniso.ishare.Fragment

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import ia2.moduleproject.eniso.ishare.Activities.LoginActivity
import android.widget.Toast
import ia2.moduleproject.eniso.ishare.Activities.SpalshScreen
import ia2.moduleproject.eniso.ishare.R



class SignOutFragment : Fragment() {


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_signout, container, false)

        val btnConfirmSignout = view.findViewById<View>(R.id.btnConfirmSignout) as Button
        btnConfirmSignout.setOnClickListener {
            val intent= Intent(activity!!.applicationContext, LoginActivity::class.java)
//            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
            activity!!.applicationContext!!.startActivity(intent)
            Toast.makeText(activity!!.applicationContext,"Sign Out ",Toast.LENGTH_LONG).show()
        }
        return view
    }

    companion object {

        private val TAG = "SignOutFragment"
    }
}