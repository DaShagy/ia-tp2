package com.dshagapps.es

import com.github.cschen1205.ess.engine.Clause
import com.github.cschen1205.ess.engine.EqualsClause
import com.github.cschen1205.ess.engine.KieRuleInferenceEngine
import com.github.cschen1205.ess.engine.Rule
import java.util.Vector

object DiseasesInferenceEngine {
    private var engine = KieRuleInferenceEngine().apply {
        DiseasesInferenceEngineRules.getRules().forEach { rule ->
            this.addRule(rule)
        }
    }

    fun infer(
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
        if (fever.isNotEmpty()) {
            engine.addFact(
                EqualsClause(
                    DiseasesInferenceEngineRules.RULE_VARIABLE_SYMPTOM_FEVER,
                    fever
                )
            )
        }
        if (cough.isNotEmpty()) {
            engine.addFact(
                EqualsClause(
                    DiseasesInferenceEngineRules.RULE_VARIABLE_SYMPTOM_COUGH,
                    cough
                )
            )
        }
        if (difficultyBreathing.isNotEmpty()) {
            engine.addFact(
                EqualsClause(
                    DiseasesInferenceEngineRules.RULE_VARIABLE_SYMPTOM_DIFFICULTY_BREATHING,
                    difficultyBreathing
                )
            )
        }
        if (headache.isNotEmpty()) {
            engine.addFact(
                EqualsClause(
                    DiseasesInferenceEngineRules.RULE_VARIABLE_SYMPTOM_HEADACHE,
                    headache
                )
            )
        }
        if (eyePain.isNotEmpty()) {
            engine.addFact(
                EqualsClause(
                    DiseasesInferenceEngineRules.RULE_VARIABLE_SYMPTOM_EYE_PAIN,
                    eyePain
                )
            )
        }
        if (musclePain.isNotEmpty()) {
            engine.addFact(
                EqualsClause(
                    DiseasesInferenceEngineRules.RULE_VARIABLE_SYMPTOM_MUSCLE_PAIN,
                    musclePain
                )
            )
        }
        if (rash.isNotEmpty()) {
            engine.addFact(
                EqualsClause(
                    DiseasesInferenceEngineRules.RULE_VARIABLE_SYMPTOM_RASH,
                    rash
                )
            )
        }
        if (soreThroat.isNotEmpty()) {
            engine.addFact(
                EqualsClause(
                    DiseasesInferenceEngineRules.RULE_VARIABLE_SYMPTOM_SORE_THROAT,
                    soreThroat
                )
            )
        }
        if (congestion.isNotEmpty()) {
            engine.addFact(
                EqualsClause(
                    DiseasesInferenceEngineRules.RULE_VARIABLE_SYMPTOM_CONGESTION,
                    congestion
                )
            )
        }
        if (lossOfTasteSmell.isNotEmpty()) {
            engine.addFact(
                EqualsClause(
                    DiseasesInferenceEngineRules.RULE_VARIABLE_SYMPTOM_LOSS_OF_TASTE_SMELL,
                    lossOfTasteSmell
                )
            )
        }

        val unprovedConditions: Vector<Clause> = Vector()
        val conclusion =
            engine.infer(DiseasesInferenceEngineRules.RULE_VARIABLE_DISEASE, unprovedConditions)

        val solution = if (conclusion != null) {
            "La enfermedad detectada es: ${conclusion.value}"
        } else {
            "No se pudo determinar la enfermedad"
        }

        restartEngine()

        return solution
    }

    private fun restartEngine() {
        engine = KieRuleInferenceEngine().apply {
            DiseasesInferenceEngineRules.getRules().forEach { rule ->
                this.addRule(rule)
            }
        }
    }
}

private object DiseasesInferenceEngineRules {
    const val RULE_VARIABLE_SYMPTOM_FEVER = "fever"
    const val RULE_VARIABLE_SYMPTOM_COUGH = "cough"
    const val RULE_VARIABLE_SYMPTOM_DIFFICULTY_BREATHING = "difficultyBreathing"
    const val RULE_VARIABLE_SYMPTOM_HEADACHE = "headache"
    const val RULE_VARIABLE_SYMPTOM_EYE_PAIN = "eyePain"
    const val RULE_VARIABLE_SYMPTOM_MUSCLE_PAIN = "musclePain"
    const val RULE_VARIABLE_SYMPTOM_RASH = "rash"
    const val RULE_VARIABLE_SYMPTOM_SORE_THROAT = "soreThroat"
    const val RULE_VARIABLE_SYMPTOM_CONGESTION = "congestion"
    const val RULE_VARIABLE_SYMPTOM_LOSS_OF_TASTE_SMELL = "lossOfTasteSmell"
    const val RULE_VARIABLE_DISEASE = "disease"

