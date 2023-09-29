package com.example.appgithubuser.data.database

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity(tableName = "favusers")
@Parcelize
data class FavoriteUser(
    @PrimaryKey
    @ColumnInfo(name = "username")
    var username: String,

    @ColumnInfo(name = "avatarUrl")
    var avatarUrl: String?,

    @field:ColumnInfo(name = "getFavorite")
    var getFavorite: Boolean,

    @ColumnInfo(name = "follower")
    var follower: String?,

    @ColumnInfo(name = "following")
    var following: String?,

    @ColumnInfo(name = "nameId")
    var nameId: String?,

    ): Parcelable