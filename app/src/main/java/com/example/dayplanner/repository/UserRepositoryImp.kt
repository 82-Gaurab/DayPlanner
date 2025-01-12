package com.example.dayplanner.repository

import com.example.dayplanner.model.UserModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class UserRepositoryImp: UserRepository {

    var auth: FirebaseAuth = FirebaseAuth.getInstance()

    val database: FirebaseDatabase = FirebaseDatabase.getInstance()
    val reference: DatabaseReference = database.reference.child("users")

    override fun login(email: String, password: String, callback: (Boolean, String) -> Unit) {
        auth.signInWithEmailAndPassword(email, password).addOnCompleteListener {
            if (it.isSuccessful) {
                callback(true, "Login Successfull")
            } else {
                callback(false, it.exception?.message.toString())
            }
        }
    }

    override fun signup(
        email: String,
        password: String,
        callback: (Boolean, String, String) -> Unit
    ) {
        val userid =auth.currentUser?.uid
        auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener {
            if (it.isSuccessful){
                callback(true, "New Account Created", userid.toString())
            } else {
                callback(false, it.exception?.message.toString(), "")
            }
        }
    }

    override fun forgetPassword(email: String, callback: (Boolean, String) -> Unit) {
        TODO("Not yet implemented")
    }

    override fun addUserToDatabase(
        userId: String,
        userModel: UserModel,
        callback: (Boolean, String) -> Unit
    ) {
        reference.child(userId.toString()).setValue(userModel).addOnCompleteListener {
            if (it.isSuccessful) {
                callback(true, "Registration Successfull")
            } else {
                callback(false, it.exception?.message.toString())
            }
        }
    }

    override fun getCurrentUser(): FirebaseUser? {
        return auth.currentUser
    }

    override fun getUserFromDatabase(
        userId: String,
        callback: (UserModel?, Boolean, String) -> Unit
    ) {
        reference.child(userId).addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    var model = snapshot.getValue(UserModel::class.java)

                    callback(model, true, "Details fetched Successfully")
                }
            }

            override fun onCancelled(error: DatabaseError) {
                callback(null, false, error.message)
            }
        })
    }

    override fun logout(callback: (Boolean, String) -> Unit) {
        try {
            auth.signOut()
            callback(true, "Signout successfull")
        }  catch (e: Exception) {
            callback(false, e.message.toString())
        }
    }

    override fun editProfile(
        userId: String,
        data: MutableMap<String, Any>,
        callback: (Boolean, String) -> Unit
    ) {
        reference.child(userId).updateChildren(data).addOnCompleteListener {
            if (it.isSuccessful){
                callback(true, "Profile edited successfully" )
            } else {
                callback(false, "Unable to edit profile!")
            }
        }
    }
}