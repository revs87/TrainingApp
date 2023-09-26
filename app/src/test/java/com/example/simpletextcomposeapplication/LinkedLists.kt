package com.example.simpletextcomposeapplication

import assertk.assertThat
import assertk.assertions.isEqualTo
import org.junit.Assert
import org.junit.jupiter.api.RepeatedTest
import org.junit.jupiter.api.Test

class LinkedLists {

    /**
     * Example:
     * var li = ListNode(5)
     * var v = li.`val`
     * Definition for singly-linked list.
     * class ListNode(var `val`: Int) {
     *     var next: ListNode? = null
     * }
     */
    private fun isPalindrome(head: ListNode?): Boolean {
        head ?: return false
        var digit = head

        val list = ArrayList<Int>()
        while (digit != null) {
            list.add(digit.`val`)
            digit = digit.next
        }

        var i = 0
        var j = list.size - 1
        while (i < list.size / 2) {
            println("Comparing ${list[i]} with ${list[j]}")
            if (list[i] != list[j]) {
                return false
            }
            i++
            j--
        }
        return true
    }

    @Test
    fun test1() {
        val nodes = ListNode(1).apply { next = ListNode(2).apply { next = ListNode(2).apply { next = ListNode(1) } } }
        Assert.assertTrue(isPalindrome(nodes))
    }

    @Test
    fun test2() {
        val nodes = ListNode(1).apply { next = ListNode(2).apply { next = ListNode(3).apply { next = ListNode(2).apply { next = ListNode(1) } } } }
        Assert.assertTrue(isPalindrome(nodes))
    }

    @Test
    fun test3() {
        val nodes = ListNode(1).apply { next = ListNode(2) }
        Assert.assertFalse(isPalindrome(nodes))
    }

    @Test
    fun test4() {
        val nodes = ListNode(1)
        Assert.assertTrue(isPalindrome(nodes))
    }

    /**
     * Example:
     * var li = ListNode(5)
     * var v = li.`val`
     * Definition for singly-linked list.
     * class ListNode(var `val`: Int) {
     *     var next: ListNode? = null
     * }
     *
     * Input: head = [1,2,3,4,5]
     * Output: [3,4,5]
     * Explanation: The middle node of the list is node 3.
     *
     * Input: head = [1,2,3,4,5,6]
     * Output: [4,5,6]
     * Explanation: Since the list has two middle nodes with values 3 and 4, we return the second one.
     *
     */
    fun middleNodeList(head: ListNode?): IntArray {
        var index = 0
        var middleIndex = 0
        val list = ArrayList<Int>()

        var node = head
        while (node != null) {
            list.add(node.`val`)
            index++
            if (index % 2 == 0) { middleIndex++ }
            node = node.next
        }

        return list.subList(middleIndex, list.size).toIntArray()
    }

    @Test
    fun test_middleOf_0() {
        val nodes: ListNode? = null
        Assert.assertTrue(intArrayOf().contentEquals(middleNodeList(nodes)))
    }

    @Test
    fun test_middleOf_1() {
        val nodes = ListNode(1)
        Assert.assertTrue(intArrayOf(1).contentEquals(middleNodeList(nodes)))
    }

    @Test
    fun test_middleOf_5() {
        val nodes = ListNode(1).apply { next = ListNode(2).apply { next = ListNode(3).apply { next = ListNode(4).apply { next = ListNode(5) } } } }
        Assert.assertTrue(intArrayOf(3, 4, 5).contentEquals(middleNodeList(nodes)))
    }

    @Test
    fun test_middleOf_6() {
        val nodes = ListNode(1).apply { next = ListNode(2).apply { next = ListNode(3).apply { next = ListNode(4).apply { next = ListNode(5).apply { next = ListNode(6) }  } } } }
        Assert.assertTrue(intArrayOf(4, 5, 6).contentEquals(middleNodeList(nodes)))
    }


    /**
     * For this challenge you will be manipulating an array.
     *
     * Find Intersection
    Have the function FindIntersection(strArr) read the array of strings stored in strArr which will
    contain 2 elements: the first element will represent a list of comma-separated numbers sorted in
    ascending order, the second element will represent a second list of comma-separated numbers
    (also sorted). Your goal is to return a comma-separated string containing the numbers that occur
    in elements of strArr in sorted order. If there is no intersection, return the string false.
    Examples
    Input: ["1, 3, 4, 7, 13", "1, 2, 4, 13, 15"]
    Output: 1,4,13
    Input: ["1, 3, 9, 10, 17, 18", "1, 4, 9, 10"]
    Output: 1,9,10

     * */

