package com.dicoding.githubuserapp.provider

import android.content.ContentProvider
import android.content.ContentValues
import android.content.UriMatcher
import android.database.Cursor
import android.net.Uri
import com.dicoding.githubuserapp.data.local.FavoriteUserDatabase
import com.dicoding.githubuserapp.data.local.dao.FavortieUserDao

class FavoriteContentProvider : ContentProvider() {

    private lateinit var favortieUserDao: FavortieUserDao

    companion object {
        private const val AUTHORITY = "com.dicoding.githubuserapp"
        private const val TABLE_NAME = "favorite_user"
        private const val FAVORITE = 1
        private val sUriMatcher = UriMatcher(UriMatcher.NO_MATCH)

        init {
            sUriMatcher.addURI(AUTHORITY, TABLE_NAME, FAVORITE)
        }
    }

    override fun onCreate(): Boolean {
        favortieUserDao = context?.let { FavoriteUserDatabase.getInstance(it).favoriteUserDao() }!!
        return false
    }

    override fun query(
        uri: Uri, projection: Array<String>?, selection: String?,
        selectionArgs: Array<String>?, sortOrder: String?
    ): Cursor? {
        val cursor: Cursor?
        when (sUriMatcher.match(uri)) {
            FAVORITE -> {
                cursor = favortieUserDao.getUsers()
                cursor.setNotificationUri(context?.contentResolver, uri)
            }
            else -> {
                cursor = null
            }
        }
        return cursor
    }

    override fun getType(uri: Uri): String? {
        return null
    }

    override fun insert(uri: Uri, values: ContentValues?): Uri? {
        return null
    }

    override fun delete(uri: Uri, selection: String?, selectionArgs: Array<out String>?): Int {
        return 0
    }

    override fun update(
        uri: Uri,
        values: ContentValues?,
        selection: String?,
        selectionArgs: Array<out String>?
    ): Int {
        return 0
    }


}