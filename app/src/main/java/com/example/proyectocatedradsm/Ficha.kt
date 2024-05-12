package com.example.proyectocatedradsm

class Ficha {
    var id_ficha: Int? = 0
    var idTematica: Int? = 0

    var anverso: String? = null
    var enverso: String? = null
    var pistas: String? = null

    constructor(
        id_ficha: Int?,
        idTematica: Int?,
        anverso: String?,
        enverso: String?,
        pistas: String?
    ) {
        this.id_ficha = id_ficha
        this.idTematica = idTematica
        this.anverso = anverso
        this.enverso = enverso
        this.pistas = pistas

    }
}
