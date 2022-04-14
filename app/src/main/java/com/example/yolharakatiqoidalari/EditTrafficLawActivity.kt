package com.example.yolharakatiqoidalari

import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import com.example.yolharakatiqoidalari.database.MyDatabase
import com.example.yolharakatiqoidalari.models.TrafficLawModel
import kotlinx.android.synthetic.main.activity_add_traffic_law.*
import kotlinx.android.synthetic.main.activity_edit_traffic_law.*
import java.io.File
import java.io.FileOutputStream

class EditTrafficLawActivity : AppCompatActivity() {

    lateinit var myDatabase: MyDatabase
    var imageP: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_traffic_law)
        myDatabase = MyDatabase(this)

        val id = intent.getIntExtra("trafficLaw", 0)
        val trafficLawModel = myDatabase.getTrafficLawById(id)
        text_et.setText(trafficLawModel.name)
        description_edit.setText(trafficLawModel.description)
        edit_image.setImageBitmap(BitmapFactory.decodeFile(trafficLawModel.imagePath))

        var index = -1
        val titleList = arrayListOf("Ogohlantiruvchi", "Imtiyozli", "Taqiqlovchi", "Buyuruvchi")
        for (i in titleList.indices) {
            if (trafficLawModel.type == titleList[i]) {
                index = i
            }
        }
        toolbarEditActivity.setNavigationOnClickListener {
            finish()
        }

        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, titleList)
        adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line)
        spinner1.adapter = adapter
        spinner1.setSelection(index)

        edit_image.setOnClickListener {
            getImageContent.launch("image/*")
        }

        save.setOnClickListener {
            if (text_et.text.toString().trim() != "" && description_edit.text.toString()
                    .trim() != ""
            ) {
                if (imageP == null){
                    imageP = trafficLawModel.imagePath
                }
                trafficLawModel.imagePath = imageP
                trafficLawModel.name = text_et.text.toString()
                trafficLawModel.description = description_edit.text.toString()
                trafficLawModel.type = titleList[spinner1.selectedItemPosition]
                myDatabase.updateTrafficLaw(trafficLawModel)
                finish()
                Toast.makeText(this, "Saqlandi", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Bo'sh maydonni to'ldiring!", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private var getImageContent =
        registerForActivityResult(ActivityResultContracts.GetContent()) { uri ->
            uri ?: return@registerForActivityResult
            edit_image.setImageURI(uri)

            val openInputSystem = contentResolver.openInputStream(uri)
            val file = File(filesDir, "image_${openInputSystem.hashCode()}.jpg")
            val outputStream = FileOutputStream(file)
            openInputSystem?.copyTo(outputStream)
            openInputSystem?.close()
            outputStream.close()
            imageP = file.path
        }
}