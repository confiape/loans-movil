package org.confiape.loan.borrowers

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import org.confiape.loan.core.repositories.BorrowersRepository
import org.confiape.loan.core.repositories.TagRepository
import org.confiape.loan.models.BorrowerClientDto
import org.confiape.loan.models.BorrowerClientDtoNewBorrowerClientDto
import org.confiape.loan.models.TagDto
import javax.inject.Inject

@HiltViewModel
class BorrowerViewModel @Inject constructor(
    tagRepository: TagRepository, private val borrowersRepository: BorrowersRepository
) : ViewModel() {
    var name by mutableStateOf("")
    var dni by mutableStateOf("")
    var tags by mutableStateOf<List<TagDto>>(listOf())
    var selectedTags by mutableStateOf<List<TagDto>>(listOf())

    init {
        viewModelScope.launch {
            tags = tagRepository.getTags()
        }
    }

    fun onChangeName(newText: String) {
        name = newText
    }

    fun onChangeDni(newText: String) {
        dni = newText
    }

    fun onSelectedTags(tagDto: TagDto) {
        val existTag = selectedTags.any { it.id == tagDto.id }
        selectedTags = if (existTag) {
            selectedTags.filterNot { it.id == tagDto.id }
        } else {
            selectedTags + tagDto
        }
    }

    fun isSelectedTag(tagDto: TagDto): Boolean {
        return selectedTags.any { it.id == tagDto.id }
    }

    fun saveBorrower() {
        viewModelScope.launch {
            borrowersRepository.createBorrower(
                BorrowerClientDtoNewBorrowerClientDto(
                    tagIdList = selectedTags.map { it.id!! }, borrowerClientDto = BorrowerClientDto(
                        name = name, dni = dni
                    )

                )
            )
        }
    }
}
