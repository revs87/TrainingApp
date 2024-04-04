package com.example.simpletextcomposeapplication

import assertk.assertThat
import assertk.assertions.isEqualTo
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource
import kotlin.system.measureTimeMillis

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
        "Marco;M", "Maria;F", "Joao;M", "Luis;M", "Ana;F", "Isabel;M", "Ana;F", "Rita;I", "Luis;M",
        "Catarina;F", "Paulo;M", "Marina;F", "Luisa;F", "Marcia;F", "Pedro;M", "Joel;I", "Antonio;M",
        "Marisa;F", "Sofia;F", "Jose;M", "Patricia;F", "Paulo;M", "Marisa;F"
    )
    private val expectedDescending = listOf(
        "Ana;F;2", "Luis;M;2", "Marisa;F;2", "Paulo;M;2",
        "Antonio;M;1", "Catarina;F;1", "Isabel;M;1", "Joao;M;1", "Joel;I;1", "Jose;M;1", "Luisa;F;1",
        "Marcia;F;1", "Marco;M;1", "Maria;F;1", "Marina;F;1", "Patricia;F;1", "Pedro;M;1", "Rita;I;1",
        "Sofia;F;1",
    )
    private val expectedAscending = listOf(
        "Antonio;M;1", "Catarina;F;1", "Isabel;M;1", "Joao;M;1", "Joel;I;1", "Jose;M;1", "Luisa;F;1",
        "Marcia;F;1", "Marco;M;1", "Maria;F;1", "Marina;F;1", "Patricia;F;1", "Pedro;M;1", "Rita;I;1",
        "Sofia;F;1",
        "Ana;F;2", "Luis;M;2", "Marisa;F;2", "Paulo;M;2",
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

    @ParameterizedTest
    @ValueSource(booleans = [true, false])
    fun `Using groupingBy-eachCount and toSortedMap`(isDescending: Boolean) {

        val map = baseNames
            .groupingBy { it }
            .eachCount()
        val actual = map
            .toSortedMap(
                if (isDescending) { compareByDescending<String> { map[it] }.thenBy { it } }
                else { compareBy<String> { map[it] }.thenBy { it } }
            )
            .map {
                Profile(
                    it.key.split(";")[0],
                    it.key.split(";")[1],
                    it.value
                )
            }
            .map {
                "${it.name};${it.gender};${it.times}"
            }

        if (isDescending) { assertThat(actual).isEqualTo(expectedDescending) }
        else { assertThat(actual).isEqualTo(expectedAscending) }
    }

    @Test
    fun `Using groupingBy-eachCount and sortedWith Timed`() {
        var actual: List<String>? = null

        val time = measureTimeMillis {
            actual = baseNames
                .groupingBy { it }
                .eachCount()
                .toList()
                .sortedWith(compareByDescending<Pair<String, Int>> { it.second }.thenBy { it.first })
                .map { "${it.first.split(";")[0]};${it.first.split(";")[1]};${it.second}" }
        }

        assertThat(actual).isEqualTo(expectedDescending)

        println("Timed: ${time}ms")
    }
    
    @Test
    fun `Using groupingBy-eachCount and toSortedMap Timed`() {
        var actual: List<String>? = null

        val time = measureTimeMillis {
            val map = baseNames
                .groupingBy { it }
                .eachCount()
            actual = map
                .toSortedMap(compareByDescending<String> { map[it] }.thenBy { it })
                .map { "${it.key.split(";")[0]};${it.key.split(";")[1]};${it.value}" }
        }

        assertThat(actual).isEqualTo(expectedDescending)

        println("Timed: ${time}ms")
    }

    private val expectedNamesWithLengthDescending = listOf(
        "Catarina;F;8", "Patricia;F;8",
        "Antonio;M;7",
        "Isabel;M;6", "Marcia;F;6", "Marina;F;6", "Marisa;F;6",
        "Luisa;F;5", "Marco;M;5", "Maria;F;5", "Paulo;M;5", "Pedro;M;5", "Sofia;F;5",
        "Joao;M;4", "Joel;I;4", "Jose;M;4", "Luis;M;4", "Rita;I;4",
        "Ana;F;3"
    )

    @Test
    fun `Using associateWith to sort by name length descending`() {
        val namesWithLength = baseNames
            .associateWith { it.split(";")[0].length }
        val actual = namesWithLength
            .toSortedMap(compareByDescending<String> { namesWithLength[it] }.thenBy { it })
            .map {
                "${it.key};${it.value}"
            }

        assertThat(actual).isEqualTo(expectedNamesWithLengthDescending)
    }

    private data class Profile(val name: String, val gender: String, val times: Int)


    @Test
    fun `Receives list, returns groups of max sized lists`() {
        val list: List<Int> = (1..7).toList()

        val expected = listOf(
            listOf(1, 2, 3),
            listOf(4, 5, 6),
            listOf(7)
        )
        val actual = list.windowed(size = 3, step = 3, partialWindows = true)

        assertThat(actual).isEqualTo(expected)
    }
}
