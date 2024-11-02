package com.enma.pawfriends.viewmodel

import androidx.lifecycle.ViewModel
import javax.inject.Inject
import com.enma.pawfriends.model.Review
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

@HiltViewModel
class ReviewViewModel @Inject constructor() : ViewModel() {

    private val _reviews = MutableStateFlow<List<Review>>(emptyList())
    val reviews: StateFlow<List<Review>> get() = _reviews

    fun addReview(review: Review) {
        _reviews.value = _reviews.value + review
    }
}

annotation class HiltViewModel
