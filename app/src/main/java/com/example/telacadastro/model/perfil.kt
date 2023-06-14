package com.example.telacadastro.model

data class perfil(
    val fot: Int,    // se pegar do firebase é uma string,
    val nome:String,
    val Descrição: String,
    val analisar: String,
    val uid: String,
    val imagePath:String,
    val imagePath1:String,
    val imagePath2:String
)