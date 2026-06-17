package com.kadircetin.cryptotracker.model

data class CryptoModel(
    val id: Int,
    val name: String,
    val symbol: String,
    val quote: Quote
)

