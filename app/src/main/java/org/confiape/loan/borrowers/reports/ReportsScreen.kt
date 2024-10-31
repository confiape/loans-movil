package org.confiape.loan.borrowers.reports

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.pdf.PdfDocument
import android.os.Bundle
import android.os.CancellationSignal
import android.os.ParcelFileDescriptor
import android.print.PageRange
import android.print.PrintAttributes
import android.print.PrintDocumentAdapter
import android.print.PrintDocumentInfo
import android.print.PrintManager
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DatePicker
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MultiChoiceSegmentedButtonRow
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.SegmentedButton
import androidx.compose.material3.SegmentedButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Popup
import java.io.FileOutputStream
import java.text.SimpleDateFormat
import java.time.Instant
import java.time.OffsetDateTime
import java.time.ZoneOffset
import java.util.Date
import java.util.Locale
import java.util.TimeZone

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("DefaultLocale")
@Composable
fun ReportsScreen(reportsViewModel: ReportsViewModel) {
    val context = LocalContext.current

    if (reportsViewModel.isLoading) {
        Box(modifier = Modifier.fillMaxSize()) {
            CircularProgressIndicator(Modifier.align(Alignment.Center))
        }
    } else {
        Column {

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Button(
                    onClick = { printReport(context, reportsViewModel) },
                    modifier = Modifier
                        .padding(start = 16.dp)
                        .align(Alignment.CenterVertically)
                ) {
                    Text(text = "Imprimir Reporte")
                }
                DatePickerDocked(
                    defaultDate = reportsViewModel.currentOffsetDateTime ?: OffsetDateTime.now(),
                    onChangeDate = {
                        reportsViewModel.onChangeCurrentDay(it)
                    },
                    modifier = Modifier.weight(1f)
                )
            }
            MultiChoiceSegmentedButtonRow(
                modifier = Modifier.align(Alignment.CenterHorizontally),
            ) {
                reportsViewModel.tags.forEachIndexed { index, label ->
                    SegmentedButton(
                        shape = SegmentedButtonDefaults.itemShape(
                            index = index, count = reportsViewModel.tags.size
                        ),
                        onCheckedChange = {
                            label.id?.let { it1 -> reportsViewModel.onSelectTag(it1) }
                        },
                        checked = reportsViewModel.isSelectedTag(label.id),
                    ) {
                        Text(
                            text = label.title ?: "",
                            modifier = Modifier.fillMaxWidth(),
                            maxLines = 1
                        )
                    }
                }
            }

            LazyColumn(
                contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(items = reportsViewModel.filteredPaymentsByDayDto ?: listOf(), itemContent = {
                    Row(Modifier.fillMaxWidth()) {
                        Text(text = it.name ?: "d", modifier = Modifier.weight(0.7f))
                        Text(text = "S./", modifier = Modifier.weight(0.1f))
                        Text(
                            text = String.format("%,.2f", it.payment),
                            modifier = Modifier.weight(0.2f),
                            textAlign = TextAlign.End
                        )
                    }

                })
                item {
                    Spacer(modifier = Modifier.height(16.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = "Total pagado:",
                            modifier = Modifier.weight(0.6f),
                            style = MaterialTheme.typography.titleLarge
                        )
                        Text(
                            text = "S./",
                            modifier = Modifier.weight(0.1f),
                            style = MaterialTheme.typography.titleLarge
                        )
                        Text(
                            text = String.format("%,.2f", reportsViewModel.totalPayment),
                            modifier = Modifier.weight(0.3f),
                            textAlign = TextAlign.End,
                            style = MaterialTheme.typography.titleLarge
                        )
                    }

                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DatePickerDocked(
    onChangeDate: (OffsetDateTime) -> Unit,
    defaultDate: OffsetDateTime = OffsetDateTime.now(),
    @SuppressLint("ModifierParameter") modifier: Modifier = Modifier
) {
    val defaultMillis = defaultDate.toInstant().toEpochMilli()
    var showDatePicker by remember { mutableStateOf(false) }

    val datePickerState = rememberDatePickerState(initialSelectedDateMillis = defaultMillis)

    LaunchedEffect(datePickerState.selectedDateMillis) {
        datePickerState.selectedDateMillis?.let {
            val selectedOffsetDateTime = convertMillisToDateTimeOffset(it)
            onChangeDate(selectedOffsetDateTime)
            showDatePicker = false
        }
    }

    val selectedDate = datePickerState.selectedDateMillis?.let {
        convertMillisToDate(it)
    } ?: ""

    Box(modifier = modifier) {
        OutlinedTextField(value = selectedDate,
            onValueChange = { },
            label = { Text("Elige el día") },
            readOnly = true,
            trailingIcon = {
                IconButton(onClick = { showDatePicker = !showDatePicker }) {
                    Icon(imageVector = Icons.Default.DateRange, contentDescription = "Select date")
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(64.dp)
        )

        if (showDatePicker) {
            Popup(onDismissRequest = { showDatePicker = false }) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .offset(y = 64.dp)
                        .shadow(elevation = 4.dp)
                        .background(MaterialTheme.colorScheme.surface)
                        .padding(16.dp)
                ) {
                    DatePicker(
                        state = datePickerState, showModeToggle = true
                    )
                }
            }
        }
    }
}


fun convertMillisToDate(millis: Long): String {
    val formatter = SimpleDateFormat("dd-MM-yyyy", Locale("es", "PE"))
    formatter.timeZone = TimeZone.getTimeZone("UTC")
    return formatter.format(Date(millis))
}

fun convertMillisToDateTimeOffset(millis: Long): OffsetDateTime {
    val instant = Instant.ofEpochMilli(millis)
    return OffsetDateTime.ofInstant(
        instant, ZoneOffset.UTC
    )
}

fun printReport(context: Context, reportsViewModel: ReportsViewModel) {
    val printManager = context.getSystemService(Context.PRINT_SERVICE) as PrintManager
    val printAdapter = object : PrintDocumentAdapter() {
        override fun onStart() {}

        override fun onLayout(
            oldAttributes: PrintAttributes?,
            newAttributes: PrintAttributes?,
            cancellationSignal: CancellationSignal?,
            callback: LayoutResultCallback?,
            extras: Bundle?
        ) {
            if (cancellationSignal?.isCanceled == true) {
                callback?.onLayoutCancelled()
                return
            }

            val info = PrintDocumentInfo.Builder("report_payment_by_tags.pdf")
                .setContentType(PrintDocumentInfo.CONTENT_TYPE_DOCUMENT)
                .build()

            callback?.onLayoutFinished(info, true)
        }

        @SuppressLint("DefaultLocale")
        override fun onWrite(
            pageRanges: Array<out PageRange>?,
            destination: ParcelFileDescriptor?,
            cancellationSignal: CancellationSignal?,
            callback: WriteResultCallback?
        ) {
            val pdfDocument = PdfDocument()

            reportsViewModel.tags.forEach { tag ->
                // Filtrar los pagos correspondientes al tag actual
                val filteredPayments = reportsViewModel.paymentByDayDto.detailsDto?.filter {
                    it.tagId == tag.id
                } ?: listOf()

                if (filteredPayments.isNotEmpty()) {
                    // Crear una nueva página para el tag actual
                    var pageInfo = PdfDocument.PageInfo.Builder(595, 842, pdfDocument.pages.size + 1).create()
                    var page = pdfDocument.startPage(pageInfo)
                    var canvas = page.canvas
                    val paint = android.graphics.Paint()
                    paint.textSize = 14f

                    // Header para cada tag
                    paint.isFakeBoldText = true
                    canvas.drawText("Reporte de Pagos - Tag: ${tag.title ?: "N/A"}", 80f, 50f, paint)
                    canvas.drawText("Fecha: ${reportsViewModel.currentOffsetDateTime}", 80f, 100f, paint)
                    paint.isFakeBoldText = false

                    // Table Headers
                    var currentY = 160f
                    paint.isFakeBoldText = true
                    canvas.drawText("Nombre", 80f, currentY, paint)
                    canvas.drawText("Fecha Préstamo", 200f, currentY, paint)
                    canvas.drawText("Monto Prestado (S./)", 350f, currentY, paint)
                    canvas.drawText("Monto Pagado (S./)", 500f, currentY, paint)
                    paint.isFakeBoldText = false

                    // Table Content
                    currentY += 30f
                    filteredPayments.forEach {
                        if (currentY > 750f) { // Check if we need a new page for the current tag
                            pdfDocument.finishPage(page)
                            pageInfo = PdfDocument.PageInfo.Builder(595, 842, pdfDocument.pages.size + 1).create()
                            page = pdfDocument.startPage(pageInfo)
                            canvas = page.canvas
                            currentY = 50f

                            // Reimprimir encabezado de tabla
                            paint.isFakeBoldText = true
                            canvas.drawText("Nombre", 80f, currentY, paint)
                            canvas.drawText("Fecha Préstamo", 200f, currentY, paint)
                            canvas.drawText("Monto Prestado (S./)", 350f, currentY, paint)
                            canvas.drawText("Monto Pagado (S./)", 500f, currentY, paint)
                            paint.isFakeBoldText = false
                            currentY += 30f
                        }

                        // Datos de cada pago
                        canvas.drawText(it.name ?: "N/A", 80f, currentY, paint)
                        canvas.drawText(it.loanDate?.toLocalDate().toString(), 200f, currentY, paint)
                        canvas.drawText(String.format("%,.2f", it.amount ?: 0.0), 350f, currentY, paint)
                        canvas.drawText(String.format("%,.2f", it.payment), 500f, currentY, paint)
                        currentY += 30f
                    }

                    // Total Payment for current tag
                    if (currentY > 750f) {
                        pdfDocument.finishPage(page)
                        pageInfo = PdfDocument.PageInfo.Builder(595, 842, pdfDocument.pages.size + 1).create()
                        page = pdfDocument.startPage(pageInfo)
                        canvas = page.canvas
                        currentY = 50f
                    }
                    currentY += 30f
                    paint.isFakeBoldText = true
                    canvas.drawText(
                        "Total Pagado para ${tag.title ?: "N/A"}: S./ ${
                            String.format("%,.2f", filteredPayments.sumOf { it.payment ?: 0.0 })
                        }", 80f, currentY, paint
                    )

                    pdfDocument.finishPage(page)

                    // Agregar página en blanco si el número de páginas es impar
                    if (pdfDocument.pages.size % 2 != 0) {
                        val blankPageInfo = PdfDocument.PageInfo.Builder(595, 842, pdfDocument.pages.size + 1).create()
                        val blankPage = pdfDocument.startPage(blankPageInfo)
                        pdfDocument.finishPage(blankPage)
                    }
                }
            }

            try {
                destination?.fileDescriptor?.let {
                    FileOutputStream(it).use { output ->
                        pdfDocument.writeTo(output)
                    }
                }
                callback?.onWriteFinished(arrayOf(PageRange.ALL_PAGES))
            } catch (e: Exception) {
                callback?.onWriteFailed(e.message)
            } finally {
                pdfDocument.close()
            }
        }
    }

    printManager.print(
        "ReportPaymentByTagsPrintJob",
        printAdapter,
        PrintAttributes.Builder().setMediaSize(PrintAttributes.MediaSize.ISO_A4)
            .setDuplexMode(PrintAttributes.DUPLEX_MODE_LONG_EDGE).build()
    )
}


