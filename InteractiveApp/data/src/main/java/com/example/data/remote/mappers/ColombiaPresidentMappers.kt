package com.example.data.remote.mappers

import com.example.core.models.presidents.ColombiaPresident
import com.example.data.remote.models.ColombiaPresidentDto
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

fun ColombiaPresidentDto.toColombiaPresident() = ColombiaPresident(
    id = id?:0,
    image = image.orEmpty(),
    name = name.orEmpty(),
    lastName = lastName.orEmpty(),
    startPeriodDate =  formatDate(startPeriodDate.orEmpty(),),
    endPeriodDate = endPeriodDate?.let { formatDate(it) },
    politicalParty = politicalParty.orEmpty(),
    description = description.orEmpty(),
)

fun formatDate(date: String): String {
    val inputFormat = SimpleDateFormat("yyyy-MM-dd", Locale("es"))
    val outputFormat =
        SimpleDateFormat("d 'de' MMMM 'del' yyyy", Locale("es"))
    val parsedDate: Date = inputFormat.parse(date)
    return outputFormat.format(parsedDate)
}
