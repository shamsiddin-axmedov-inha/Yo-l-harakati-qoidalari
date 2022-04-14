package com.example.yolharakatiqoidalari.database

import com.example.yolharakatiqoidalari.models.TrafficLawModel

interface DatabaseService {

    fun insertTrafficLaw(trafficLawModel: TrafficLawModel)

    fun getAllTrafficLawsByIsLiked(): ArrayList<TrafficLawModel>

    fun getAllTrafficLawsByCategory(string: String): ArrayList<TrafficLawModel>

    fun getTrafficLawById(id: Int): TrafficLawModel

    fun updateTrafficLaw(trafficLawModel: TrafficLawModel): Int

    fun deleteTrafficLaw(trafficLawModel: TrafficLawModel)
}