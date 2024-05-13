package com.dshagapps.ia_tp2

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.dshagapps.es.DiseasesInferenceEngine

@Composable
fun MainScreen(
    modifier: Modifier = Modifier,
) {
    val inferDialogShown = remember { mutableStateOf(false) }
    val inferResult = remember { mutableStateOf("") }

    val feverSelectedOption = remember { mutableStateOf("") }
    val coughSelectedOption = remember { mutableStateOf("") }
    val difficultyBreathingSelectedOption = remember { mutableStateOf("") }
    val headacheSelectedOption = remember { mutableStateOf("") }
    val eyePainSelectedOption = remember { mutableStateOf("") }
    val musclePainSelectedOption = remember { mutableStateOf("") }
    val rashSelectedOption = remember { mutableStateOf("") }
    val soreThroatSelectedOption = remember { mutableStateOf("") }
    val congestionSelectedOption = remember { mutableStateOf("") }
    val lossOfTasteSmellSelectedOption = remember { mutableStateOf("") }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(18.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .verticalScroll(rememberScrollState())
        ) {
            SymptomRadioGroup(
                symptom = "Fever",
                options = listOf("No", "Low", "High"),
                selectedOption = feverSelectedOption
            )
            SymptomRadioGroup(
                symptom = "Cough",
                options = listOf("No", "Dry", "Wet"),
                selectedOption = coughSelectedOption
            )
            SymptomRadioGroup(
                symptom = "Difficulty Breathing",
                options = listOf("No", "Yes"),
                selectedOption = difficultyBreathingSelectedOption
            )
            SymptomRadioGroup(
                symptom = "Headache",
                options = listOf("No", "Mild", "Intense"),
                selectedOption = headacheSelectedOption
            )
            SymptomRadioGroup(
                symptom = "Eye Pain",
                options = listOf("No", "Yes"),
                selectedOption = eyePainSelectedOption
            )
            SymptomRadioGroup(
                symptom = "Muscle Pain",
                options = listOf("No", "Mild", "Intense"),
                selectedOption = musclePainSelectedOption
            )
            SymptomRadioGroup(
                symptom = "Rash",
                options = listOf("No", "Yes"),
                selectedOption = rashSelectedOption
            )
            SymptomRadioGroup(
                symptom = "Sore Throat",
                options = listOf("No", "Yes"),
                selectedOption = soreThroatSelectedOption
            )
            SymptomRadioGroup(
                symptom = "Congestion",
                options = listOf("No", "Yes"),
                selectedOption = congestionSelectedOption
            )
            SymptomRadioGroup(
                symptom = "Loss of Taste/Smell",
                options = listOf("No", "Yes"),
                selectedOption = lossOfTasteSmellSelectedOption
            )
        }

        Button(
            onClick = {
                inferResult.value = performInference(
                    feverSelectedOption.value,
                    coughSelectedOption.value,
                    difficultyBreathingSelectedOption.value,
                    headacheSelectedOption.value,
                    eyePainSelectedOption.value,
                    musclePainSelectedOption.value,
                    rashSelectedOption.value,
                    soreThroatSelectedOption.value,
                    congestionSelectedOption.value,
                    lossOfTasteSmellSelectedOption.value
                )
                inferDialogShown.value = true
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp)
        ) {
            Text(text = "Inferir")
        }

        Button(
            modifier = Modifier.fillMaxWidth(),
            onClick = {
                feverSelectedOption.value = ""
                coughSelectedOption.value = ""
                difficultyBreathingSelectedOption.value = ""
                headacheSelectedOption.value = ""
                eyePainSelectedOption.value = ""
                musclePainSelectedOption.value = ""
                rashSelectedOption.value = ""
                soreThroatSelectedOption.value = ""
                congestionSelectedOption.value = ""
                lossOfTasteSmellSelectedOption.value = ""
            }) {
            Text(text = "Reset")
        }
    }

    if (inferDialogShown.value) {
        AlertDialog(
            onDismissRequest = { inferDialogShown.value = false },
            title = { Text(text = "Resultado de la Inferencia") },
            text = { Text(text = inferResult.value) },
            confirmButton = {
                Button(
                    onClick = { inferDialogShown.value = false }
                ) {
                    Text(text = "Aceptar")
                }
            }
        )
    }
}

fun performInference(
    fever: String,
    cough: String,
    difficultyBreathing: String,
    headache: String,
    eyePain: String,
    musclePain: String,
    rash: String,
    soreThroat: String,
    congestion: String,
    lossOfTasteSmell: String,
): String {
    return DiseasesInferenceEngine.infer(
        fever = fever.lowercase(),
        cough = cough.lowercase(),
        difficultyBreathing = difficultyBreathing.lowercase(),
        headache = headache.lowercase(),
        eyePain = eyePain.lowercase(),
        musclePain = musclePain.lowercase(),
        rash = rash.lowercase(),
        soreThroat = soreThroat.lowercase(),
        congestion = congestion.lowercase(),
        lossOfTasteSmell = lossOfTasteSmell.lowercase()
    )
}


@Composable
fun SymptomRadioGroup(
    symptom: String,
    options: List<String>,
    selectedOption: MutableState<String>,
) {
    Column {
        Divider(
            modifier = Modifier.fillMaxWidth().padding(bottom = 8.dp).height(2.dp).background(Color.LightGray)
        )
        Text(text = symptom)
        Row(
            modifier = Modifier.fillMaxWidth()
        ) {
            options.forEach { option ->
                Column(
                    modifier = Modifier.weight(1f)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = CenterVertically
                    ) {
                        RadioButton(
                            selected = (selectedOption.value == option),
                            onClick = { selectedOption.value = option }
                        )
                        Text(text = option, modifier = Modifier.padding(start = 8.dp))
                    }
                }
            }
        }
    }
}