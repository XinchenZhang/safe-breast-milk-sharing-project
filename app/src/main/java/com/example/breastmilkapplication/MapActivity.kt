package com.example.breastmilkapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.webkit.WebView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

class MapActivity : AppCompatActivity() {

    private lateinit var mAuth: FirebaseAuth
    private lateinit var mDbRef: FirebaseDatabase
    private lateinit var senderAddress: String
    private lateinit var receiverAddress: String
    private lateinit var senderProfile: Profile
    private lateinit var receiverProfile: Profile

    private lateinit var webView: WebView



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_map)

        val name = intent.getStringExtra("name")
        val receiverUid = intent.getStringExtra("uid")
        val senderUid = FirebaseAuth.getInstance().currentUser?.uid
        webView = findViewById(R.id.map_web_view)
        webView.settings.javaScriptEnabled = true

        senderProfile = Profile()
        receiverProfile = Profile()

        var loadCount = 0;

        mDbRef = FirebaseDatabase.getInstance()
        mDbRef.getReference("profile").child(senderUid!!).get().addOnSuccessListener {
            senderProfile = it.getValue(Profile::class.java)!!
            senderAddress = senderProfile.address!!
            loadCount++
            if(loadCount == 2){
                webView.loadUrl("https://www.google.com/maps/dir/?api=1&origin=$senderAddress&destination=$receiverAddress&travelmode=driving")
            }
        }.addOnFailureListener{
            //handle error
        }

        mDbRef.getReference("profile").child(receiverUid!!).get().addOnSuccessListener {
            receiverProfile = it.getValue(Profile::class.java)!!
            receiverAddress = receiverProfile.address!!
            loadCount++
            if(loadCount == 2){
                webView.loadUrl("https://www.google.com/maps/dir/?api=1&origin=$senderAddress&destination=$receiverAddress&travelmode=driving")
            }

        }.addOnFailureListener{
            //handle error
        }






    }
}