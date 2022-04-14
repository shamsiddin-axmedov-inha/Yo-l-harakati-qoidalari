package com.example.yolharakatiqoidalari.database

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.yolharakatiqoidalari.models.TrafficLawModel

class MyDatabase(context: Context): SQLiteOpenHelper(context, Constants.DATABASE_NAME, null, Constants.DATABASE_VERSION), DatabaseService {
    override fun onCreate(db: SQLiteDatabase?) {
        val query = "create table ${Constants.TRAFFIC_TABLE} (${Constants.ID} integer not null primary key autoincrement unique, ${Constants.IMAGE} text not null, ${Constants.NAME} text not null, ${Constants.TEXT} text not null, ${Constants.TYPE} text not null, ${Constants.IS_LIKED} integer not null)"
        db?.execSQL(query)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
    }

    override fun insertTrafficLaw(trafficLawModel: TrafficLawModel) {
        val database = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(Constants.IMAGE, trafficLawModel.imagePath)
        contentValues.put(Constants.NAME, trafficLawModel.name)
        contentValues.put(Constants.TEXT, trafficLawModel.description)
        contentValues.put(Constants.TYPE, trafficLawModel.type)
        contentValues.put(Constants.IS_LIKED, trafficLawModel.isLiked)
        database.insert(Constants.TRAFFIC_TABLE, null, contentValues)
        database.close()
    }

    override fun getAllTrafficLawsByIsLiked(): ArrayList<TrafficLawModel> {
        val list = ArrayList<TrafficLawModel>()
        val query = "SELECT * FROM ${Constants.TRAFFIC_TABLE} WHERE ${Constants.IS_LIKED} = 1"
        val database = this.readableDatabase
        val cursor = database.rawQuery(query, null)
        if (cursor.moveToFirst()){
            do {
                val id = cursor.getInt(0)
                val imagePath = cursor.getString(1)
                val name = cursor.getString(2)
                val text = cursor.getString(3)
                val type = cursor.getString(4)
                val isLiked = cursor.getInt(5)

                val trafficLawModel = TrafficLawModel(id, imagePath, name, text, type, isLiked)
                list.add(trafficLawModel)
            }while (cursor.moveToNext())
        }
        return list
    }

    override fun getAllTrafficLawsByCategory(string: String): ArrayList<TrafficLawModel> {
        val list = ArrayList<TrafficLawModel>()
        val query = "SELECT * FROM ${Constants.TRAFFIC_TABLE} WHERE ${Constants.TYPE} = '$string'"
        val database = this.readableDatabase
        val cursor = database.rawQuery(query, null)
        if (cursor.moveToFirst()){
            do {
                val id = cursor.getInt(0)
                val imagePath = cursor.getString(1)
                val name = cursor.getString(2)
                val text = cursor.getString(3)
                val type = cursor.getString(4)
                val isLiked = cursor.getInt(5)

                val trafficLawModel = TrafficLawModel(id, imagePath, name, text, type, isLiked)
                list.add(trafficLawModel)
            }while (cursor.moveToNext())
        }
        return list
    }

    override fun getTrafficLawById(id: Int): TrafficLawModel {
        val database = this.readableDatabase
        val cursor = database.query(
            Constants.TRAFFIC_TABLE,
            arrayOf(
                Constants.ID,
                Constants.IMAGE,
                Constants.NAME,
                Constants.TEXT,
                Constants.TYPE,
                Constants.IS_LIKED
            ),
            "${Constants.ID} =?",
            arrayOf(id.toString()),
            null,
            null,
            null
        )
        cursor?.moveToFirst()
        val trafficLawModel = TrafficLawModel()
        trafficLawModel.id = cursor.getInt(0)
        trafficLawModel.imagePath = cursor.getString(1)
        trafficLawModel.name = cursor.getString(2)
        trafficLawModel.description = cursor.getString(3)
        trafficLawModel.type = cursor.getString(4)
        trafficLawModel.isLiked = cursor.getInt(5)

        return trafficLawModel
    }

    override fun updateTrafficLaw(trafficLawModel: TrafficLawModel): Int {
        val database = this.writableDatabase
        val contentValues = ContentValues()

        contentValues.put(Constants.ID, trafficLawModel.id)
        contentValues.put(Constants.IMAGE, trafficLawModel.imagePath)
        contentValues.put(Constants.NAME, trafficLawModel.name)
        contentValues.put(Constants.TEXT, trafficLawModel.description)
        contentValues.put(Constants.TYPE, trafficLawModel.type)
        contentValues.put(Constants.IS_LIKED, trafficLawModel.isLiked)

        return database.update(Constants.TRAFFIC_TABLE, contentValues, "${Constants.ID} = ?", arrayOf(trafficLawModel.id.toString()))
    }

    override fun deleteTrafficLaw(trafficLawModel: TrafficLawModel) {
        val database = this.writableDatabase
        database.delete(Constants.TRAFFIC_TABLE, "${Constants.ID} = ?", arrayOf(trafficLawModel.id.toString()))
        database.close()
    }
}