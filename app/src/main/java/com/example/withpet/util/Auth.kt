package com.example.withpet.util

import com.google.firebase.auth.FirebaseAuth

object Auth {

    private val fireBaseAuth = FirebaseAuth.getInstance()

    var email: String? = fireBaseAuth?.currentUser?.email
    var displayName: String? = fireBaseAuth?.currentUser?.displayName

    fun signOut() {
        email = null
        displayName = null
        fireBaseAuth?.signOut()

    }
}