package com.example.yolharakatiqoidalari.models

class TrafficLawModel {

    var id: Int? = null
    var imagePath: String? = null
    var name: String? = null
    var description: String? = null
    var type: String? = null
    var isLiked: Int? = null

    constructor()


    constructor(
        id: Int?,
        imagePath: String?,
        name: String?,
        description: String?,
        type: String?,
        isLiked: Int?
    ) {
        this.id = id
        this.imagePath = imagePath
        this.name = name
        this.description = description
        this.type = type
        this.isLiked = isLiked
    }

    constructor(
        imagePath: String?,
        name: String?,
        description: String?,
        type: String?,
        isLiked: Int?
    ) {
        this.imagePath = imagePath
        this.name = name
        this.description = description
        this.type = type
        this.isLiked = isLiked
    }


}