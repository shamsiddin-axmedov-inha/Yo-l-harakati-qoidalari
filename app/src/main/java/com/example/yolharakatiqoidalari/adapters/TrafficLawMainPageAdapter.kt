package com.example.yolharakatiqoidalari.adapters

import android.util.Log
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.fragment.app.FragmentStatePagerAdapter
import com.example.yolharakatiqoidalari.DescriptionFragment

class TrafficLawMainPageAdapter(
    private val titleList: ArrayList<String>,
    fragmentManager: FragmentManager
) : FragmentStatePagerAdapter(fragmentManager, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {
    override fun getCount(): Int = 4

    override fun getItem(position: Int): Fragment {
        return when (position) {
            0 -> {
                DescriptionFragment.newInstance("Ogohlantiruvchi")
            }
            1 -> {
                DescriptionFragment.newInstance("Imtiyozli")
            }
            2 -> {
                DescriptionFragment.newInstance("Taqiqlovchi")
            }
            else -> {
                DescriptionFragment.newInstance("Buyuruvchi")
            }
        }
    }

    override fun getPageTitle(position: Int): CharSequence {
        return titleList[position]
    }

}