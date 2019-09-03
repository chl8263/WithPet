package com.example.withpet.util

import com.google.firebase.storage.FirebaseStorage

class Storage {

    private val fireBaseStorage: FirebaseStorage = FirebaseStorage.getInstance()
    private val storageRef = fireBaseStorage.reference


}