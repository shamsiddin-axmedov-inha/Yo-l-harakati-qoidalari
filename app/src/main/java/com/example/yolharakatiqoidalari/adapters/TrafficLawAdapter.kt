package com.example.yolharakatiqoidalari.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.yolharakatiqoidalari.R
import com.example.yolharakatiqoidalari.models.TrafficLawModel
import kotlinx.android.synthetic.main.rv_item.view.*
import android.graphics.BitmapFactory

class TrafficLawAdapter(context: Context,val onMyItemClick: OnMyItemClick) :
    RecyclerView.Adapter<TrafficLawAdapter.Vh>() {

    private var list: ArrayList<TrafficLawModel>? = null

    fun setAdapter(list: ArrayList<TrafficLawModel>) {
        this.list = list
    }


    inner class Vh(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun onBind(trafficLawModel: TrafficLawModel, position: Int) {

            itemView.symbol.setImageBitmap(BitmapFactory.decodeFile(trafficLawModel.imagePath))
            itemView.textView.text = trafficLawModel.name
            if (trafficLawModel.isLiked == 0){
                itemView.like_icon.setBackgroundResource(R.drawable.blue_like)
            }else{
                itemView.like_icon.setBackgroundResource(R.drawable.like)
            }
            itemView.like_icon.setOnClickListener {
                onMyItemClick.likeIconClick(trafficLawModel)
                if (trafficLawModel.isLiked == 0){
                    itemView.like_icon.setBackgroundResource(R.drawable.blue_like)
                }else{
                    itemView.like_icon.setBackgroundResource(R.drawable.like)
                }
            }
            itemView.materialButton.setOnClickListener {
                onMyItemClick.changeLayoutClick(trafficLawModel)
            }
            itemView.remove_btn.setOnClickListener {
                onMyItemClick.removeLayoutClick(trafficLawModel, position)
            }
            itemView.setOnClickListener {
                onMyItemClick.itemClick(trafficLawModel)
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Vh {
        return Vh(LayoutInflater.from(parent.context).inflate(R.layout.rv_item, parent, false))
    }

    override fun onBindViewHolder(holder: Vh, position: Int) {
        holder.onBind(list!![position], position)
    }

    override fun getItemCount(): Int = list!!.size

    interface OnMyItemClick{
        fun likeIconClick(trafficLawModel: TrafficLawModel)
        fun changeLayoutClick(trafficLawModel: TrafficLawModel)
        fun removeLayoutClick(trafficLawModel: TrafficLawModel, position: Int)
        fun itemClick(trafficLawModel: TrafficLawModel)
    }
}