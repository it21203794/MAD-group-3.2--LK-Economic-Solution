package com.example.crudmyapp

class MainModel {
    var itemName: String? = null
    var price: String? = null
    var description: String? = null
   var iImage: String? = null

    internal constructor() {}
    constructor(itemName: String?, price: String?, description: String?, iImage: String?) {
        this.itemName = itemName
        this.price = price
        this.description = description
        this.iImage = iImage
    }

   /* fun getiImage(): String? {
        return iImage
    }

    fun setiImage(iImage: String?) {
        this.iImage = iImage
    }

    */
}