    const val RULE_VALUE_DISEASE_COVID = "COVID-19"
    const val RULE_VALUE_DISEASE_DENGUE = "Dengue"

    fun getRules(): List<Rule> = listOf(
        Rule("Rule #1").apply {
            addAntecedent(EqualsClause(RULE_VARIABLE_SYMPTOM_FEVER, "high"))
            addAntecedent(EqualsClause(RULE_VARIABLE_SYMPTOM_COUGH, "dry"))
            addAntecedent(EqualsClause(RULE_VARIABLE_SYMPTOM_DIFFICULTY_BREATHING, "yes"))
            consequent = EqualsClause(RULE_VARIABLE_DISEASE, RULE_VALUE_DISEASE_COVID)
        },
        Rule("Rule #2").apply {
            addAntecedent(EqualsClause(RULE_VARIABLE_SYMPTOM_FEVER, "high"))
            addAntecedent(EqualsClause(RULE_VARIABLE_SYMPTOM_HEADACHE, "intense"))
            addAntecedent(EqualsClause(RULE_VARIABLE_SYMPTOM_EYE_PAIN, "yes"))
            consequent = EqualsClause(RULE_VARIABLE_DISEASE, RULE_VALUE_DISEASE_DENGUE)
        },
        Rule("Rule #3").apply {
            addAntecedent(EqualsClause(RULE_VARIABLE_SYMPTOM_FEVER, "high"))
            addAntecedent(EqualsClause(RULE_VARIABLE_SYMPTOM_MUSCLE_PAIN, "intense"))
            addAntecedent(EqualsClause(RULE_VARIABLE_SYMPTOM_RASH, "yes"))
            consequent = EqualsClause(RULE_VARIABLE_DISEASE, RULE_VALUE_DISEASE_DENGUE)
        },
        Rule("Rule #4").apply {
            addAntecedent(EqualsClause(RULE_VARIABLE_SYMPTOM_FEVER, "high"))
            addAntecedent(EqualsClause(RULE_VARIABLE_SYMPTOM_COUGH, "dry"))
            addAntecedent(EqualsClause(RULE_VARIABLE_SYMPTOM_LOSS_OF_TASTE_SMELL, "yes"))
            consequent = EqualsClause(RULE_VARIABLE_DISEASE, RULE_VALUE_DISEASE_COVID)
        },
        Rule("Rule #5").apply {
            addAntecedent(EqualsClause(RULE_VARIABLE_SYMPTOM_FEVER, "high"))
            addAntecedent(EqualsClause(RULE_VARIABLE_SYMPTOM_HEADACHE, "intense"))
            addAntecedent(EqualsClause(RULE_VARIABLE_SYMPTOM_RASH, "yes"))
            addAntecedent(EqualsClause(RULE_VARIABLE_SYMPTOM_MUSCLE_PAIN, "mild"))
            consequent = EqualsClause(RULE_VARIABLE_DISEASE, RULE_VALUE_DISEASE_DENGUE)
        },
        Rule("Rule #6").apply {
            addAntecedent(EqualsClause(RULE_VARIABLE_SYMPTOM_FEVER, "high"))
            addAntecedent(EqualsClause(RULE_VARIABLE_SYMPTOM_COUGH, "wet"))
            addAntecedent(EqualsClause(RULE_VARIABLE_SYMPTOM_DIFFICULTY_BREATHING, "yes"))
            consequent = EqualsClause(RULE_VARIABLE_DISEASE, RULE_VALUE_DISEASE_COVID)
        },
        Rule("Rule #7").apply {
            addAntecedent(EqualsClause(RULE_VARIABLE_SYMPTOM_FEVER, "high"))
            addAntecedent(EqualsClause(RULE_VARIABLE_SYMPTOM_HEADACHE, "mild"))
            addAntecedent(EqualsClause(RULE_VARIABLE_SYMPTOM_RASH, "no"))
            addAntecedent(EqualsClause(RULE_VARIABLE_SYMPTOM_MUSCLE_PAIN, "mild"))
            consequent = EqualsClause(RULE_VARIABLE_DISEASE, RULE_VALUE_DISEASE_DENGUE)
        },
        Rule("Rule #8").apply {
            addAntecedent(EqualsClause(RULE_VARIABLE_SYMPTOM_FEVER, "high"))
            addAntecedent(EqualsClause(RULE_VARIABLE_SYMPTOM_COUGH, "dry"))
            addAntecedent(EqualsClause(RULE_VARIABLE_SYMPTOM_LOSS_OF_TASTE_SMELL, "no"))
            consequent = EqualsClause(RULE_VARIABLE_DISEASE, RULE_VALUE_DISEASE_COVID)
        },
        Rule("Rule #9").apply {
            addAntecedent(EqualsClause(RULE_VARIABLE_SYMPTOM_FEVER, "high"))
            addAntecedent(EqualsClause(RULE_VARIABLE_SYMPTOM_HEADACHE, "intense"))
            addAntecedent(EqualsClause(RULE_VARIABLE_SYMPTOM_RASH, "yes"))
            addAntecedent(EqualsClause(RULE_VARIABLE_SYMPTOM_MUSCLE_PAIN, "intense"))
            consequent = EqualsClause(RULE_VARIABLE_DISEASE, RULE_VALUE_DISEASE_DENGUE)
        },
        Rule("Rule #10").apply {
            addAntecedent(EqualsClause(RULE_VARIABLE_SYMPTOM_FEVER, "high"))
            addAntecedent(EqualsClause(RULE_VARIABLE_SYMPTOM_COUGH, "wet"))
            addAntecedent(EqualsClause(RULE_VARIABLE_SYMPTOM_DIFFICULTY_BREATHING, "yes"))
            consequent = EqualsClause(RULE_VARIABLE_DISEASE, RULE_VALUE_DISEASE_COVID)
        },
        Rule("Rule #11").apply {
            addAntecedent(EqualsClause(RULE_VARIABLE_SYMPTOM_FEVER, "high"))
            addAntecedent(EqualsClause(RULE_VARIABLE_SYMPTOM_HEADACHE, "mild"))
            addAntecedent(EqualsClause(RULE_VARIABLE_SYMPTOM_RASH, "no"))
            addAntecedent(EqualsClause(RULE_VARIABLE_SYMPTOM_MUSCLE_PAIN, "mild"))
            consequent = EqualsClause(RULE_VARIABLE_DISEASE, RULE_VALUE_DISEASE_DENGUE)
        },
        Rule("Rule #12").apply {
            addAntecedent(EqualsClause(RULE_VARIABLE_SYMPTOM_FEVER, "high"))
            addAntecedent(EqualsClause(RULE_VARIABLE_SYMPTOM_COUGH, "dry"))
            addAntecedent(EqualsClause(RULE_VARIABLE_SYMPTOM_LOSS_OF_TASTE_SMELL, "no"))
            consequent = EqualsClause(RULE_VARIABLE_DISEASE, RULE_VALUE_DISEASE_COVID)
        },
        Rule("Rule #13").apply {
            addAntecedent(EqualsClause(RULE_VARIABLE_SYMPTOM_FEVER, "high"))
            addAntecedent(EqualsClause(RULE_VARIABLE_SYMPTOM_HEADACHE, "intense"))
            addAntecedent(EqualsClause(RULE_VARIABLE_SYMPTOM_RASH, "yes"))
            addAntecedent(EqualsClause(RULE_VARIABLE_SYMPTOM_MUSCLE_PAIN, "mild"))
            consequent = EqualsClause(RULE_VARIABLE_DISEASE, RULE_VALUE_DISEASE_DENGUE)
        },
        Rule("Rule #14").apply {
            addAntecedent(EqualsClause(RULE_VARIABLE_SYMPTOM_FEVER, "high"))
            addAntecedent(EqualsClause(RULE_VARIABLE_SYMPTOM_COUGH, "wet"))
            addAntecedent(EqualsClause(RULE_VARIABLE_SYMPTOM_DIFFICULTY_BREATHING, "yes"))
            consequent = EqualsClause(RULE_VARIABLE_DISEASE, RULE_VALUE_DISEASE_COVID)
        },
        Rule("Rule #15").apply {
            addAntecedent(EqualsClause(RULE_VARIABLE_SYMPTOM_FEVER, "high"))
            addAntecedent(EqualsClause(RULE_VARIABLE_SYMPTOM_HEADACHE, "mild"))
            addAntecedent(EqualsClause(RULE_VARIABLE_SYMPTOM_RASH, "no"))
            addAntecedent(EqualsClause(RULE_VARIABLE_SYMPTOM_MUSCLE_PAIN, "mild"))
            consequent = EqualsClause(RULE_VARIABLE_DISEASE, RULE_VALUE_DISEASE_DENGUE)
        },
        Rule("Rule #16").apply {
            addAntecedent(EqualsClause(RULE_VARIABLE_SYMPTOM_FEVER, "low"))
            addAntecedent(EqualsClause(RULE_VARIABLE_SYMPTOM_COUGH, "dry"))
            addAntecedent(EqualsClause(RULE_VARIABLE_SYMPTOM_LOSS_OF_TASTE_SMELL, "yes"))
            consequent = EqualsClause(RULE_VARIABLE_DISEASE, RULE_VALUE_DISEASE_COVID)
        },
        Rule("Rule #17").apply {
            addAntecedent(EqualsClause(RULE_VARIABLE_SYMPTOM_FEVER, "low"))
            addAntecedent(EqualsClause(RULE_VARIABLE_SYMPTOM_HEADACHE, "mild"))
            addAntecedent(EqualsClause(RULE_VARIABLE_SYMPTOM_RASH, "no"))
            addAntecedent(EqualsClause(RULE_VARIABLE_SYMPTOM_MUSCLE_PAIN, "mild"))
            consequent = EqualsClause(RULE_VARIABLE_DISEASE, RULE_VALUE_DISEASE_DENGUE)
        },
        Rule("Rule #18").apply {
            addAntecedent(EqualsClause(RULE_VARIABLE_SYMPTOM_FEVER, "low"))
            addAntecedent(EqualsClause(RULE_VARIABLE_SYMPTOM_COUGH, "wet"))
            addAntecedent(EqualsClause(RULE_VARIABLE_SYMPTOM_DIFFICULTY_BREATHING, "yes"))
            consequent = EqualsClause(RULE_VARIABLE_DISEASE, RULE_VALUE_DISEASE_COVID)
        },
        Rule("Rule #19").apply {
            addAntecedent(EqualsClause(RULE_VARIABLE_SYMPTOM_FEVER, "low"))
            addAntecedent(EqualsClause(RULE_VARIABLE_SYMPTOM_HEADACHE, "mild"))
            addAntecedent(EqualsClause(RULE_VARIABLE_SYMPTOM_RASH, "no"))
            consequent = EqualsClause(RULE_VARIABLE_DISEASE, RULE_VALUE_DISEASE_DENGUE)
        },
        Rule("Rule #20").apply {
            addAntecedent(EqualsClause(RULE_VARIABLE_SYMPTOM_FEVER, "low"))
            addAntecedent(EqualsClause(RULE_VARIABLE_SYMPTOM_COUGH, "dry"))
            addAntecedent(EqualsClause(RULE_VARIABLE_SYMPTOM_LOSS_OF_TASTE_SMELL, "no"))
            consequent = EqualsClause(RULE_VARIABLE_DISEASE, RULE_VALUE_DISEASE_COVID)
        },
        Rule("Rule #21").apply {
            addAntecedent(EqualsClause(RULE_VARIABLE_SYMPTOM_FEVER, "low"))
            addAntecedent(EqualsClause(RULE_VARIABLE_SYMPTOM_HEADACHE, "intense"))
            addAntecedent(EqualsClause(RULE_VARIABLE_SYMPTOM_RASH, "yes"))
            consequent = EqualsClause(RULE_VARIABLE_DISEASE, RULE_VALUE_DISEASE_DENGUE)
        },
        Rule("Rule #22").apply {
            addAntecedent(EqualsClause(RULE_VARIABLE_SYMPTOM_FEVER, "low"))
            addAntecedent(EqualsClause(RULE_VARIABLE_SYMPTOM_COUGH, "wet"))
            addAntecedent(EqualsClause(RULE_VARIABLE_SYMPTOM_DIFFICULTY_BREATHING, "yes"))
            consequent = EqualsClause(RULE_VARIABLE_DISEASE, RULE_VALUE_DISEASE_COVID)
        },
        Rule("Rule #23").apply {
            addAntecedent(EqualsClause(RULE_VARIABLE_SYMPTOM_FEVER, "low"))
            addAntecedent(EqualsClause(RULE_VARIABLE_SYMPTOM_HEADACHE, "mild"))
            addAntecedent(EqualsClause(RULE_VARIABLE_SYMPTOM_RASH, "no"))
            consequent = EqualsClause(RULE_VARIABLE_DISEASE, RULE_VALUE_DISEASE_DENGUE)
        },
        Rule("Rule #24").apply {
            addAntecedent(EqualsClause(RULE_VARIABLE_SYMPTOM_FEVER, "low"))
            addAntecedent(EqualsClause(RULE_VARIABLE_SYMPTOM_COUGH, "dry"))
            addAntecedent(EqualsClause(RULE_VARIABLE_SYMPTOM_LOSS_OF_TASTE_SMELL, "no"))
            consequent = EqualsClause(RULE_VARIABLE_DISEASE, RULE_VALUE_DISEASE_COVID)
        },
        Rule("Rule #25").apply {
            addAntecedent(EqualsClause(RULE_VARIABLE_SYMPTOM_FEVER, "low"))
            addAntecedent(EqualsClause(RULE_VARIABLE_SYMPTOM_HEADACHE, "intense"))
            addAntecedent(EqualsClause(RULE_VARIABLE_SYMPTOM_RASH, "yes"))
            addAntecedent(EqualsClause(RULE_VARIABLE_SYMPTOM_MUSCLE_PAIN, "mild"))
            consequent = EqualsClause(RULE_VARIABLE_DISEASE, RULE_VALUE_DISEASE_DENGUE)
        },
        Rule("Rule #26").apply {
            addAntecedent(EqualsClause(RULE_VARIABLE_SYMPTOM_FEVER, "low"))
            addAntecedent(EqualsClause(RULE_VARIABLE_SYMPTOM_COUGH, "wet"))
            addAntecedent(EqualsClause(RULE_VARIABLE_SYMPTOM_DIFFICULTY_BREATHING, "no"))
            consequent = EqualsClause(RULE_VARIABLE_DISEASE, RULE_VALUE_DISEASE_COVID)
        },
        Rule("Rule #27").apply {
            addAntecedent(EqualsClause(RULE_VARIABLE_SYMPTOM_FEVER, "low"))
            addAntecedent(EqualsClause(RULE_VARIABLE_SYMPTOM_HEADACHE, "intense"))
            addAntecedent(EqualsClause(RULE_VARIABLE_SYMPTOM_RASH, "yes"))
            addAntecedent(EqualsClause(RULE_VARIABLE_SYMPTOM_MUSCLE_PAIN, "intense"))
            consequent = EqualsClause(RULE_VARIABLE_DISEASE, RULE_VALUE_DISEASE_DENGUE)
        },
        Rule("Rule #28").apply {
            addAntecedent(EqualsClause(RULE_VARIABLE_SYMPTOM_FEVER, "low"))
            addAntecedent(EqualsClause(RULE_VARIABLE_SYMPTOM_COUGH, "wet"))
            addAntecedent(EqualsClause(RULE_VARIABLE_SYMPTOM_DIFFICULTY_BREATHING, "no"))
            consequent = EqualsClause(RULE_VARIABLE_DISEASE, RULE_VALUE_DISEASE_COVID)
        },
        Rule("Rule #29").apply {
            addAntecedent(EqualsClause(RULE_VARIABLE_SYMPTOM_FEVER, "low"))
            addAntecedent(EqualsClause(RULE_VARIABLE_SYMPTOM_HEADACHE, "mild"))
            addAntecedent(EqualsClause(RULE_VARIABLE_SYMPTOM_RASH, "no"))
            addAntecedent(EqualsClause(RULE_VARIABLE_SYMPTOM_MUSCLE_PAIN, "mild"))
            consequent = EqualsClause(RULE_VARIABLE_DISEASE, RULE_VALUE_DISEASE_DENGUE)
        },
        Rule("Rule #30").apply {
            addAntecedent(EqualsClause(RULE_VARIABLE_SYMPTOM_FEVER, "low"))
            addAntecedent(EqualsClause(RULE_VARIABLE_SYMPTOM_COUGH, "dry"))
            addAntecedent(EqualsClause(RULE_VARIABLE_SYMPTOM_LOSS_OF_TASTE_SMELL, "no"))
            consequent = EqualsClause(RULE_VARIABLE_DISEASE, RULE_VALUE_DISEASE_COVID)
        }
    )
}