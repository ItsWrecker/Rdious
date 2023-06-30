package com.wrecker.rdious.presantation

import com.wrecker.rdious.domain.entities.Exclusion
import com.wrecker.rdious.domain.entities.Facility

data class MainState(
    val facilities: List<Facility> = emptyList(),
    val exclusion: List<Exclusion> = emptyList()
)