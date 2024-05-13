package com.example.proyectocatedradsm.Fichas

class Ficha {
    var id_ficha: Int? = 0
    var idTematica: Int? = 0
    var anverso: String? = null
    var reverso: String? = null
    var pistas: String? = null
    var resultado: String? = null

    constructor(
        id_ficha: Int?,
        idTematica: Int?,
        anverso: String?,
        reverso: String?,
        pistas: String?,
        resultado: String?,
    ) {
        this.id_ficha = id_ficha
        this.idTematica = idTematica
        this.anverso = anverso
        this.reverso = reverso
        this.pistas = pistas
        this.resultado = resultado
    }
}
