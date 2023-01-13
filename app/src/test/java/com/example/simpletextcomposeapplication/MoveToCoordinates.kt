package com.example.simpletextcomposeapplication

import org.junit.Test

class MoveToCoordinates {

    @Test
    fun moveToNorthBoundary() {
        main(args = arrayOf("30", "0", "0", "17", "40"))
    }

    @Test
    fun moveToNorthWestBoundary() {
        main(args = arrayOf("0", "0", "39", "17", "40"))
    }

    @Test
    fun moveToSouthEastBoundary() {
        main(args = arrayOf("39", "17", "5", "5", "34"))
    }

    @Test
    fun moveToSouthEastBoundary_noEnergyLeft() {
        main(args = arrayOf("39", "17", "5", "5", "30"))
    }

    /**
     * Auto-generated code below aims at helping you parse
     * the standard input according to the problem statement.
     * ---
     * Hint: You can use the debug stream to print initialTX and initialTY, if Thor seems not follow your orders.
     **/
    private fun main(args : Array<String>) {
//        val input = Scanner(System.`in`)
//        val lightX = input.nextInt() // the X position of the light of power
//        val lightY = input.nextInt() // the Y position of the light of power
//        val initialTx = input.nextInt() // Thor's starting X position
//        val initialTy = input.nextInt() // Thor's starting Y position
        val lightX = args[0].toInt() // the X position of the light of power
        val lightY = args[1].toInt() // the Y position of the light of power
        val initialTx = args[2].toInt() // Thor's starting X position
        val initialTy = args[3].toInt() // Thor's starting Y position

        var currX = initialTx
        var currY = initialTy

        var remainingTurns = args[4].toInt() // The remaining amount of turns Thor can move. Do not remove this line.

        println("Starting at ($initialTx, $initialTy) and moving to ($lightX, $lightY). Energy = $remainingTurns")

        // game loop
        while ((currX != lightX || currY != lightY) && remainingTurns > 0) {
//            val remainingTurns = input.nextInt() // The remaining amount of turns Thor can move. Do not remove this line.

            // Write an action using println()
            // To debug: System.err.println("Debug messages...");

            val sameX = currX == lightX
            val sameY = currY == lightY
            val greaterX = lightX > currX
            val greaterY = lightY > currY
            val lesserX = lightX < currX
            val lesserY = lightY < currY

            val north = sameX && lesserY
            val south = sameX && greaterY
            val west = sameY && lesserX
            val east = sameY && greaterX
            val northWest = lesserX && lesserY
            val northEast = greaterX && lesserY
            val southWest = lesserX && greaterY
            val southEast = greaterX && greaterY

            val limitWest = currX == 0
            val limitEast = currX == 39
            val limitNorth = currY == 0
            val limitSouth = currY == 17

            var direction = ""

            when {
                north  -> {currY -= 1; direction = "N"}
                south  -> {currY += 1; direction = "S"}
                west   -> {currX -= 1; direction = "W"}
                east   -> {currX += 1; direction = "E"}
                northWest  -> {
                    if (limitNorth) {currX -= 1; direction = "W"}
                    else if (limitWest) {currY -= 1; direction = "N"}
                    else {currX -= 1; currY -= 1; direction = "NW"}
                }
                northEast  -> {
                    if (limitNorth) {currX += 1; direction = "E"}
                    else if (limitEast) {currY -= 1; direction = "N"}
                    else {currX += 1; currY -= 1; direction = "NE"}
                }
                southWest  -> {
                    if (limitSouth) {currX -= 1; direction = "W"}
                    else if (limitWest) {currY += 1; direction = "S"}
                    else {currX -= 1; currY += 1; direction = "SW"}
                }
                southEast  -> {
                    if (limitSouth) {currX += 1; direction = "E"}
                    else if (limitEast) {currY += 1; direction = "S"}
                    else {currX += 1; currY += 1; direction = "SE"}}
                else -> {}
            }

            remainingTurns--

            // A single line providing the move to be made: N NE E SE S SW W or NW
            println("Moved to $direction = ($currX, $currY) Energy = $remainingTurns")

            Thread.sleep(500)
        }
    }
}