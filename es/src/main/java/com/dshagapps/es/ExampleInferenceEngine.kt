package com.dshagapps.es

import com.github.cschen1205.ess.engine.Clause
import com.github.cschen1205.ess.engine.EqualsClause
import com.github.cschen1205.ess.engine.KieRuleInferenceEngine
import com.github.cschen1205.ess.engine.LessClause
import com.github.cschen1205.ess.engine.Rule
import java.util.Vector


object ExampleInferenceEngine {
    private var engine = KieRuleInferenceEngine().apply {
        ExampleInferenceEngineRules.getRules().forEach { rule ->
            this.addRule(rule)
        }
    }

    fun testForwardChain() {
        engine.addFact(EqualsClause(ExampleInferenceEngineRules.RULE_VARIABLE_NUM_WHEELS, "4"))
        engine.addFact(EqualsClause(ExampleInferenceEngineRules.RULE_VARIABLE_MOTOR, "yes"))
        engine.addFact(EqualsClause(ExampleInferenceEngineRules.RULE_VARIABLE_NUM_DOORS, "3"))
        engine.addFact(EqualsClause(ExampleInferenceEngineRules.RULE_VARIABLE_SIZE, "medium"))
        println("before inference")
        println(engine.facts)
        engine.infer() //forward chain
        println("after inference")
        println(engine.facts)
        restartEngine()
    }

    fun testBackwardChain() {
        engine.addFact(EqualsClause(ExampleInferenceEngineRules.RULE_VARIABLE_NUM_WHEELS, "4"))
        engine.addFact(EqualsClause(ExampleInferenceEngineRules.RULE_VARIABLE_MOTOR, "yes"))
        engine.addFact(EqualsClause(ExampleInferenceEngineRules.RULE_VARIABLE_NUM_DOORS, "3"))
        engine.addFact(EqualsClause(ExampleInferenceEngineRules.RULE_VARIABLE_SIZE, "medium"))
        println("Infer: ${ExampleInferenceEngineRules.RULE_VARIABLE_VEHICLE}")
        val unprovedConditions: Vector<Clause> = Vector()
        val conclusion =
            engine.infer(ExampleInferenceEngineRules.RULE_VARIABLE_VEHICLE, unprovedConditions)
        println("Conclusion: $conclusion")
        restartEngine()
    }

    private fun restartEngine() {
        engine = KieRuleInferenceEngine().apply {
            ExampleInferenceEngineRules.getRules().forEach { rule ->
                this.addRule(rule)
            }
        }
    }
}

private object ExampleInferenceEngineRules {
    const val RULE_VARIABLE_VEHICLE_TYPE = "vehicleType"
    const val RULE_VARIABLE_NUM_WHEELS = "numWheels"
    const val RULE_VARIABLE_NUM_DOORS = "numDoors"
    const val RULE_VARIABLE_MOTOR = "motor"
    const val RULE_VARIABLE_SIZE = "size"
    const val RULE_VARIABLE_VEHICLE = "vehicle"

    const val RULE_VALUE_VEHICLE_TYPE_CYCLE = "cycle"
    const val RULE_VALUE_VEHICLE_TYPE_AUTOMOBILE = "automobile"

    const val RULE_VALUE_VEHICLE_BICYCLE = "Bicycle"
    const val RULE_VALUE_VEHICLE_TRICYCLE = "Tricycle"
    const val RULE_VALUE_VEHICLE_MOTORCYCLE = "Motorcycle"
    const val RULE_VALUE_VEHICLE_SPORTS_CAR = "Sports_Car"
    const val RULE_VALUE_VEHICLE_SEDAN = "Sedan"
    const val RULE_VALUE_VEHICLE_MINI_VAN = "Mini_Van"
    const val RULE_VALUE_VEHICLE_SUV = "SUV"

