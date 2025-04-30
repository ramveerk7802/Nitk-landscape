package com.example.majorproject.Utilities

import kotlinx.serialization.Serializable

class Destination {

    @Serializable
    data object Main

    @Serializable
    data object Splash

    @Serializable
    data object Home
}