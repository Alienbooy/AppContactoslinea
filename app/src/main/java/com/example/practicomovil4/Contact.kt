package com.example.practicomovil4

data class Contact(
    val id: Int,
    val name: String,
    val last_name: String,
    val company: String,
    val address: String,
    val city: String,
    val state: String,
    val profile_picture: String,
    val phones: List<Phone> = listOf(),
    val emails: List<Email> = listOf()
)

data class Phone(
    val id: Int,
    val persona_id: Int,
    val label: String,
    val number: String
)

data class Email(
    val id: Int,
    val persona_id: Int,
    val label: String,
    val email: String
)


