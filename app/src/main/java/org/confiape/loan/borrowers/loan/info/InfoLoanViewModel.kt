package org.confiape.loan.borrowers.loan.info

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import kotlinx.coroutines.time.delay
import org.confiape.loan.apis.PaymentApi
import org.confiape.loan.borrowers.BorrowersViewModel
import org.confiape.loan.models.NewPaymentDto
import org.confiape.loan.models.PointDto
import org.confiape.loan.models.SimplePayments
import java.time.Duration
import java.time.OffsetDateTime
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class InfoLoanViewModel @Inject constructor(
    private val paymentApi: PaymentApi,
) : ViewModel() {


    var amountToPAy by mutableStateOf("")
    var isDisablePayButton by mutableStateOf(false)


    fun OnChangeAmountToPay(it: String) {
        amountToPAy = it
    }

    fun pay(loanId: UUID, borrowersViewModel: BorrowersViewModel,isYape:Boolean) {
        isDisablePayButton = true
        viewModelScope.launch {
            try {
                val response = paymentApi.apiPaymentPost(
                    NewPaymentDto(
                        loanId = loanId,
                        amount = amountToPAy.toDouble(),
                        location = PointDto(0.0, 0.0),
                        isYape = isYape
                    )
                )
                if (response.code() == 200) {
                    borrowersViewModel.addLocalPayments(
                        loanId, SimplePayments(
                            dateTime=OffsetDateTime.now(),
                            amount=amountToPAy.toDouble(),
                            id=response.body()!!.id
                        )
                    )
                }
                delay(Duration.ofSeconds(2))
                isDisablePayButton = false
                amountToPAy = ""
            } catch (e: Exception) {
                delay(Duration.ofSeconds(2))
                isDisablePayButton = false
            }
        }
    }

    fun editPay(id: UUID?, borrowerViewModel: BorrowersViewModel,isYape:Boolean) {

        isDisablePayButton = true
        viewModelScope.launch {
            try {
                id?.let {
                    val response = paymentApi.apiPaymentIdPut(
                        id = it,
                        amount = amountToPAy.toDouble(),
                        isYape= isYape
                    )
                    if (response.code() == 200) {
                        borrowerViewModel.updateLocalPayments(
                            id, SimplePayments(
                                dateTime= OffsetDateTime.now(),
                                amount=amountToPAy.toDouble(),
                                id= response.body()!!.id
                            )
                        )
                    }
                }

                delay(Duration.ofSeconds(2))
                isDisablePayButton = false
                amountToPAy = ""
            } catch (e: Exception) {
                delay(Duration.ofSeconds(2))
                isDisablePayButton = false
            }
        }
    }

}