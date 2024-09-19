package org.confiape.loan.borrowers

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import org.confiape.loan.core.AppConstants
import org.confiape.loan.core.repositories.BorrowersRepository
import org.confiape.loan.core.repositories.TagRepository
import org.confiape.loan.models.BasicBorrowerClientWithTagsAndLoans
import org.confiape.loan.models.TagDto
import javax.inject.Inject

@HiltViewModel
class BorrowersViewModel @Inject constructor(
    private val borrowersRepository: BorrowersRepository,
    private val tagRepository: TagRepository,
) : ViewModel() {

    private var allBorrowers by mutableStateOf<List<BasicBorrowerClientWithTagsAndLoans>>(listOf())
    private var selectedTags by mutableStateOf<List<String>>(listOf())

    var showAddBorrowerScreen by mutableStateOf(false)
        private set

    var filterBorrowers by mutableStateOf<List<BasicBorrowerClientWithTagsAndLoans>>(listOf())
        private set

    var tags by mutableStateOf<List<TagDto>>(listOf())
        private set

    var searchText by mutableStateOf("")
        private set

    init {
        Log.i(AppConstants.Tag, "Init borrower viewmodel")
        viewModelScope.launch {
            allBorrowers = borrowersRepository.getBorrowers()
            tags = tagRepository.getTags()
            filterBorrowers = allBorrowers
        }
    }

    fun onSelectTag(id: String) {
        selectedTags = if (id in selectedTags) {
            selectedTags - id
        } else {
            selectedTags + id
        }

        filterBorrowers = if (selectedTags.isEmpty()) {
            allBorrowers
        } else {
            allBorrowers.filter { borrower ->
                selectedTags.any { tagId ->
                    borrower.tags!!.contains(tagId)
                }
            }
        }
    }
    fun activateAddBorrowerScreen(isActivate: Boolean){
        showAddBorrowerScreen=isActivate
    }

    fun isSelectedTag(id: String?): Boolean {
        return id in selectedTags
    }

    fun onSearchTextChange(newText: String) {
        searchText = newText
        filterBorrowers = if (searchText.isEmpty()) {
            allBorrowers
        } else {
            allBorrowers.filter { borrower ->

                borrower.name!!.contains(searchText, ignoreCase = true)
            }
        }
    }
}