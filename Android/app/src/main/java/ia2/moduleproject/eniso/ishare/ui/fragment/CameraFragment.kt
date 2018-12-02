package ia2.moduleproject.eniso.ishare.ui.fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import ia2.moduleproject.eniso.ishare.R


class CameraFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        return inflater.inflate(R.layout.fragment_camera, container, false)
    }

    companion object {
        private val TAG = "CameraFragment"
    }
}