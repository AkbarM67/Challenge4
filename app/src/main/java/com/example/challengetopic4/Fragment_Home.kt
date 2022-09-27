package com.example.challengetopic4

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.challengetopic4.databinding.FragmentHomeBinding
import com.example.challengetopic4.room.DataNote
import com.example.challengetopic4.room.NoteDatabase
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class Fragment_Home : Fragment() {

    lateinit var binding: FragmentHomeBinding
    lateinit var shared : SharedPreferences
    var NoteDB : NoteDatabase? = null
    private lateinit var adapterNote : NoteAdapter
    private lateinit var noteViewModel : NoteViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
       //return inflater.inflate(R.layout.fragment__home, container, false)
        binding = FragmentHomeBinding.inflate(inflater,container,false)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        shared = requireContext().getSharedPreferences("datauser",Context.MODE_PRIVATE)
        binding.TextNama.text = shared.getString("UName","")
        NoteDB = NoteDatabase.getInstance(requireContext())

        noteVm()
        noteViewModel = ViewModelProvider(this)[NoteViewModel::class.java]
        noteViewModel.getAllNoteObservers().observe(viewLifecycleOwner, Observer {
            adapterNote.setNoteData(it as ArrayList<DataNote>)
        })

        binding.btnAddNote.setOnClickListener {
            Navigation.findNavController(view).navigate(R.id.action_fragment_Home_to_fragment_add)
        }

        binding.TvNote.setOnClickListener{
            var addUser = shared.edit()
            addUser.putString("UserName", "")
            addUser.putString("Upassword","")
            addUser.apply()

            Navigation.findNavController(view).navigate(R.id.action_fragment_Home_to_fragment_Login)
        }
    }

    fun noteVm() {
        adapterNote = NoteAdapter(ArrayList())
        binding.TvNote.layoutManager = LinearLayoutManager(context,LinearLayoutManager.VERTICAL,false)
        binding.TvNote.adapter = adapterNote
    }

    override fun onStart() {
        super.onStart()
        GlobalScope.launch {
            var data = NoteDB?.noteDao()?.getDataNote()
            activity?.runOnUiThread {
                adapterNote = NoteAdapter(data!!)
                binding.TvNote.layoutManager =
                    LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
                binding.TvNote.adapter = adapterNote
            }
        }
    }

    @OptIn(DelicateCoroutinesApi::class)
    fun getAllNote() {

        GlobalScope.launch {
            val data = NoteDB?.noteDao()?.getDataNote()
            activity?.runOnUiThread {
                adapterNote = NoteAdapter(data!!)
                binding.TvNote.layoutManager= LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
                binding.TvNote.adapter = adapterNote
            }
        }
    }



}


