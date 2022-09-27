package com.example.challengetopic4.room

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface NoteDAO {

    @Insert
    fun inserNote(note: DataNote)

    @Query("SELECT * FROM Datanote ORDER BY id DESC")
    fun getDataNote() : List<DataNote>

    @Delete
    fun deleteNote(note: DataNote)

    @Update
    fun UpdateNote(note: DataNote)
}