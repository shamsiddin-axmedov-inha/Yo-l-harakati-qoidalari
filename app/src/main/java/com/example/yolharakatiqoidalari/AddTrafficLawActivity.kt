package com.example.yolharakatiqoidalari

import android.Manifest
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import com.example.yolharakatiqoidalari.database.MyDatabase
import com.example.yolharakatiqoidalari.models.TrafficLawModel
import com.github.florent37.runtimepermission.RuntimePermission.askPermission
import com.github.florent37.runtimepermission.kotlin.askPermission
import kotlinx.android.synthetic.main.activity_add_traffic_law.*
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream

class AddTrafficLawActivity : AppCompatActivity() {
    var imagePath: String? = null
    lateinit var myDatabase: MyDatabase
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_traffic_law)
        myDatabase = MyDatabase(this)

        toolbarAddActivity.setNavigationOnClickListener {
            finish()
        }

        cat.setOnClickListener {
            askPermission(android.Manifest.permission.READ_EXTERNAL_STORAGE){
                getImageContent.launch("image/*")
                //all permissions already granted or just granted
            }.onDeclined { e ->
                if (e.hasDenied()) {

                    AlertDialog.Builder(this)
                        .setMessage("Please accept our permissions")
                        .setPositiveButton("yes") { dialog, which ->
                            e.askAgain();
                        } //ask again
                        .setNegativeButton("no") { dialog, which ->
                            dialog.dismiss();
                        }
                        .show();
                }

                if(e.hasForeverDenied()) {
                    Toast.makeText(this, "ForeverDenied", Toast.LENGTH_SHORT).show()
                    // you need to open setting manually if you really need it
                    e.goToSettings();
                }
            }
        }

        val categoryList = ArrayList<String>()
        categoryList.add("Ogohlantiruvchi")
        categoryList.add("Imtiyozli")
        categoryList.add("Taqiqlovchi")
        categoryList.add("Buyuruvchi")
        val adapter =
            ArrayAdapter(this, android.R.layout.simple_spinner_item, categoryList)
        adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line)
        spinner.adapter = adapter
        
        save_btn.setOnClickListener {
            if (imagePath != null && title_et.text.toString().trim() != "" && description_et.text.toString().trim() != ""){
                val trafficLawModel = TrafficLawModel(imagePath, title_et.text.toString(), description_et.text.toString(), categoryList[spinner.selectedItemPosition], 0)
                myDatabase.insertTrafficLaw(trafficLawModel)
                Toast.makeText(this, "Saqlandi", Toast.LENGTH_SHORT).show()
                finish()
            }else{
                Toast.makeText(this, "Bo'sh maydonni to'ldiring!", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private var getImageContent = registerForActivityResult(ActivityResultContracts.GetContent()){ uri->
        uri ?: return@registerForActivityResult
        add_image.setImageURI(uri)

        val openInputSystem = contentResolver.openInputStream(uri)
        val file = File(filesDir, "image_${openInputSystem.hashCode()}.jpg")
        val outputStream = FileOutputStream(file)
        openInputSystem?.copyTo(outputStream)
        openInputSystem?.close()
        outputStream.close()
        imagePath = file.path
    }
}