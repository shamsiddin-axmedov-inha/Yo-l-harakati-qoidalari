package com.example.yolharakatiqoidalari

import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.yolharakatiqoidalari.database.MyDatabase
import com.example.yolharakatiqoidalari.models.TrafficLawModel
import kotlinx.android.synthetic.main.activity_info.*

class InfoActivity : AppCompatActivity() {
    lateinit var myDatabase: MyDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_info)
        myDatabase = MyDatabase(this)
        val trafficLawModel = myDatabase.getTrafficLawById(intent.getIntExtra("trafficLawId", 0)) as TrafficLawModel

        toolbarInfoActivity.setNavigationOnClickListener {
            finish()
        }
        toolbarInfoActivity.title = trafficLawModel.name
        symbol_image.setImageBitmap(BitmapFactory.decodeFile(trafficLawModel.imagePath))
        lawName.text = trafficLawModel.name
        descriptionTV.text = trafficLawModel.description
    }
}