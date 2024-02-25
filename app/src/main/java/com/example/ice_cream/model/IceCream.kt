package com.example.ice_cream.model

import android.os.Parcel
import android.os.Parcelable
import com.example.ice_cream.enums.Status
import java.io.Serializable

class IceCream (
    val id: Long? = null,
    val name: String? = null,
    val status: Status? = null,
    val imageUrl: String? = null
): Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readValue(Long::class.java.classLoader) as? Long,
        parcel.readString(),
        parcel.readSerializable() as? Status,
        parcel.readString()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeValue(id)
        parcel.writeString(name)
        parcel.writeSerializable(status)
        parcel.writeString(imageUrl)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<IceCream> {
        override fun createFromParcel(parcel: Parcel): IceCream {
            return IceCream(parcel)
        }

        override fun newArray(size: Int): Array<IceCream?> {
            return arrayOfNulls(size)
        }
    }
}