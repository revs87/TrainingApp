package com.example.simpletextcomposeapplication

import org.junit.Assert
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
}

class ListNode(var `val`: Int) {
    var next: ListNode? = null
}