    fun getRules(): List<Rule> = listOf(
        Rule(RULE_VALUE_VEHICLE_BICYCLE).apply {
            addAntecedent(EqualsClause(RULE_VARIABLE_VEHICLE_TYPE, RULE_VALUE_VEHICLE_TYPE_CYCLE))
            addAntecedent(EqualsClause(RULE_VARIABLE_NUM_WHEELS, "2"))
            addAntecedent(EqualsClause(RULE_VARIABLE_MOTOR, "no"))
            consequent = EqualsClause(RULE_VARIABLE_VEHICLE, RULE_VALUE_VEHICLE_BICYCLE)
        },
        Rule(RULE_VALUE_VEHICLE_TRICYCLE).apply {
            addAntecedent(EqualsClause(RULE_VARIABLE_VEHICLE_TYPE, RULE_VALUE_VEHICLE_TYPE_CYCLE))
            addAntecedent(EqualsClause(RULE_VARIABLE_NUM_WHEELS, "3"))
            addAntecedent(EqualsClause(RULE_VARIABLE_MOTOR, "no"))
            consequent = EqualsClause(RULE_VARIABLE_VEHICLE, RULE_VALUE_VEHICLE_TRICYCLE)
        },
        Rule(RULE_VALUE_VEHICLE_MOTORCYCLE).apply {
            addAntecedent(EqualsClause(RULE_VARIABLE_VEHICLE_TYPE, RULE_VALUE_VEHICLE_TYPE_CYCLE))
            addAntecedent(EqualsClause(RULE_VARIABLE_NUM_WHEELS, "2"))
            addAntecedent(EqualsClause(RULE_VARIABLE_MOTOR, "yes"))
            consequent = EqualsClause(RULE_VARIABLE_VEHICLE, RULE_VALUE_VEHICLE_MOTORCYCLE)
        },
        Rule(RULE_VALUE_VEHICLE_SPORTS_CAR).apply {
            addAntecedent(
                EqualsClause(
                    RULE_VARIABLE_VEHICLE_TYPE,
                    RULE_VALUE_VEHICLE_TYPE_AUTOMOBILE
                )
            )
            addAntecedent(EqualsClause(RULE_VARIABLE_SIZE, "medium"))
            addAntecedent(EqualsClause(RULE_VARIABLE_NUM_DOORS, "2"))
            consequent = EqualsClause(RULE_VARIABLE_VEHICLE, RULE_VALUE_VEHICLE_SPORTS_CAR)
        },
        Rule(RULE_VALUE_VEHICLE_SEDAN).apply {
            addAntecedent(
                EqualsClause(
                    RULE_VARIABLE_VEHICLE_TYPE,
                    RULE_VALUE_VEHICLE_TYPE_AUTOMOBILE
                )
            )
            addAntecedent(EqualsClause(RULE_VARIABLE_SIZE, "medium"))
            addAntecedent(EqualsClause(RULE_VARIABLE_NUM_DOORS, "4"))
            consequent = EqualsClause(RULE_VARIABLE_VEHICLE, RULE_VALUE_VEHICLE_SEDAN)
        },
        Rule(RULE_VALUE_VEHICLE_MINI_VAN).apply {
            addAntecedent(
                EqualsClause(
                    RULE_VARIABLE_VEHICLE_TYPE,
                    RULE_VALUE_VEHICLE_TYPE_AUTOMOBILE
                )
            )
            addAntecedent(EqualsClause(RULE_VARIABLE_SIZE, "medium"))
            addAntecedent(EqualsClause(RULE_VARIABLE_NUM_DOORS, "3"))
            consequent = EqualsClause(RULE_VARIABLE_VEHICLE, RULE_VALUE_VEHICLE_MINI_VAN)
        },
        Rule(RULE_VALUE_VEHICLE_SUV).apply {
            addAntecedent(
                EqualsClause(
                    RULE_VARIABLE_VEHICLE_TYPE,
                    RULE_VALUE_VEHICLE_TYPE_AUTOMOBILE
                )
            )
            addAntecedent(EqualsClause(RULE_VARIABLE_SIZE, "large"))
            addAntecedent(EqualsClause(RULE_VARIABLE_NUM_DOORS, "4"))
            consequent = EqualsClause(RULE_VARIABLE_VEHICLE, RULE_VALUE_VEHICLE_SUV)
        },
        Rule(RULE_VALUE_VEHICLE_TYPE_CYCLE).apply {
            addAntecedent(LessClause(RULE_VARIABLE_NUM_WHEELS, "4"))
            consequent = EqualsClause(RULE_VARIABLE_VEHICLE_TYPE, RULE_VALUE_VEHICLE_TYPE_CYCLE)
        },
        Rule(RULE_VALUE_VEHICLE_TYPE_AUTOMOBILE).apply {
            addAntecedent(EqualsClause(RULE_VARIABLE_NUM_WHEELS, "4"))
            addAntecedent(EqualsClause(RULE_VARIABLE_MOTOR, "yes"))
            consequent =
                EqualsClause(RULE_VARIABLE_VEHICLE_TYPE, RULE_VALUE_VEHICLE_TYPE_AUTOMOBILE)
        }
    )
}
