package ia2.moduleproject.eniso.ishare.ui

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import ia2.moduleproject.eniso.ishare.R
import android.widget.ArrayAdapter


import android.view.View

import android.widget.ImageView
import android.widget.ListView

import java.util.ArrayList
import android.widget.AdapterView
import android.widget.RelativeLayout
import android.support.v4.view.ViewPager
import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx
import ia2.moduleproject.eniso.ishare.ui.fragment.EditProfileFragment
import ia2.moduleproject.eniso.ishare.ui.fragment.SignOutFragment
import ia2.moduleproject.eniso.ishare.utils.BottomNavigationViewHelper
import ia2.moduleproject.eniso.ishare.utils.SectionsStatePagerAdapter


class AccountSettings : AppCompatActivity() {

    private var mContext = this@AccountSettings
    private val ACTIVITY_NUM = 0
    private var pagerAdapter: SectionsStatePagerAdapter? = null
    private var mViewPager: ViewPager? = null
    private var mRelativeLayout: RelativeLayout? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_account_settings)

        Log.d(TAG, "onCreate: started.")
        mViewPager = findViewById<View>(R.id.container) as? ViewPager
        mRelativeLayout = findViewById<View>(R.id.relLayout1) as RelativeLayout
        setupBottomNavigationView()
        setupSettingsList()
        setupFragments()

        //setup the backarrow for navigating back to "ProfileActivity"
        val backArrow = findViewById<View>(R.id.backArrow) as ImageView
        backArrow.setOnClickListener {
            Log.d(TAG, "onClick: navigating back to 'ProfileActivity'")
            finish()
        }
    }

    private fun setupFragments() {
        pagerAdapter = SectionsStatePagerAdapter(supportFragmentManager)
        pagerAdapter!!.addFragment(EditProfileFragment(), getString(R.string.edit_profile_fragment)) //fragment 0
        pagerAdapter!!.addFragment(SignOutFragment(), getString(R.string.sign_out_fragment)) //fragment 1
    }

    private fun setViewPager(fragmentNumber: Int) {
        mRelativeLayout!!.visibility = View.GONE
        Log.d(TAG, "setViewPager: navigating to fragment #: $fragmentNumber")
        mViewPager!!.adapter = pagerAdapter
        mViewPager!!.currentItem = fragmentNumber
    }

    private fun setupSettingsList() {
        Log.d(TAG, "setupSettingsList: initializing 'Account Settings' list.")
        val listView = findViewById<View>(R.id.lvAccountSettings) as ListView

        val options = ArrayList<String>()
        options.add(getString(R.string.edit_profile_fragment)) //fragment 0
        options.add(getString(R.string.sign_out_fragment)) //fragement 1

        val adapter = ArrayAdapter(mContext!!, android.R.layout.simple_list_item_1, options)
        listView.adapter = adapter

        listView.onItemClickListener = AdapterView.OnItemClickListener { parent, view, position, id ->
            Log.d(TAG, "onItemClick: navigating to fragment#: $position")
            setViewPager(position)
        }

    }
    private fun setupBottomNavigationView() {
        Log.d(TAG, "setupBottomNavigationView: setting up BottomNavigationView")
        val bottomNavigationViewEx = findViewById<View>(R.id.bottomNavViewBar) as BottomNavigationViewEx
        BottomNavigationViewHelper.setupBottomNavigationView(bottomNavigationViewEx)
        BottomNavigationViewHelper.enableNavigation(mContext, bottomNavigationViewEx)
        val menu = bottomNavigationViewEx.menu
        val menuItem = menu.getItem(ACTIVITY_NUM)
        menuItem.isChecked = true
    }
    companion object {

        private val TAG = "AccountSettingsActivity"
    }
}