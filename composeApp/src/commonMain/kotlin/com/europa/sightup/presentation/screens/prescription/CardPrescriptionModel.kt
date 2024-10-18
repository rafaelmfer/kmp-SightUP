package com.europa.sightup.presentation.screens.prescription

data class CardPrescriptionModel(
    val title: String,
    val badge: String,
    val visionTest: String,
    val prescriptionDate: String,
    val expiration: String,
    val manufacture: String,
    val productName: String,
    val replacement: String,
    val subTitleMessage: String,
    val visualAcuity: VisualAcuity,
    val astigmatism: Astigmatism,
    val sph: Sph,
    val axis: Axis,
    val cyl: Cyl,
    val va: Va,
    val pd: Pd,
    val add: Add,
    val prism: Prism,
    val base: Base,
    val dia: Dia,
    val note: Array<String>
)

data class VisualAcuity(
    val left: String,
    val right: String
)

data class Astigmatism(
    val left: String,
    val right: String
)

data class Sph(
    val left: String,
    val right: String
)

data class Axis(
    val left: String,
    val right: String
)

data class Cyl(
    val left: String,
    val right: String
)

data class Va(
    val left: String,
    val right: String
)

data class Pd(
    val left: String,
    val right: String
)

data class Add(
    val left: String,
    val right: String
)

data class Prism(
    val left: String,
    val right: String
)

data class Base(
    val left: String,
    val right: String
)

data class Dia(
    val left: String,
    val right: String
)