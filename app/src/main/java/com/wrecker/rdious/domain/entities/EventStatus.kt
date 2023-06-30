package com.wrecker.rdious.domain.entities

sealed interface EventStatus<out R> {
    data class Error(val message: String?=null): EventStatus<Nothing>
    data class Loading(val message: String): EventStatus<Nothing>
    data class Success<R>(val data: R): EventStatus<R>
}