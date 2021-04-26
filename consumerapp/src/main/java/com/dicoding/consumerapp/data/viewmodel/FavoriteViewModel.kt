package com.dicoding.consumerapp.data.viewmodel

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.dicoding.consumerapp.data.DatabaseContract
import com.dicoding.consumerapp.data.model.UserModel
import com.dicoding.consumerapp.helper.MappingHelper

class FavoriteViewModel(application: Application) : AndroidViewModel(application) {

    private val mfavoriteUsers = MutableLiveData<ArrayList<UserModel>>()

    fun setFavorite(context: Context) {
        val cursor = context.contentResolver.query(DatabaseContract.FavoriteColumns.CONTENT_URI,null,null,null,null)

        mfavoriteUsers.postValue(MappingHelper.mapCursorToArrayList(cursor))
    }

    fun getAll(): LiveData<ArrayList<UserModel>> {
        return mfavoriteUsers
    }

}