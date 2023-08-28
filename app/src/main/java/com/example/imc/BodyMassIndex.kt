package com.example.imc

import android.os.Parcel
import android.os.Parcelable
import kotlin.math.pow

enum class BodyMassIndexType(s: String) {
    None("None"),
    SevereUnderweight("Magreza grave"),
    ModerateUnderweight("Magreza moderada"),
    Underweight("Magreza leve"),
    Normal("Saudável"),
    Overweight("Sobrepeso"),
    Obesity("Obesidade"),
    ClassI("Obesidade grau I"),
    ClassII("Obesidade grau II"),
}

class BodyMassIndex(var name: String?, var weight: Double, var height: Double, var imc: Double, var type: BodyMassIndexType): Parcelable {
    constructor(name: String, weight: Double, height: Double) : this(name, weight, height, 0.0, BodyMassIndexType.None)
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readDouble(),
        parcel.readDouble(),
        parcel.readDouble(),
        parcel.readSerializable() as BodyMassIndexType
    )

    fun calculate() {
        imc = weight / (height / 100).pow(2)

        type = when (imc.toInt()) {
            in 0 ..16  -> BodyMassIndexType.SevereUnderweight
            in 16 ..17 -> BodyMassIndexType.ModerateUnderweight
            in 17 ..19 -> BodyMassIndexType.Underweight
            in 19 ..25 ->BodyMassIndexType.Normal
            in 25 ..30 -> BodyMassIndexType.Overweight
            in 30 ..35 -> BodyMassIndexType.Obesity
            in 35 ..40 -> BodyMassIndexType.ClassI
            else -> BodyMassIndexType.ClassII
        }
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(name)
        parcel.writeDouble(weight)
        parcel.writeDouble(height)
        parcel.writeDouble(imc)
        parcel.writeSerializable(type)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<BodyMassIndex> {
        override fun createFromParcel(parcel: Parcel): BodyMassIndex {
            return BodyMassIndex(parcel)
        }

        override fun newArray(size: Int): Array<BodyMassIndex?> {
            return arrayOfNulls(size)
        }
    }
}