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
import org.confiape.loan.models.ReportPaymentsDto
import org.confiape.loan.models.TagDto
import java.io.FileOutputStream


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
                val filteredPayments = reportsViewModel.paymentByDayDto.detailsDto?.filter {
                    it.tagId == tag.id
                } ?: listOf()

                if (filteredPayments.isNotEmpty()) {
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
fun printReportFromList(context: Context, reportPaymentsList: List<ReportPaymentsDto>, tagList: List<TagDto>) {
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

            val info = PrintDocumentInfo.Builder("report_payment_list.pdf")
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

            if (reportPaymentsList.isNotEmpty()) {
                var pageInfo = PdfDocument.PageInfo.Builder(595, 842, pdfDocument.pages.size + 1).create()
                var page = pdfDocument.startPage(pageInfo)
                var canvas = page.canvas
                val paint = android.graphics.Paint()
                paint.textSize = 12f

                // Header
                paint.isFakeBoldText = true
                canvas.drawText("Reporte de Pagos - Lista Completa", 80f, 50f, paint)
                paint.isFakeBoldText = false

                // Agrupar por tagId
                val groupedPayments = reportPaymentsList.groupBy { it.tagId }

                var currentY = 100f
                var grandTotalPayment = 0.0
                groupedPayments.forEach { (tagId, payments) ->
                    // Obtener el nombre del tag
                    val tagName = tagList.find { it.id == tagId }?.title ?: "Sin Tag"

                    // Dibujar la línea de separación con el nombre del tag
                    paint.isFakeBoldText = true
                    canvas.drawLine(80f, currentY, 500f, currentY, paint)
                    canvas.drawText(tagName, 250f, currentY - 5f, paint)
                    paint.isFakeBoldText = false
                    currentY += 20f

                    // Table Headers
                    paint.isFakeBoldText = true
                    canvas.drawText("Nombre", 80f, currentY, paint)
                    canvas.drawText("Monto Pagado", 300f, currentY, paint)
                    paint.isFakeBoldText = false
                    currentY += 15f

                    // Table Content
                    payments.forEach {
                        if (currentY > 750f) { // Check if we need a new page
                            pdfDocument.finishPage(page)
                            pageInfo = PdfDocument.PageInfo.Builder(595, 842, pdfDocument.pages.size + 1).create()
                            page = pdfDocument.startPage(pageInfo)
                            canvas = page.canvas
                            currentY = 50f

                            // Reimprimir encabezado de tabla
                            paint.isFakeBoldText = true
                            canvas.drawText("Nombre", 80f, currentY, paint)
                            canvas.drawText("Monto Pagado", 300f, currentY, paint)
                            paint.isFakeBoldText = false
                            currentY += 15f
                        }

                        // Datos de cada pago
                        canvas.drawText(it.title ?: "N/A", 80f, currentY, paint)
                        canvas.drawText(String.format("%,.2f", it.payment), 300f, currentY, paint)
                        grandTotalPayment += it.payment ?: 0.0
                        currentY += 15f
                    }
                }

                // Grand Total Summary
                if (currentY > 750f) {
                    pdfDocument.finishPage(page)
                    pageInfo = PdfDocument.PageInfo.Builder(595, 842, pdfDocument.pages.size + 1).create()
                    page = pdfDocument.startPage(pageInfo)
                    canvas = page.canvas
                    currentY = 50f
                }
                currentY += 15f
                paint.isFakeBoldText = true
                canvas.drawText("Total Monto Pagado: S./ ${String.format("%,.2f", grandTotalPayment)}", 80f, currentY, paint)
                paint.isFakeBoldText = false

                pdfDocument.finishPage(page)
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
        "ReportPaymentListPrintJob",
        printAdapter,
        PrintAttributes.Builder().setMediaSize(PrintAttributes.MediaSize.ISO_A4)
            .setDuplexMode(PrintAttributes.DUPLEX_MODE_LONG_EDGE).build()
    )
}


