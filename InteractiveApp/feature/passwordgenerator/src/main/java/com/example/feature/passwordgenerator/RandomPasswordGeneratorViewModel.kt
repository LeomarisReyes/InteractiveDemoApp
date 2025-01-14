package com.example.feature.passwordgenerator

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlin.math.round

class RandomPasswordGeneratorViewModel : ViewModel() {

    private val _viewStateFlow = MutableStateFlow(ViewState())
    val viewStateFlow = _viewStateFlow.asStateFlow()

    fun processEvent(viewEvent: ViewEvent) {
        when (viewEvent) {
            ViewEvent.OnGeneratedPassword -> generatePassword()
            is ViewEvent.OnIncludeNumberChanged -> _viewStateFlow.update { it.copy(includeNumber = viewEvent.isEnabled) }
            is ViewEvent.OnIncludeSymbolChanged -> _viewStateFlow.update { it.copy(includeSymbol = viewEvent.isEnabled) }
            is ViewEvent.OnIncludeUpperCaseLetterChanged -> _viewStateFlow.update { it.copy(includeUpperCaseLetter = viewEvent.isEnabled) }
            is ViewEvent.OnSizeChanged -> _viewStateFlow.update {
                it.copy(size = round(viewEvent.size))
            }
        }
    }

    private fun generatePassword(
        hasUppercaseLetters: Boolean = viewStateFlow.value.includeUpperCaseLetter,
        hasNumbers: Boolean = viewStateFlow.value.includeNumber,
        hasSymbols: Boolean = viewStateFlow.value.includeSymbol,
        passwordSize: Float = viewStateFlow.value.size
    ) {
        viewModelScope.launch(Dispatchers.Default) {
            val lowerCaseList = ('a'..'z').toList()
            val numberList = if (hasNumbers) ('0'..'9').toList() else emptyList()
            val uppercaseList = if (hasUppercaseLetters) ('A'..'Z').toList() else emptyList()
            val symbolList = if (hasSymbols) {
                ('!'..'/') + (':'..'@') + ('['..'`') + ('{'..'~')
            } else emptyList()

            val requiredCharacters = mutableListOf<Char>().apply {
                if (hasUppercaseLetters) add(uppercaseList[timeBasedIndex(uppercaseList.size)])
                if (hasNumbers) add(numberList[timeBasedIndex(numberList.size)])
                if (hasSymbols) add(symbolList[timeBasedIndex(symbolList.size)])
                add(lowerCaseList[timeBasedIndex(lowerCaseList.size)])
            }

            val allPossibleCharacters = buildList {
                addAll(lowerCaseList)
                if (hasUppercaseLetters) addAll(uppercaseList)
                if (hasNumbers) addAll(numberList)
                if (hasSymbols) addAll(symbolList)
            }

            val remainingSize = passwordSize.toInt() - requiredCharacters.size
            val additionalCharacters = List(remainingSize) {
                allPossibleCharacters[timeBasedIndex(allPossibleCharacters.size, it)]
            }
            val finalPassword = (requiredCharacters + additionalCharacters).shuffledManually()
            _viewStateFlow.update { it.copy(generatedPassword = finalPassword.joinToString("")) }
        }
    }

    private fun timeBasedIndex(listSize: Int, offset: Int = 0): Int {
        val time = System.currentTimeMillis()
        return ((time / (offset + 1)) % listSize).toInt()
    }

    private fun List<Char>.shuffledManually(): List<Char> {
        val mutableList = this.toMutableList()
        mutableList.indices.forEach { i ->
            val swapIndex = timeBasedIndex(mutableList.size, i)
            mutableList[i] = mutableList[swapIndex].also { mutableList[swapIndex] = mutableList[i] }
        }
        return mutableList
    }

    data class ViewState(
        val includeUpperCaseLetter: Boolean = true,
        val includeNumber: Boolean = false,
        val includeSymbol: Boolean = false,
        val size: Float = 5f,
        val generatedPassword: String = "Password not generated yet"
    )

    sealed interface ViewEvent {
        data class OnIncludeUpperCaseLetterChanged(val isEnabled: Boolean) : ViewEvent
        data class OnIncludeNumberChanged(val isEnabled: Boolean) : ViewEvent
        data class OnIncludeSymbolChanged(val isEnabled: Boolean) : ViewEvent
        data class OnSizeChanged(val size: Float) : ViewEvent
        data object OnGeneratedPassword : ViewEvent
    }

}