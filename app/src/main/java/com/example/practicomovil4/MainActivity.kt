package com.example.practicomovil4

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.practicomovil4.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: ContactViewModel
    private lateinit var adapter: ContactAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this).get(ContactViewModel::class.java)
        adapter = ContactAdapter()

        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.adapter = adapter

        // Observa los cambios en la lista de contactos
        viewModel.contactos.observe(this) { contactos ->
            adapter.setContacts(contactos)
        }

        // Configura el SearchView para la búsqueda
        binding.searchView.setOnQueryTextListener(object : androidx.appcompat.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                filterContacts(query)
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                filterContacts(newText)
                return true
            }
        })

        // Configura el botón para agregar un nuevo contacto
        binding.btnNuevoContacto.setOnClickListener {
            startActivity(Intent(this, FormContact::class.java))
        }

        // Inicializa la carga de contactos
        viewModel.fetchContacts()
    }

    // Filtra la lista de contactos según la consulta de búsqueda
    private fun filterContacts(query: String?) {
        val filteredContacts = viewModel.contactos.value?.filter {
            it.name.contains(query ?: "", ignoreCase = true) ||
                    it.last_name.contains(query ?: "", ignoreCase = true)
        }
        adapter.setContacts(filteredContacts ?: emptyList())
    }
}





