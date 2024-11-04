package com.example.practicomovil4

import android.R
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.practicomovil4.databinding.FormContactBinding

class FormContact : AppCompatActivity() {
    private lateinit var binding: FormContactBinding
    private lateinit var viewModel: ContactViewModel
    private val listaTelefonos = mutableListOf<Phone>()
    private val listaEmails = mutableListOf<Email>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        binding = FormContactBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupSpinners()

        binding.btnGuardar.setOnClickListener {
            guardarContacto()
        }
    }
    private fun setupSpinners() {
        val etiquetasTelefono = listOf("Casa", "Trabajo", "Celular", "Otro")
        val etiquetasEmail = listOf("Persona", "Trabajo", "Universidad", "Otro")

        val adapterTelefono = ArrayAdapter(this, R.layout.simple_spinner_item, etiquetasTelefono)
        val adapterEmail = ArrayAdapter(this, android.R.layout.simple_spinner_item, etiquetasEmail)

        adapterTelefono.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        adapterEmail.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        binding.phoneLabelSpinner.adapter = adapterTelefono
        binding.emailLabelSpinner.adapter = adapterEmail

        binding.phoneLabelSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                if (binding.etTelefono.text.toString().isNotBlank()) {
                    val telefono = binding.etTelefono.text.toString()
                    val etiquetaTelefono = etiquetasTelefono[position]
                    listaTelefonos.add(Phone(0, 0, telefono, etiquetaTelefono))
                }
            }
            override fun onNothingSelected(parent: AdapterView<*>) {}
        }

        binding.emailLabelSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                if (binding.etEmail.text.toString().isNotBlank()) {
                    val email = binding.etEmail.text.toString()
                    val etiquetaEmail = etiquetasEmail[position]
                    listaEmails.add(Email(0, 0, email, etiquetaEmail))
                }
            }
            override fun onNothingSelected(parent: AdapterView<*>) {}
        }
    }

    private fun guardarContacto() {
        val nombre = binding.etNombre.text.toString()
        val apellido = binding.etApellido.text.toString()
        val compania = binding.etCompania.text.toString()
        val direccion = binding.etDireccion.text.toString()
        val ciudad = binding.etCiudad.text.toString()
        val estado = binding.etEstado.text.toString()

        if (binding.etTelefono.text.toString().isNotBlank()) {
            val telefono = binding.etTelefono.text.toString()
            val etiquetaTelefono = binding.phoneLabelSpinner.selectedItem.toString()
            listaTelefonos.add(Phone(0, 0, telefono, etiquetaTelefono))
        }

        if (binding.etEmail.text.toString().isNotBlank()) {
            val email = binding.etEmail.text.toString()
            val etiquetaEmail = binding.emailLabelSpinner.selectedItem.toString()
            listaEmails.add(Email(0, 0, email, etiquetaEmail))
        }

        val contact = Contact(
            id = 0,
            name = nombre,
            last_name = apellido,
            company = compania,
            address = direccion,
            city = ciudad,
            state = estado,
            profile_picture = "",
            phones = listaTelefonos,
            emails = listaEmails
        )

        viewModel.addContact(contact).observe(this) { success ->
            if (success) {
                startActivity(Intent(this, MainActivity::class.java))
                finish()
            }
        }
    }
}
