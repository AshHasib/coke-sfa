package com.example.cokesfa.adapters

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter

class ViewPagerAdapter(fm:FragmentManager):FragmentPagerAdapter(fm) {

    val fragments= arrayListOf<Fragment>()
    val fragmentTitles = arrayListOf<String>()

    override fun getItem(i: Int): Fragment {
        return fragments.get(i)
    }

    override fun getCount(): Int {
        return fragments.size
    }

    override fun getPageTitle(i:Int):CharSequence {
        return fragmentTitles.get(i)
    }

    fun addFragment(name:String,f:Fragment) {
        fragments.add(f)
        fragmentTitles.add(name)
    }

}