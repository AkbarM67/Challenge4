package com.example.challengetopic4

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.example.challengetopic4.databinding.FragmentAddBinding
import com.example.challengetopic4.room.DataNote
import com.example.challengetopic4.room.NoteDatabase
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async

class Fragment_add : Fragment() {

    lateinit var binding: FragmentAddBinding
    var NoteDB: NoteDatabase? = null
    lateinit var noteVm: NoteViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        //return inflater.inflate(R.layout.fragment_add, container, false)
        binding = FragmentAddBinding.inflate(layoutInflater, container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        NoteDB = NoteDatabase.getInstance(requireContext())
        noteVm = ViewModelProvider(this).get(NoteViewModel::class.java)

        binding.btnSaveNote.setOnClickListener {
            addNote()
            Navigation.findNavController(view).navigate(R.id.action_fragment_add_to_fragment_Home)
        }
    }

    @OptIn(DelicateCoroutinesApi::class)
    fun addNote(){
        GlobalScope.async {
            var title = binding.noteTitle.text.toString()
            var note = binding.noteContent.text.toString()

            NoteDB!!.noteDao().inserNote(DataNote(0,title,note))
            Toast.makeText(context, "add notes, success!", Toast.LENGTH_SHORT).show()
        }
        
    }



}