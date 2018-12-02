package ia2.moduleproject.eniso.ishare.utils

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter

import java.util.ArrayList
import java.util.HashMap


class SectionsStatePagerAdapter(fm: FragmentManager) : FragmentStatePagerAdapter(fm) {

    private val mFragmentList = ArrayList<Fragment>()
    private val mFragments = HashMap<Fragment, Int>()
    private val mFragmentNumbers = HashMap<String, Int>()
    private val mFragmentNames = HashMap<Int, String>()

    override fun getItem(position: Int): Fragment {
        return mFragmentList[position]
    }

    override fun getCount(): Int {
        return mFragmentList.size
    }

    fun addFragment(fragment: Fragment, fragmentName: String) {
        mFragmentList.add(fragment)
        mFragments[fragment] = mFragmentList.size - 1
        mFragmentNumbers[fragmentName] = mFragmentList.size - 1
        mFragmentNames[mFragmentList.size - 1] = fragmentName
    }

    /**
     * returns the fragment with the name @param
     * @param fragmentName
     * @return
     */
    fun getFragmentNumber(fragmentName: String): Int? {
        return if (mFragmentNumbers.containsKey(fragmentName)) {
            mFragmentNumbers[fragmentName]
        } else {
            null
        }
    }


    /**
     * returns the fragment with the name @param
     * @param fragment
     * @return
     */
//    fun getFragmentNumber(fragment: Fragment): Int? {
//        return if (mFragmentNumbers.containsKey(fragment)) {
//            mFragmentNumbers.get(fragment)
//        } else {
//            null
//        }
//    }

    /**
     * returns the fragment with the name @param
     * @param fragmentNumber
     * @return
     */
    fun getFragmentName(fragmentNumber: Int?): String? {
        return if (mFragmentNames.containsKey(fragmentNumber)) {
            mFragmentNames[fragmentNumber]
        } else {
            null
        }
    }
}





