package com.sablot.marsservice.overview

import com.sablot.marsservice.network.MarsProperty

sealed class OverviewStates {
    object Loading : OverviewStates()
    data class Success(val data: List<MarsProperty>) : OverviewStates()
    data class Error(val message: String) : OverviewStates()
}