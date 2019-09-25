package com.example.withpet.util

import com.example.withpet.vo.pet.PetDTO
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

object Auth {

    fun getEmail(): String? {
        val fireBaseAuth = FirebaseAuth.getInstance()
        return fireBaseAuth?.currentUser?.email
    }

    fun getDisplayName(): String? {
        val fireBaseAuth = FirebaseAuth.getInstance()
        return fireBaseAuth?.currentUser?.displayName
    }


    fun login(user: FirebaseUser) {
        val fireBaseAuth = FirebaseAuth.getInstance()
        fireBaseAuth.updateCurrentUser(user)
    }

    fun signOut() {
        val fireBaseAuth = FirebaseAuth.getInstance()
        fireBaseAuth?.signOut()
    }

    fun getPetListId(petDTO: PetDTO): String {
        return "${petDTO.name}_${petDTO.birthDay}"
    }
}