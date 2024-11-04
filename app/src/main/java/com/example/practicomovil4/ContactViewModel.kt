package com.example.practicomovil4

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class ContactViewModel : ViewModel() {
    private val _contactos = MutableLiveData<List<Contact>>().apply { value = listOf() }
    val contactos: LiveData<List<Contact>> get() = _contactos

    private val apiService = RetrofitInstance.api

    fun fetchContacts() {
        viewModelScope.launch {
            try {
                val response = apiService.getContacts()
                if (response.isSuccessful) {
                    response.body()?.let { contacts ->
                        _contactos.value = contacts
                        Log.d("ContactoViewModel", "Contactos obtenidos: $contacts")
                    } ?: Log.e("ContactoViewModel", "Error: la respuesta de contactos es nula")
                } else {
                    Log.e("ContactoViewModel", "Error en la obtención de contactos: ${response.errorBody()?.string()}")
                }
            } catch (e: Exception) {
                Log.e("ContactoViewModel", "Error en la obtención de contactos", e)
            }
        }
    }

    fun addContact(contact: Contact): LiveData<Boolean> {
        val result = MutableLiveData<Boolean>()
        viewModelScope.launch {
            try {
                val response = apiService.addContact(contact)
                result.value = response.isSuccessful
                fetchContacts() // Actualizar la lista
            } catch (e: Exception) {
                Log.e("ContactoViewModel", "Error al agregar contacto", e)
                result.value = false
            }
        }
        return result
    }
    fun deleteContact(contactId: Int) {
        viewModelScope.launch {
            try {
                val response = apiService.deleteContact(contactId)
                if (response.isSuccessful) {
                    Log.d("ContactoViewModel", "Contacto eliminado exitosamente")
                    fetchContacts()
                } else {
                    Log.e("ContactoViewModel", "Error al eliminar contacto: ${response.errorBody()?.string()}")
                }
            } catch (e: Exception) {
                Log.e("ContactoViewModel", "Excepción al eliminar contacto", e)
            }
        }
    }
    fun updateContact(contact: Contact) {
        viewModelScope.launch {
            try {
                val response = apiService.updateContact(contact.id, contact)
                if (response.isSuccessful) {
                    Log.d("ContactoViewModel", "Contacto actualizado exitosamente")
                    fetchContacts()
                } else {
                    Log.e("ContactoViewModel", "Error al actualizar contacto: ${response.errorBody()?.string()}")
                }
            } catch (e: Exception) {
                Log.e("ContactoViewModel", "Excepción al actualizar contacto", e)
            }
        }
    }
    fun searchContacts(query: String) {
        viewModelScope.launch {
            try {
                val response = apiService.searchContacts(query)
                if (response.isSuccessful) {
                    response.body()?.let { contacts ->
                        _contactos.value = contacts
                        Log.d("ContactoViewModel", "Contactos obtenidos: $contacts")
                    } ?: Log.e("ContactoViewModel", "Error: la respuesta de contactos es nula")
                } else {
                    Log.e("ContactoViewModel", "Error en la búsqueda de contactos: ${response.errorBody()?.string()}")
                }
            } catch (e: Exception) {
                Log.e("ContactoViewModel", "Error en la búsqueda de contactos", e)
            }
        }
    }
    fun addPhone(phone: Phone) {
        viewModelScope.launch {
            try {
                val response = apiService.addPhone(phone)
                if (response.isSuccessful) {
                    Log.d("ContactoViewModel", "Teléfono agregado exitosamente")
                } else {
                    Log.e("ContactoViewModel", "Error al agregar teléfono: ${response.errorBody()?.string()}")
                }
            } catch (e: Exception) {
                Log.e("ContactoViewModel", "Excepción al agregar teléfono", e)
            }
        }
    }
}
