package com.example.proyectocatedradsm

class Tematica {
    var idTematica: Int? = 0
    var nombre: String? = null
    var descripcion: String? = null
    var color: String? = null

    constructor(
        idTematica: Int?,
        nombre: String?,
        descripcion: String?,
        color: String?
    ) {
        this.idTematica = idTematica
        this.nombre = nombre
        this.descripcion = descripcion
        this.color = color
    }
}