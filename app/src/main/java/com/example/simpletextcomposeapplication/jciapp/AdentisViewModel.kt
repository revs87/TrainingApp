package com.example.simpletextcomposeapplication.jciapp

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

interface IAdentisViewModel {
    fun getSortedNamesData()

    fun getLiveData(): LiveData<List<GenderProfile>>
}

class AdentisViewModel : ViewModel(), IAdentisViewModel {
    private val listLiveData: MutableLiveData<List<GenderProfile>> = MutableLiveData()

    override fun getSortedNamesData() {

        val baseNames = listOf(
            "Marco;M",
            "Maria;F",
            "Joao;M",
            "Luis;M",
            "Ana;F",
            "Isabel;M",
            "Ana;F",
            "Rita;I",
            "Luis;M",
            "Catarina;F",
            "Paulo;M",
            "Marina;F",
            "Luisa;F",
            "Marcia;F",
            "Pedro;M",
            "Joel;I",
            "Antonio;M",
            "Marisa;F",
            "Sofia;F",
            "Jose;M",
            "Patricia;F",
            "Paulo;M",
            "Marisa;F"
        )

        /**
         *
         *
         * Descrição:
         *
         * Mostrar o conteúdo da lista numa Recycler View, indicando quantidade de vezes
         * que repete o nome, texto para indicar se é masculino, feminino ou indefinido. Ordenado
         * alfabeticamente (por defeito) e permitir modifcar por numero de ocorrencias.
         * Identificar com uma cor distinta por cada genero (masculino, feminino, indefinido).
         * Mostrar o conteúdo das lista numa Recycler View.
         *
         * */
        val namesData = mutableMapOf<GenderProfile, Int>()
        baseNames
            .map { nameGender ->
                val split = nameGender.split(';')
                GenderProfile(split[0], split[1])
            }
            .map { profile ->
                if (namesData.containsKey(profile)) {
                    val oldValue = namesData[profile] ?: -1
                    namesData.put(profile, oldValue + 1)
                } else {
                    namesData.put(profile, 1)
                }
            }
        val sortedNamesData = namesData
            .toSortedMap(compareBy<GenderProfile> { it.name }.thenBy { namesData[it] })
            .map { GenderProfile(it.key.name, it.key.gender, it.value) }



//        val sorte2 = baseNames
//            .distinct()
//            .map { nameGender ->
//                val split = nameGender.split(';')
//                GenderProfile(split[0], split[1])
//            }
//            .map { GenderProfile(it.name, it.gender, baseNames.count { p -> p == it.name }) }
//


        listLiveData.value = sortedNamesData
    }

    override fun getLiveData(): LiveData<List<GenderProfile>> {
        return listLiveData
    }

}