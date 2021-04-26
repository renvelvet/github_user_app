package com.dicoding.consumerapp.data

import android.net.Uri
import android.provider.BaseColumns

object DatabaseContract {
    const val AUTHORITY = "com.dicoding.githubuserapp"
    const val SCHEME = "content"

    class FavoriteColumns : BaseColumns {

        companion object {
            const val TABLE_NAME = "favorite_user"
            const val _ID = "uid"
            const val USERNAME = "username"
            const val AVATAR = "avatar_url"

            val CONTENT_URI: Uri = Uri.Builder().scheme(SCHEME)
                .authority(AUTHORITY)
                .appendPath(TABLE_NAME)
                .build()
        }
    }
}