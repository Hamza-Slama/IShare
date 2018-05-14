package ia2.moduleproject.eniso.ishare.Utils

import android.content.Context
import android.content.Intent
import android.support.design.widget.BottomNavigationView
import android.util.Log
import android.view.MenuItem

import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx

import ia2.moduleproject.eniso.ishare.Activities.Home2Activity
import ia2.moduleproject.eniso.ishare.Activities.HomeActivity
import ia2.moduleproject.eniso.ishare.Activities.LikesActivity
import ia2.moduleproject.eniso.ishare.Activities.ProfileActivity
import ia2.moduleproject.eniso.ishare.Activities.SearchActivity
import ia2.moduleproject.eniso.ishare.Activities.ShareActivity

//import tabian.com.instagramclone.HomeActivity;
//import tabian.com.instagramclone.LikesActivity;
//import tabian.com.instagramclone.ProfileActivity;
//import tabian.com.instagramclone.R;
//import tabian.com.instagramclone.SearchActivity;
//import tabian.com.instagramclone.ShareActivity;

import ia2.moduleproject.eniso.ishare.R;
object BottomNavigationViewHelper {

    private val TAG = "BottomNavigationViewHel"

    fun setupBottomNavigationView(bottomNavigationViewEx: BottomNavigationViewEx) {
        Log.d(TAG, "setupBottomNavigationView: Setting up BottomNavigationView")
        bottomNavigationViewEx.enableAnimation(false)
        bottomNavigationViewEx.enableItemShiftingMode(false)
        bottomNavigationViewEx.enableShiftingMode(false)
        bottomNavigationViewEx.setTextVisibility(false)
    }

    fun enableNavigation(context: Context, view: BottomNavigationViewEx) {
        view.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {

                R.id.ic_house -> {
                    val intent1 = Intent(context, Home2Activity::class.java)//ACTIVITY_NUM = 0
                    context.startActivity(intent1)
                }



//                R.id.ic_circle -> {
//                    val intent3 = Intent(context, ShareActivity::class.java)//ACTIVITY_NUM = 2
//                    context.startActivity(intent3)
//                }

                R.id.ic_alert -> {
                    val intent4 = Intent(context, LikesActivity::class.java)//ACTIVITY_NUM = 1
                    context.startActivity(intent4)
                }

                R.id.ic_search -> {
                    val intent2 = Intent(context, SearchActivity::class.java)//ACTIVITY_NUM = 2
                    context.startActivity(intent2)
                }

                R.id.ic_android -> {
                    val intent5 = Intent(context, ProfileActivity::class.java)//ACTIVITY_NUM = 3
                    context.startActivity(intent5)
                }
            }


            false
        }
    }
}