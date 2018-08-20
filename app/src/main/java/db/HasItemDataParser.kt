package db

import entity.HasItemData
import org.jetbrains.anko.db.MapRowParser

class HasItemDataParser: MapRowParser<HasItemData> {
    override fun parseRow(columns: Map<String, Any?>): HasItemData {
        return HasItemData(columns["number"] as String, columns["has"] as Int)
    }
}