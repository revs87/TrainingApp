package com.example.simpletextcomposeapplication

import org.junit.Assert
import org.junit.Test

class PalindromeLinkedList {

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
}

class ListNode(var `val`: Int) {
    var next: ListNode? = null
}
