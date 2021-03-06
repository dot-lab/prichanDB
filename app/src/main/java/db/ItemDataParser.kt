package db

import entity.ItemData
import org.jetbrains.anko.db.MapRowParser

class ItemDataParser: MapRowParser<ItemData> {
    override fun parseRow(columns: Map<String, Any?>): ItemData {
        return ItemData(
                columns["_id"] as Long,
                columns["number"] as String,
                columns["category"] as String,
                columns["name"] as String,
                columns["bland"] as String,
                columns["reality"] as Long,
                columns["color"] as String,
                columns["type"] as String,
                columns["iine"] as Long,
                columns["has"] as Long
        )
    }
}