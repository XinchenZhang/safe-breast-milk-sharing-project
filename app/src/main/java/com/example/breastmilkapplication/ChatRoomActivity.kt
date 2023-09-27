package com.example.breastmilkapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.ImageView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class ChatRoomActivity : AppCompatActivity() {

    private lateinit var messageRecyclerView: RecyclerView
    private lateinit var messageBox: EditText
    private lateinit var sendButton: ImageView
    private lateinit var messageList: ArrayList<Message>
    private lateinit var messageAdapter: MessageAdapter
    private lateinit var mDbRef: DatabaseReference

    var receiverRoom: String? = null
    var senderRoom: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat_room)

        val name = intent.getStringExtra("name")
        val receiverUid = intent.getStringExtra("uid")
        val senderUid = FirebaseAuth.getInstance().currentUser?.uid

        receiverRoom = senderUid + receiverUid
        senderRoom = receiverUid + senderUid
        mDbRef = FirebaseDatabase.getInstance().getReference()


        supportActionBar?.title = name

        messageRecyclerView = findViewById(R.id.chat_recycler_view)
        messageBox = findViewById(R.id.msg_box)
        sendButton = findViewById(R.id.send_btn)
        messageList = ArrayList()
        messageAdapter = MessageAdapter(this, messageList)

        messageRecyclerView.layoutManager = LinearLayoutManager(this)
        messageRecyclerView.adapter = messageAdapter


        mDbRef.child("chats").child(senderRoom!!).child("messages")
            .addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    messageList.clear()
                    for(messageSnapshot in snapshot.children){
                        val message = messageSnapshot.getValue(Message::class.java)
                        messageList.add(message!!)
                    }
                    messageAdapter.notifyDataSetChanged()
                }

                override fun onCancelled(error: DatabaseError) {

                }

            })

        sendButton.setOnClickListener {
            val message = messageBox.text.toString()
            if(message.isNotEmpty()){
                val messageObject = Message(message, senderUid)
                mDbRef.child("chats").child(senderRoom!!).child("messages").push()
                    .setValue(messageObject).addOnSuccessListener {
                        mDbRef.child("chats").child(receiverRoom!!).child("messages").push()
                        .setValue(messageObject)
                    }
            }

            messageBox.setText("")
        }

    }
}