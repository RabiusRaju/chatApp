package com.tutorials.chatapp

import android.app.ProgressDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.storage.FirebaseStorage
import com.tutorials.chatapp.adapter.MessagesAdapter
import com.tutorials.chatapp.databinding.ActivityChatBinding
import com.tutorials.chatapp.model.Message
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap

class ChatActivity : AppCompatActivity() {

    var binding:ActivityChatBinding?=null
    var adapter:MessagesAdapter?=null
    var messages:ArrayList<Message>?=null
    var senderRoom:String?=null
    var reveiverRoom:String?=null
    var database: FirebaseDatabase?=null
    var storage:FirebaseStorage?=null
    var dialog : ProgressDialog?=null
    var senderUid:String?=null
    var receiverUid:String?=null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChatBinding.inflate(layoutInflater)
        setContentView(binding!!.root)
        setSupportActionBar(binding!!.toolbar)
        database = FirebaseDatabase.getInstance()
        storage = FirebaseStorage.getInstance()
        dialog = ProgressDialog(this@ChatActivity)
        dialog!!.setMessage("Uploading image...")
        dialog!!.setCancelable(false)

        val name = intent.getStringExtra("name")
        val profile = intent.getStringExtra("image")
        binding!!.txtName.text = name
        Glide.with(this@ChatActivity).load(profile)
            .placeholder(R.drawable.ic_avatar)
            .into(binding!!.ivProfile)
        binding!!.ivBack.setOnClickListener {finish()}
        receiverUid = intent.getStringExtra("uid")
        senderUid = FirebaseAuth.getInstance().uid
        database!!.reference.child("Presence").child(receiverUid!!)
            .addValueEventListener(object : ValueEventListener{
                override fun onDataChange(snapshot: DataSnapshot) {
                    if (snapshot.exists()){
                        val status = snapshot.getValue(String::class.java)
                        if(status == "offline"){
                            binding!!.txtStatus.visibility = View.GONE
                        }else{
                            binding!!.txtStatus.text =status
                            binding!!.txtStatus.visibility = View.VISIBLE
                        }
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    TODO("Not yet implemented")
                }

            })

        senderRoom = senderUid + receiverUid
        reveiverRoom = receiverUid + senderUid

        adapter = MessagesAdapter(this@ChatActivity,messages,senderRoom!!,reveiverRoom!!)

        binding!!.recyclerView.layoutManager = LinearLayoutManager(this@ChatActivity)
        binding!!.recyclerView.adapter = adapter
        database!!.reference.child("chats")
            .child(senderRoom!!)
            .child("message")
            .addValueEventListener(object : ValueEventListener{
                override fun onDataChange(snapshot: DataSnapshot) {
                    messages!!.clear()
                    for(snapshot1 in snapshot.children){
                        val message : Message?= snapshot1.getValue(Message::class.java)
                        message!!.messageId = snapshot1.key
                        messages!!.add(message)
                    }
                    adapter!!.notifyDataSetChanged()
                }

                override fun onCancelled(error: DatabaseError) {
                    TODO("Not yet implemented")
                }

            })

        binding!!.ivSend.setOnClickListener {
            val messageTxt:String = binding!!.messageBox.text.toString()
            val date = Date()
            val message = Message(messageTxt,senderUid,date.time)
            binding!!.messageBox.setText("")
            val randomKey = database!!.reference.push().key
            val lastMsgObj = HashMap<String,Any>()
            lastMsgObj["lastMsg"] = message.message!!
            lastMsgObj["lastMsgTime"] = date.time

        }
    }
}