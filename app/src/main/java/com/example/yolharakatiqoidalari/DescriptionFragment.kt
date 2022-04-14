package com.example.yolharakatiqoidalari

import android.app.AlertDialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.example.yolharakatiqoidalari.adapters.TrafficLawAdapter
import com.example.yolharakatiqoidalari.database.MyDatabase
import com.example.yolharakatiqoidalari.models.TrafficLawModel
import kotlinx.android.synthetic.main.fragment_description.view.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"

/**
 * A simple [Fragment] subclass.
 * Use the [DescriptionFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class DescriptionFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
        }
    }

    lateinit var root: View
    lateinit var myDatabase: MyDatabase
    lateinit var trafficLawList: ArrayList<TrafficLawModel>
    private lateinit var trafficLawAdapter: TrafficLawAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        root = inflater.inflate(R.layout.fragment_description, container, false)
        myDatabase = MyDatabase(root.context)
        trafficLawList = myDatabase.getAllTrafficLawsByCategory(param1!!)

        trafficLawAdapter = TrafficLawAdapter(root.context, object : TrafficLawAdapter.OnMyItemClick{
            override fun likeIconClick(trafficLawModel: TrafficLawModel) {
                if (trafficLawModel.isLiked == 0){
                    trafficLawModel.isLiked = 1
                    myDatabase.updateTrafficLaw(trafficLawModel)
                }else{
                    trafficLawModel.isLiked = 0
                    myDatabase.updateTrafficLaw(trafficLawModel)
                }
            }

            override fun changeLayoutClick(trafficLawModel: TrafficLawModel) {
                val intent = Intent(root.context, EditTrafficLawActivity::class.java)
                intent.putExtra("trafficLaw", trafficLawModel.id)
                startActivity(intent)
            }

            override fun removeLayoutClick(trafficLawModel: TrafficLawModel, position: Int) {
                val dialog = AlertDialog.Builder(root.context)
                dialog.setMessage("Qoidani o’chirmoqchimisiz?")
                dialog.setPositiveButton(
                    "O'chirish"
                ) { _, which ->
                    myDatabase.deleteTrafficLaw(trafficLawModel)
                    trafficLawList.remove(trafficLawModel)
                    trafficLawAdapter.notifyItemRemoved(position)
                    trafficLawAdapter.notifyItemRangeChanged(position, trafficLawList.size)
                }
                dialog.setNegativeButton(
                    "Bekor qilish"
                ) { dialog, _ ->
                    dialog.dismiss()
                }
                dialog.show()
            }

            override fun itemClick(trafficLawModel: TrafficLawModel) {
                val intent = Intent(root.context, InfoActivity::class.java)
                intent.putExtra("trafficLawId", trafficLawModel.id)
                startActivity(intent)
            }
        })
        trafficLawAdapter.setAdapter(trafficLawList)
        root.rv.adapter = trafficLawAdapter

        return root
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment DescriptionFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String) =
            DescriptionFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                }
            }
    }
    override fun onResume() {
        super.onResume()
        trafficLawList = myDatabase.getAllTrafficLawsByCategory(param1!!)
        trafficLawAdapter.setAdapter(trafficLawList)
        root.rv.adapter = trafficLawAdapter
    }
}