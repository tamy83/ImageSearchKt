package com.example.imagesearchkt

import java.util.Arrays
import java.util.TreeSet

private val KNOWN_PRIMES = TreeSet(
    Arrays.asList(
        2, 3, 5, 7, 11, 13, 17, 19, 23, 29, 31, 37, 41, 43, 47, 53, 59, 61, 67,
        71, 73, 79, 83, 89, 97, 101, 103, 107, 109, 113, 127, 131, 137, 139,
        149, 151, 157, 163, 167, 173, 179, 181, 191, 193, 197, 199, 211, 223,
        227, 229, 233, 239, 241, 251, 257, 263, 269, 271, 277, 281, 283, 293,
        307, 311, 313, 317, 331, 337, 347, 349, 353, 359, 367, 373, 379, 383,
        389, 397, 401, 409, 419, 421, 431, 433, 439, 443, 449, 457, 461, 463,
        467, 479, 487, 491, 499
    )
)

// convertted from static method in java
fun isPrime(n: Int): Boolean {
    if (n < 2) return false

    if (KNOWN_PRIMES.contains(n)) {
        return true
    } else {
        val root = Math.sqrt(n.toDouble())
        if (root <= KNOWN_PRIMES.last()) {
            for (i in KNOWN_PRIMES.subSet(2, true, root.toInt(), true)) {
                if (n % i!! == 0) {
                    return false
                }
            }
            return true
        } else {
            var i = 2
            while (i <= Math.floor(root)) {
                if (n % i == 0) {
                    return false
                }
                i++
            }
            return true
        }
    }
}