    private fun findIntersection(strArr: Array<String>): String {

        val res: MutableList<Int> = mutableListOf()

        val list1 = strArr[0].split(',').map { it.trim().toInt() }.toList()
        val list2 = strArr[1].split(',').map { it.trim().toInt() }.toList()

        res.addAll(list1.filter { list2.contains(it) })

        if (res.isEmpty()) return "false"
        return res.joinToString(separator = ", ")
    }

    @Test
    fun test_intersectionEmpty() {
        val arrays = arrayOf("3, 4, 7, 13", "1, 2, 12, 15")
        println(findIntersection(arrays))
        Assert.assertEquals("false", findIntersection(arrays))
    }

    @Test
    fun test_intersectionA() {
        val arrays = arrayOf("1, 3, 4, 7, 13", "1, 2, 4, 13, 15")
        println(findIntersection(arrays))
        Assert.assertEquals("1, 4, 13", findIntersection(arrays))
    }

    @Test
    fun test_intersectionB() {
        val arrays = arrayOf("1, 3, 9, 10, 17, 18", "1, 4, 9, 10")
        println(findIntersection(arrays))
        Assert.assertEquals("1, 9, 10", findIntersection(arrays))
    }

    /**
     * Given a list of Person(fullName, age) provide a list as follows:
     * - with a Header listing by age decades {below 10s, 10 - 20, 20 - 30, etc}
     * - with a Subheader listing by lastname
     * - each Subheader contains the correspondent persons object
     * - sort all headers, subheaders and content by alphanumeric
     * */

    private data class Person(val fullName: String, val age: Int)

    private val firstNames = listOf("John", "Jane", "Michael", "Emily", "David", "Sarah", "Chris", "Laura", "Daniel", "Jessica", "Bruce", "Zeke", "Daniel")
    private val lastNames = listOf("Smith", "Johnson", "Taylor", "Brown", "Davis", "Miller", "Anderson", "Martin", "Harris", "Clark", "Johnson", "Almeida", "Johnson")
    private val ages = listOf(91, 5, 23, 24, 30, 39, 36, 41, 41, 59, 8, 1, 3)

    private fun generateFullName(index: Int): Person = Person(
        fullName = "${firstNames[index]} ${lastNames[index]}",
        age = ages[index]
    )

    @Test
    fun `GIVEN a list of Person(fullName, age), EXPECT a Map of decades as keys and a sorted list of Person as values`() {
        val given: List<Person> = firstNames.indices.map { index -> generateFullName(index) }

        val expected: Map<String, List<Person>> = mapOf(
            "0 - 10" to listOf(
                Person("Bruce Johnson", 8),
                Person("Daniel Johnson", 3),
                Person("Jane Johnson", 5),
                Person("Zeke Almeida", 1),
            ),
            "20 - 30" to listOf(
                Person("Emily Brown", 24),
                Person("Michael Taylor", 23),
            ),
            "30 - 40" to listOf(
                Person("Chris Anderson", 36),
                Person("David Davis", 30),
                Person("Sarah Miller", 39),
            ),
            "40 - 50" to listOf(
                Person("Daniel Harris", 41),
                Person("Laura Martin", 41),
            ),
            "50 - 60" to listOf(
                Person("Jessica Clark", 59),
            ),
            "90 - 100" to listOf(
                Person("John Smith", 91),
            ),
        )

        val actual = given
            .sortedBy { it.age }
            .groupBy { it.age.asInterval() }
            .mapValues { (_, values) -> values.sortedBy { person -> person.fullName } }
            .toMap()

        assertThat(actual).isEqualTo(expected)
    }

    @RepeatedTest(100)
    fun `GIVEN a list of Person(fullName, age) randomly ordered, EXPECT the same Map of decades as keys and a sorted list of Person as values`() {
        val given: List<Person> = firstNames.indices
            .map { index -> generateFullName(index) }
            .shuffled()

        val expected: Map<String, List<Person>> = mapOf(
            "0 - 10" to listOf(
                Person("Bruce Johnson", 8),
                Person("Daniel Johnson", 3),
                Person("Jane Johnson", 5),
                Person("Zeke Almeida", 1),
            ),
            "20 - 30" to listOf(
                Person("Emily Brown", 24),
                Person("Michael Taylor", 23),
            ),
            "30 - 40" to listOf(
                Person("Chris Anderson", 36),
                Person("David Davis", 30),
                Person("Sarah Miller", 39),
            ),
            "40 - 50" to listOf(
                Person("Daniel Harris", 41),
                Person("Laura Martin", 41),
            ),
            "50 - 60" to listOf(
                Person("Jessica Clark", 59),
            ),
            "90 - 100" to listOf(
                Person("John Smith", 91),
            ),
        )

        val actual = given
            .sortedBy { it.age }
            .groupBy { it.age.asInterval() }
            .mapValues { (_, values) -> values.sortedBy { person -> person.fullName } }
            .toMap()

        assertThat(actual).isEqualTo(expected)
    }


