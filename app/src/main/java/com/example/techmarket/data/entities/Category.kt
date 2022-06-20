package com.example.techmarket.data.entities

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import java.io.Serializable
enum class Category(val category: String)  {
    Uncategory("Uncategory"),

    Smartphones("Smartphones"),
    Tablets("Tablets"),
    Phototechnics("Phototechnics"),

    Computers("Computers"),
    Processors("Processors"),
    GraphicsCards("Graphics cards"),

    Appliances("Appliances"),
    WashingMachines("Washing machines"),
    Irons("Irons"),
    Hairdryer("Hairdryer"),

    OfficeEquipment("Office equipment"),
    Printers("Printers")
}