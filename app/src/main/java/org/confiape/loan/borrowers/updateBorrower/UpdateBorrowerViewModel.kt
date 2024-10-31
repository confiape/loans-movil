package org.confiape.loan.borrowers.addborrower

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import org.confiape.loan.apis.BorrowerApi
import org.confiape.loan.borrowers.BorrowersViewModel
import org.confiape.loan.core.repositories.TagRepository
import org.confiape.loan.models.BorrowerClientDto
import org.confiape.loan.models.TagDto
import org.confiape.loan.models.UpdateBorrowerClientDto
import org.confiape.loan.models.UpdateBorrowerClientDtoNewBorrowerClientDto
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class UpdateBorrowerViewModel @Inject constructor(
    tagRepository: TagRepository,
    private val borrowerApi: BorrowerApi
) : ViewModel() {
    lateinit var id: UUID;
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

    fun updateModel(id: UUID, tagDto: List<String>, name: String, dni: String) {
        selectedTags = tags.filter { all ->
            tagDto.any { tittle ->
                all.title == tittle
            }
        }
        this.id=id
        this.name = name
        this.dni = dni
    }

    fun isSelectedTag(tagDto: TagDto): Boolean {
        return selectedTags.any { it.id == tagDto.id }
    }

    fun saveBorrower(borrowersViewModel: BorrowersViewModel) {
        viewModelScope.launch {
            borrowerApi.apiBorrowerIdPut(
                id
                ,
                UpdateBorrowerClientDtoNewBorrowerClientDto(
                    tagIdList = selectedTags.map { it.id!! },
                    borrowerClientDto = UpdateBorrowerClientDto(
                        name = name, dni = dni
                    )
                )
            )
            borrowersViewModel.updateBorrowers()
        }
    }
}
