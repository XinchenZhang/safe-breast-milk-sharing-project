package com.example.breastmilkapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

class ProfileActivity : AppCompatActivity() {

    private lateinit var nameTextView: TextView
    private lateinit var emailTextView: TextView
    private lateinit var uidTextView: TextView
    private lateinit var mDbRef: FirebaseDatabase
    private lateinit var mAuth: FirebaseAuth
    private lateinit var User: User
    private lateinit var btnSave: Button
    private lateinit var edtPhone: EditText
    private lateinit var edtAddress: EditText
    private lateinit var edtCity: EditText
    private lateinit var edtState: EditText
    private lateinit var edtZip: EditText
    private lateinit var edtHeight: EditText
    private lateinit var edtWeight: EditText
    private lateinit var edtAge: EditText
    private lateinit var edtGender: EditText


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)
        mAuth = FirebaseAuth.getInstance()
        mDbRef = FirebaseDatabase.getInstance()
        nameTextView = findViewById(R.id.nameTextView)
        emailTextView = findViewById(R.id.emailTextView)
        uidTextView = findViewById(R.id.uidTextView)
        User = User()

        val currentUser = mAuth.currentUser
        val uid = currentUser?.uid
        mDbRef.getReference("user").child(uid!!).get().addOnSuccessListener {
            User = it.getValue(User::class.java)!!
            nameTextView.text = "Name: " + User.name
            emailTextView.text = "Email: " + User.email
            uidTextView.text = "UID: " + User.uid
        }.addOnFailureListener{
            //handle error
        }

        btnSave = findViewById(R.id.saveButton)


        btnSave.setOnClickListener{

            edtPhone = findViewById(R.id.phoneEditText)
            edtAddress = findViewById(R.id.addressEditText)
            edtCity = findViewById(R.id.cityEditText)
            edtState = findViewById(R.id.stateEditText)
            edtZip = findViewById(R.id.zipEditText)
            edtHeight = findViewById(R.id.heightEditText)
            edtWeight = findViewById(R.id.weightEditText)
            edtAge = findViewById(R.id.ageEditText)
            edtGender = findViewById(R.id.genderEditText)

            val updates = hashMapOf<String, Any>(
                "name" to User.name!!,
                "email" to User.email!!,
                "phone" to edtPhone.text.toString(),
                "address" to edtAddress.text.toString(),
                "city" to edtCity.text.toString(),
                "state" to edtState.text.toString(),
                "zip" to edtZip.text.toString(),
                "height" to edtHeight.text.toString(),
                "weight" to edtWeight.text.toString(),
                "age" to edtAge.text.toString(),
                "gender" to edtGender.text.toString()
            )
            updateUserProfile(uid, updates)
        }


    }

    private fun updateUserProfile(uid: String, updates: Map<String, Any>) {
        mDbRef = FirebaseDatabase.getInstance()

        mDbRef.getReference("profile").child(uid).updateChildren(updates)
            .addOnSuccessListener {
                // Update successful
                Toast.makeText(this, "Profile Updated", Toast.LENGTH_SHORT).show()
                Log.d("Firebase", "User profile updated successfully.")
            }
            .addOnFailureListener {
                // Update failed
                Log.d("Firebase", "User profile update failed.", it)
            }
    }

}