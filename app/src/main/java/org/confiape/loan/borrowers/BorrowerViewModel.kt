package org.confiape.loan.borrowers

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import org.confiape.loan.apis.BorrowerApi
import org.confiape.loan.core.repositories.TagRepository
import org.confiape.loan.models.BorrowerClientDto
import org.confiape.loan.models.BorrowerClientDtoNewBorrowerClientDto
import org.confiape.loan.models.TagDto
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class BorrowerViewModel @Inject constructor(
    tagRepository: TagRepository,
    private val borrowerApi: BorrowerApi
) : ViewModel() {
    var name by mutableStateOf("")
    var dni by mutableStateOf("")
    var tags by mutableStateOf<List<TagDto>>(listOf())
    private var selectedTags by mutableStateOf<List<TagDto>>(listOf())

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

    fun saveBorrower(borrowersViewModel: BorrowersViewModel) {
        viewModelScope.launch {
            borrowerApi.apiBorrowerPost(
                BorrowerClientDtoNewBorrowerClientDto(
                    tagIdList = selectedTags.map { it.id!! }, borrowerClientDto = BorrowerClientDto(
                        name = name, dni = dni, id = UUID.randomUUID()
                    )
                )
            )
            borrowersViewModel.updateBorrowers()
        }
    }
}