    @Test
    fun `GIVEN a list of Person(fullName, age), EXPECT a Map of decades, a Map of lastNames and a list of Persons as values sorted by descendant age`() {
        val given: List<Person> = firstNames.indices.map { index -> generateFullName(index) }

        val expected: Map<String, Map<String, List<Person>>> = mapOf(
            "0 - 10" to
                    mapOf(
                        "Almeida" to listOf(
                            Person("Zeke Almeida", 1),
                        ),
                        "Johnson" to listOf(
                            Person("Bruce Johnson", 8),
                            Person("Jane Johnson", 5),
                            Person("Daniel Johnson", 3),
                        ),
                    ),
            "20 - 30" to
                    mapOf(
                        "Brown" to listOf(
                            Person("Emily Brown", 24),
                        ),
                        "Taylor" to listOf(
                            Person("Michael Taylor", 23),
                        ),
                    ),
            "30 - 40" to
                    mapOf(
                        "Anderson" to listOf(
                            Person("Chris Anderson", 36),
                        ),
                        "Davis" to listOf(
                            Person("David Davis", 30),
                        ),
                        "Miller" to listOf(
                            Person("Sarah Miller", 39),
                        ),
                    ),
            "40 - 50" to
                    mapOf(
                        "Harris" to listOf(
                            Person("Daniel Harris", 41),
                        ),
                        "Martin" to listOf(
                            Person("Laura Martin", 41),
                        ),
                    ),
            "50 - 60" to
                    mapOf(
                        "Clark" to listOf(
                            Person("Jessica Clark", 59),
                        ),
                    ),
            "90 - 100" to
                    mapOf(
                        "Smith" to listOf(
                            Person("John Smith", 91),
                        ),
                    ),
        )

        val actual = given
            .sortedBy { it.age }
            .groupBy { it.age.asInterval() }
            .mapValues { (_, values) -> values.groupBy { it.fullName.split(" ")[1] } }
            .mapValues { (_, values) -> values.mapValues { (_, lastNames) -> lastNames.sortedByDescending { it.age } } }
            .toMap()

        assertThat(actual).isEqualTo(expected)
    }

    /**
     * ## Descrição
     *
     * Criar uma aplicação que permita ao utilizador introduzir uma frase e que ao clicar num botão
     * mostre numa lista o total de ocorrências para cada palavra. A lista deve ser ordenada pelo
     * total de ocorrências.
     *
     * ## Dados de Entrada
     *
     * "Esta é uma frase exemplo exemplo"
     * */
    @Test
    fun `Test Counting and Sorting words`() {
        val phrase = "Esta é uma frase exemplo exemplo"

        val expectedList = listOf<Pair<String, Int>>(
            Pair("Esta", 1),
            Pair("é", 1),
            Pair("uma", 1),
            Pair("frase", 1),
            Pair("exemplo", 2),
        )
        phrase
            .split(" ")
            .groupingBy { it }
            .eachCount()
            .toList()
            .sortedBy { it.second }
            .also { actual ->
                
                assertThat(actual).isEqualTo(expectedList)
            }

        val expectedMap = mapOf(
            "Esta" to 1,
            "frase" to 1,
            "uma" to 1,
            "é" to 1,
            "exemplo" to 2,
        )
        phrase
            .split(" ")
            .groupingBy { it }
            .eachCount()
            .also { map ->
                val actual =
                    map.toSortedMap(compareBy<String> { map[it] }.thenBy { it })

                assertThat(actual).isEqualTo(expectedMap)
            }
    }

}
private fun Int.asInterval(): String {
    val lowerBound = (this / 10) * 10
    val upperBound = lowerBound + 10
    return "$lowerBound - $upperBound"
}

class ListNode(var `val`: Int) {
    var next: ListNode? = null
}
