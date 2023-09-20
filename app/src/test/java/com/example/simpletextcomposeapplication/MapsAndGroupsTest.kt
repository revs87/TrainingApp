package com.example.simpletextcomposeapplication

import assertk.assertThat
import assertk.assertions.isEqualTo
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource

class MapsAndGroupsTest {

    /**
     *
     * Descrição:
     *
     * Mostrar o conteúdo da lista numa RecyclerView, indicando quantidade de vezes que repete o nome,
     * e identificar com uma bolinha de uma cor distinta cada gênero (masculino, feminino, indefinido).
     * O resultado tem de aparecer ordenado alfabeticamente (por defeito).Extra: Permitir que o utilizador
     * mude a ordem na lista por número de ocorrências.
     *
     *
     * Show the content of the list on a RecyclerView.
     *
     * You should show how many times the name exists on the original source (but must be shown only once),
     * and identify the gender with a color ball beside the name (male, female, indetermined).
     * The result must be shown alphabetically ordered (by default)Extra: allow the user to change
     * the order by ocurrencies of the name
     *
     *
     * Dados de Entrada:
     * "Marco;M","Maria;F","Joao;M","Luis;M","Ana;F","Isabel;M","Ana;F","Rita;I","Luis;M","Catarina;F",
     * "Paulo;M","Marina;F","Luisa;F","Marcia;F","Pedro;M","Joel;I","Antonio;M","Marisa;F","Sofia;F",
     * "Jose;M","Patricia;F","Paulo;M","Marisa;F”
     *
     * */

    private val baseNames = listOf(
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
    private val expectedDescending = listOf(
        "Ana;F;2",
        "Luis;M;2",
        "Marisa;F;2",
        "Paulo;M;2",
        "Antonio;M;1",
        "Catarina;F;1",
        "Isabel;M;1",
        "Joao;M;1",
        "Joel;I;1",
        "Jose;M;1",
        "Luisa;F;1",
        "Marcia;F;1",
        "Marco;M;1",
        "Maria;F;1",
        "Marina;F;1",
        "Patricia;F;1",
        "Pedro;M;1",
        "Rita;I;1",
        "Sofia;F;1",
    )
    private val expectedAscending = listOf(
        "Antonio;M;1",
        "Catarina;F;1",
        "Isabel;M;1",
        "Joao;M;1",
        "Joel;I;1",
        "Jose;M;1",
        "Luisa;F;1",
        "Marcia;F;1",
        "Marco;M;1",
        "Maria;F;1",
        "Marina;F;1",
        "Patricia;F;1",
        "Pedro;M;1",
        "Rita;I;1",
        "Sofia;F;1",
        "Ana;F;2",
        "Luis;M;2",
        "Marisa;F;2",
        "Paulo;M;2",
    )

    @ParameterizedTest
    @ValueSource(booleans = [true, false])
    fun `Using groupingBy-eachCount and sortedWith`(isDescending: Boolean) {

        val actual = baseNames
            .groupingBy { it }
            .eachCount()
            .map {
                Profile(
                    it.key.split(";")[0],
                    it.key.split(";")[1],
                    it.value
                )
            }
            .sortedWith(
                if (isDescending) {
                    compareByDescending<Profile> { it.times }
                        .thenBy { it.name }
                } else {
                    compareBy<Profile> { it.times }
                        .thenBy { it.name }
                }
            ).map {
                "${it.name};${it.gender};${it.times}"
            }

        if (isDescending) { assertThat(actual).isEqualTo(expectedDescending) }
        else { assertThat(actual).isEqualTo(expectedAscending) }
    }

    private data class Profile(val name: String, val gender: String, var times: Int = 0)
}