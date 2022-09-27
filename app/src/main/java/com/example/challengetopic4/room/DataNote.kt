package com.example.challengetopic4.room


import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity
data class DataNote (
    @PrimaryKey(autoGenerate = true)
    val id :Int,
    val title: String,
    val context: String
    ): Serializable {

}