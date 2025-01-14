package com.example.feature.capitalizergenerator

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class CapitalizerGeneratorViewModel : ViewModel() {
    private val _viewStateFlow = MutableStateFlow(ViewState())
    val viewStateFlow = _viewStateFlow.asStateFlow()

    fun processEvent(viewEvent: ViewEvent){
        when(viewEvent){
            ViewEvent.OnCapitalizedPhrase -> capitalizerGenerator()
            is ViewEvent.OnPhraseChanged -> _viewStateFlow.update { it.copy(phraseToCapitalized = viewEvent.phrase) }
         }
    }

    private fun capitalizerGenerator(
        phrase: String = viewStateFlow.value.phraseToCapitalized
    ) {
        val myPhraseArray = phrase.toCharArray()
        val myMutableList = myPhraseArray.toMutableList()
        val spaceCharacter = 32
        val aLowercase = 65
        val zLowercase = 90
        val aUppercase = 97
        val zUppercase = 122

        var i = 0

        while (i < myPhraseArray.size) {
            if (i == 0 || myMutableList[i].code == spaceCharacter) {
                if (i < myMutableList.size - 1 && myMutableList[i].code == spaceCharacter && myMutableList[i+1].code != spaceCharacter) i++
                // Convert to uppercase
                convertCase(
                        code = myMutableList[i].code,
                        from = aUppercase,
                        to = zUppercase,
                        incrementCharAscii = false
                    ).also { myMutableList[i] = it }
            }else{
                // Convert to lowercase
                convertCase(
                        code = myMutableList[i].code,
                        from = aLowercase,
                        to = zLowercase,
                        incrementCharAscii = true
                    ).also { myMutableList[i] = it }
            }
            i++
        }
        _viewStateFlow.update { it.copy(phraseToCapitalized = "") }
        _viewStateFlow.update { it.copy(capitalizedPhrase = myMutableList.joinToString("")) }
    }

    private fun convertCase(
        code : Int,
        from: Int,
        to : Int,
        incrementCharAscii :Boolean
    ): Char {
        return if (code in from..to) {
            if (incrementCharAscii){
                (code + 32).toChar()
            }else{
                (code - 32).toChar()
            }
        }else{
            code.toChar()
        }
    }

    data class ViewState(
        val capitalizedPhrase : String = "Phrase not yet capitalized",
        val phraseToCapitalized : String = "",
    )

    sealed interface ViewEvent{
        data object OnCapitalizedPhrase : ViewEvent
        data class OnPhraseChanged(val phrase: String) : ViewEvent
     }

}