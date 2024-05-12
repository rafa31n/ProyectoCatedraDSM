package com.example.proyectocatedradsm


    class Ficha {
        var id_ficha: Int? = 0
        var id_tematica: Int? = 0
        var anverso: String? = null
        var enverso: String? = null
        var pistas: String? = null


        constructor(
            id_ficha: Int?,
            id_tematica: Int?,
            anverso: String?,
            enverso: String?,
            pistas: String?
        ) {
            this.id_ficha = id_ficha
            this.id_tematica = id_tematica
            this.anverso = anverso
            this.enverso = enverso
            this.pistas = pistas
        }
    }
