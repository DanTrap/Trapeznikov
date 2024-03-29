package com.dantrap.cinemania.fintech.feature.details

import androidx.lifecycle.viewModelScope
import com.dantrap.cinemania.fintech.core.common.network.Resource
import com.dantrap.cinemania.fintech.core.domain.usecase.GetMovieDetailsUseCase
import com.dantrap.cinemania.fintech.core.mvi.BaseViewModel
import com.dantrap.cinemania.fintech.feature.details.state.DetailsEvent
import com.dantrap.cinemania.fintech.feature.details.state.DetailsSideEffect
import com.dantrap.cinemania.fintech.feature.details.state.DetailsState
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.postSideEffect
import org.orbitmvi.orbit.syntax.simple.reduce

class DetailsViewModel(
    private val kinopoiskId: Int,
    private val getMovieDetailsUseCase: GetMovieDetailsUseCase,
) : BaseViewModel<DetailsState, DetailsSideEffect, DetailsEvent>(initialState = DetailsState()) {

    init {
        onEvent(DetailsEvent.LoadMovieDetails(kinopoiskId))
    }

    private var job: Job? = null

    override fun onEvent(event: DetailsEvent) {
        when (event) {
            is DetailsEvent.LoadMovieDetails -> loadMovieDetails(event.id)
            DetailsEvent.OnBackArrowClick -> navigateBack()
            DetailsEvent.OnRetryClick -> loadMovieDetails(kinopoiskId)
        }
    }

    private fun loadMovieDetails(id: Int) {
        job?.cancel()
        job = intent {
            getMovieDetailsUseCase(id).onEach { resource ->
                reduce {
                    when (resource) {
                        is Resource.Error -> state.copy(isLoading = false, error = resource.error)
                        is Resource.Loading -> state.copy(isLoading = true, error = null)
                        is Resource.Success -> state.copy(
                            isLoading = false,
                            movieDetails = resource.data
                        )
                    }
                }
            }.launchIn(viewModelScope)
        }
    }

    private fun navigateBack() {
        intent { postSideEffect(DetailsSideEffect.NavigateBack) }
    }
}
