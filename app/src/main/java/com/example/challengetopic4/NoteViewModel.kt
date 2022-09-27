package com.example.challengetopic4

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.challengetopic4.room.DataNote
import com.example.challengetopic4.room.NoteDatabase

import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class NoteViewModel(app : Application):AndroidViewModel(app) {

    lateinit var allNote : MutableLiveData<List<DataNote>>

    init{
        allNote = MutableLiveData()
        getAllNote()
    }
    fun getAllNoteObservers(): MutableLiveData<List<DataNote>> {
        return allNote
    }



    fun getAllNote() {
        GlobalScope.launch {
            val userDao = NoteDatabase.getInstance(getApplication())!!.noteDao()
            val listnote = userDao.getDataNote()
            allNote.postValue(listnote)
        }
    }

    fun insertNote(entity: DataNote){
        val noteDao = NoteDatabase.getInstance(getApplication())?.noteDao()
        noteDao!!.inserNote(entity)
        getAllNote()
    }

    fun deleteNote(entity: DataNote){
        val userDao = NoteDatabase.getInstance(getApplication())!!.noteDao()
        userDao?.deleteNote(entity)
        getAllNote()
    }

    fun updateNote(entity: DataNote){
        val userDao = NoteDatabase.getInstance(getApplication())!!.noteDao()
        userDao?.UpdateNote(entity)
        getAllNote()
    }



}