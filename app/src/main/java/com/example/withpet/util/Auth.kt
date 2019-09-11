package com.example.withpet.util

import com.google.firebase.auth.FirebaseAuth

object Auth {

    private val fireBaseAuth = FirebaseAuth.getInstance()


    val email: String? = fireBaseAuth?.currentUser?.email
    val displayName: String? = fireBaseAuth?.currentUser?.displayName

    fun signOut() {
        fireBaseAuth?.signOut()
    }
}