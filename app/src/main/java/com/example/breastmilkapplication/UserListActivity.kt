package com.example.breastmilkapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class UserListActivity : AppCompatActivity() {


    private lateinit var userRecyclerView: RecyclerView
    private lateinit var userList: ArrayList<User>
    private lateinit var userAdapter: UserAdapter
    private lateinit var mDbRef: DatabaseReference
    private lateinit var mAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)

        mDbRef = FirebaseDatabase.getInstance().getReference()
        userList = ArrayList()
        userAdapter = UserAdapter(this, userList)
        mAuth = FirebaseAuth.getInstance()

        userRecyclerView = findViewById(R.id.user_recycler_view)
        userRecyclerView.layoutManager = LinearLayoutManager(this)
        userRecyclerView.adapter = userAdapter

        mDbRef.child("user").addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                userList.clear()
                for (userSnapshot in snapshot.children){
                    val currentUser = userSnapshot.getValue(User::class.java)
                    if(mAuth.currentUser?.uid != currentUser?.uid){
                        userList.add(currentUser!!)
                    }

                }
                userAdapter.notifyDataSetChanged()
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(this@UserListActivity, "Error", Toast.LENGTH_SHORT).show()
            }

        })

    }

}