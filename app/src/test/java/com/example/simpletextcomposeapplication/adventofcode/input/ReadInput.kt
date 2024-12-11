package com.example.simpletextcomposeapplication.adventofcode.input

import kotlin.io.path.Path
import kotlin.io.path.readLines


fun readInput(filename: String): List<String> =
    Path("/Users/revs/repo/TrainingApp/app/src/test/java/com/example/simpletextcomposeapplication/adventofcode/input/$filename").readLines()
