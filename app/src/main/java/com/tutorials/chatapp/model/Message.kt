package com.tutorials.chatapp.model

/**
 * Created by MD.Rabius sani raju on 10/8/22.
 */
class Message {
    var messageId:String?=null
    var message:String?=null
    var senderId:String?=null
    var imageUrl:String?=null
    var timeStamp:Long =0
    constructor(){}
    constructor(
        message: String?,
        senderId: String?,
        timeStamp:Long
    ){
        this.message = message
        this.senderId = senderId
        this.timeStamp = timeStamp
    }
}