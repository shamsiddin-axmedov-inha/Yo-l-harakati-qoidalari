package com.example.yolharakatiqoidalari

import android.content.Intent
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.content.ContextCompat
import com.example.yolharakatiqoidalari.adapters.TrafficLawMainPageAdapter
import com.example.yolharakatiqoidalari.database.MyDatabase
import com.example.yolharakatiqoidalari.models.TrafficLawModel
import com.google.android.material.tabs.TabLayout
import kotlinx.android.synthetic.main.activity_add_traffic_law.view.*
import kotlinx.android.synthetic.main.fragment_main.view.*
import kotlinx.android.synthetic.main.tab_item.view.*
import kotlinx.android.synthetic.main.tab_shape.view.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [MainFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class MainFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    lateinit var root: View
    lateinit var titleList: ArrayList<String>
    lateinit var myDatabase: MyDatabase
    lateinit var trafficLawMainPageAdapter: TrafficLawMainPageAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        root = inflater.inflate(R.layout.fragment_main, container, false)

        myDatabase = MyDatabase(root.context)


        root.toolbar.setOnMenuItemClickListener {
            val intent = Intent(root.context, AddTrafficLawActivity::class.java)
            startActivity(intent)
            true
        }
        
        titleList = ArrayList()
        titleList.add("Ogohlantiruvchi")
        titleList.add("Imtiyozli")
        titleList.add("Taqiqlovchi")
        titleList.add("Buyuruvchi")
        trafficLawMainPageAdapter = TrafficLawMainPageAdapter(titleList, childFragmentManager)
        root.viewPager.adapter = trafficLawMainPageAdapter
        root.tabLayout.setupWithViewPager(root.viewPager)


        setTabs()

        root.tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                tab?.customView?.tab?.setBackgroundColor(resources.getColor(R.color.white))
                tab?.customView?.titleTv?.setTextColor(ContextCompat.getColor(root.context, R.color.theme)
                )
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
                tab?.customView?.tab?.background = AppCompatResources.getDrawable(root.context, R.drawable.tab_bg)
                tab?.customView?.titleTv?.setTextColor(resources.getColor(R.color.white))
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {

            }

        })

        return root
    }

    private fun setTabs() {
        val tabCount = root.tabLayout.tabCount

        for (i in 0 until tabCount) {
            val tabView = LayoutInflater.from(root.context).inflate(R.layout.tab_item, null, false)
            val tabAt = root.tabLayout.getTabAt(i)
            tabAt?.customView = tabView

            tabView.titleTv.text = titleList[i]

            if (i == 0){
                tabView.tab.setBackgroundColor(resources.getColor(R.color.white))
                tabView.titleTv.setTextColor(ContextCompat.getColor(root.context, R.color.theme))

            }else{
                tabView.tab.background = AppCompatResources.getDrawable(root.context, R.drawable.tab_bg)
                tabView.titleTv.setTextColor(ContextCompat.getColor(root.context, R.color.white))

            }
        }
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment MainFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            MainFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}