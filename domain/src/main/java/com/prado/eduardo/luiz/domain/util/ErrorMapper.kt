package com.prado.eduardo.luiz.domain.util

interface ErrorMapper {
    fun map(throwable: Throwable): String
}
