package com.android.example.android_test_app.ui.gallery

import android.Manifest
import android.app.Application
import android.content.Context
import android.database.Cursor
import android.provider.MediaStore
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import permissions.dispatcher.NeedsPermission
import permissions.dispatcher.RuntimePermissions


class GalleryViewModel(val context: Application) : AndroidViewModel(context) {

    private val _text = MutableLiveData<List<String>>()

    val text: LiveData<List<String>> = _text


    fun setAllImagePaths(paths: List<String>){
        _text.value = paths
    }
}