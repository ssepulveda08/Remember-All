package com.ssepulveda.rememberall.db.entity

import androidx.room.*

@Entity(tableName = "list_app" )
data class ListApp(
    @PrimaryKey(autoGenerate = true) val id: Long,
    val name: String = "",
    val color: String = ""
)

data class ListAppCount(
    val id: Long,
    val name: String = "",
    val color: String = "",
    val count: Int = 0
)

@Entity(
    tableName = "item_list", foreignKeys = [ForeignKey(
        entity = ListApp::class,
        parentColumns = arrayOf("id"),
        childColumns = arrayOf("listId"),
        onDelete = ForeignKey.CASCADE
    )]
)
data class ItemList(
    @PrimaryKey(autoGenerate = true) val itemId: Long,
    val listId: Long,
    var text: String
)