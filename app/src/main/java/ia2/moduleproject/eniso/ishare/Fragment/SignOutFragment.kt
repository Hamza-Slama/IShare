package ia2.moduleproject.eniso.ishare.Fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import ia2.moduleproject.eniso.ishare.R


/**
 * Created by User on 6/4/2017.
 */

class SignOutFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        return inflater.inflate(R.layout.fragment_signout, container, false)
    }

    companion object {

        private val TAG = "SignOutFragment"
    }
}