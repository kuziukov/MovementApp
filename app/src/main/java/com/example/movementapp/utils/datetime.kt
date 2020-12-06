package com.example.movementapp.utils


fun numberToLong(number: Number): Long{
    return number.toLong() * 1000 + (number.toDouble() % 1).toLong()
}