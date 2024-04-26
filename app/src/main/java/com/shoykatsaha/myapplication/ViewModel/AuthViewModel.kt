package com.shoykatsaha.myapplication.ViewModel

import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.shoykatsaha.myapplication.models.User
class AuthViewModel : ViewModel() {

    private val auth = FirebaseAuth.getInstance()
    private val db = FirebaseFirestore.getInstance()

    fun registerUser(email: String, password: String, name: String, onSuccess: (User) -> Unit, onFailure: (String) -> Unit) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val firebaseUser = auth.currentUser
                    val user = User(firebaseUser!!.uid, email, name)
                    addUserToFirestore(user, onSuccess, onFailure)
                } else {
                    onFailure(task.exception?.message ?: "Registration failed")
                }
            }
    }
    // Log in an existing user with email and password
    fun loginUser(email: String, password: String, onSuccess: (User) -> Unit, onFailure: (String) -> Unit) {
        if (email.isEmpty() || password.isEmpty()) {
            onFailure("Email and password cannot be empty")
            return
        }

        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val firebaseUser = auth.currentUser
                   // val user = User(firebaseUser!!.uid, email)
                    val user = User(firebaseUser!!.uid, email, "")
                    onSuccess(user)
                } else {
                    onFailure(task.exception?.message ?: "Login failed")
                }
            }
    }
    // Add a user to Firestore
    private fun addUserToFirestore(user: User, onSuccess: (User) -> Unit, onFailure: (String) -> Unit) {
        db.collection("users").document(user.uid)
            .set(user)
            .addOnSuccessListener {
                onSuccess(user)
            }
            .addOnFailureListener { e ->
                onFailure(e.message ?: "Failed to add user to Firestore")
            }
    }


}


