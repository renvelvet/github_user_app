package com.dicoding.consumerapp.helper

import android.database.Cursor
import com.dicoding.consumerapp.data.DatabaseContract
import com.dicoding.consumerapp.data.model.UserModel

object MappingHelper {

    fun mapCursorToArrayList(favoriteCursor: Cursor?): ArrayList<UserModel> {
        val favoriteList = ArrayList<UserModel>()
        favoriteCursor?.apply {
            while (moveToNext()) {
                val id = getInt(getColumnIndexOrThrow(DatabaseContract.FavoriteColumns._ID))
                val username = getString(getColumnIndexOrThrow(DatabaseContract.FavoriteColumns.USERNAME))
                val avatar = getString(getColumnIndexOrThrow(DatabaseContract.FavoriteColumns.AVATAR))
                favoriteList.add(UserModel(username,avatar,id))
            }
        }
        return favoriteList
    }
}