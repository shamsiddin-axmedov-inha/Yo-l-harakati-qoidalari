package com.example.yolharakatiqoidalari

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.yolharakatiqoidalari.adapters.TrafficLawAdapter
import com.example.yolharakatiqoidalari.database.MyDatabase
import com.example.yolharakatiqoidalari.models.TrafficLawModel
import kotlinx.android.synthetic.main.fragment_description.view.*
import kotlinx.android.synthetic.main.fragment_like.view.*

class LikeFragment : Fragment() {

    lateinit var root: View
    lateinit var myDatabase: MyDatabase
    lateinit var trafficLawList: ArrayList<TrafficLawModel>
    private lateinit var trafficLawAdapter: TrafficLawAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        root = inflater.inflate(R.layout.fragment_like, container, false)
        myDatabase = MyDatabase(root.context)
        trafficLawList = myDatabase.getAllTrafficLawsByIsLiked()
        trafficLawAdapter = TrafficLawAdapter(root.context, object : TrafficLawAdapter.OnMyItemClick{
            override fun likeIconClick(trafficLawModel: TrafficLawModel) {
                if (trafficLawModel.isLiked == 0){
                    trafficLawModel.isLiked = 1
                    myDatabase.updateTrafficLaw(trafficLawModel)
                    trafficLawList.clear()
                    trafficLawList = myDatabase.getAllTrafficLawsByIsLiked()
                    trafficLawAdapter.setAdapter(trafficLawList)
                    root.liked_rv.adapter = trafficLawAdapter
                }else{
                    trafficLawModel.isLiked = 0
                    myDatabase.updateTrafficLaw(trafficLawModel)
                    trafficLawList.clear()
                    trafficLawList = myDatabase.getAllTrafficLawsByIsLiked()
                    trafficLawAdapter.setAdapter(trafficLawList)
                    root.liked_rv.adapter = trafficLawAdapter
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
        root.liked_rv.adapter = trafficLawAdapter

        return root
    }

    override fun onResume() {
        super.onResume()
        trafficLawList = myDatabase.getAllTrafficLawsByIsLiked()
        trafficLawAdapter.setAdapter(trafficLawList)
        root.liked_rv.adapter = trafficLawAdapter
    }
}