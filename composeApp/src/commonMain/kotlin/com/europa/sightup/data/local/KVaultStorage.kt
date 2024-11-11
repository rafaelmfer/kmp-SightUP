package com.europa.sightup.data.local

import com.liftric.kvault.KVault
import kotlinx.serialization.json.Json

@Deprecated(
    "Use koin injection instead",
    ReplaceWith(
        expression = "val kVaultStorage: KVaultStorage by inject() OR val kVaultStorage: KVaultStorage = koinInject()",
        imports = arrayOf(
            "import org.koin.android.ext.android.inject",
            "import org.koin.compose.koinInject"
        )
    )
)
expect fun getKVaultStorage(context: Any? = null): KVaultStorage

interface KVaultStorage {
    val kVault: KVault

    fun <Type> set(key: String, value: Type) {
        when (value) {
            is String -> kVault.set(key, value)
            is Int -> kVault.set(key, value)
            is Boolean -> kVault.set(key, value)
            is Float -> kVault.set(key, value)
            is Long -> kVault.set(key, value)
            is Double -> kVault.set(key, value)
            is ByteArray -> kVault.set(key, value)
            else -> throw IllegalArgumentException("Unsupported type, try to serialize the object or use another type")
        }
    }

    fun remove(key: String) {
        kVault.deleteObject(key)
    }

    fun clear() {
        kVault.clear()
    }

    fun get(key: String): String {
        return kVault.string(key) ?: ""
    }

}

inline fun <reified Type : Any> KVaultStorage.getObject(key: String): Type? {
    val stringObject = kVault.string(key)

    return stringObject?.let {
        try {
            Json.decodeFromString<Type>(it)
        } catch (e: Exception) {
            null
        }
    }
}

inline fun <reified Type> KVaultStorage.get(key: String, defaultValue: Type): Type {
    return when (Type::class) {
        String::class -> (kVault.string(key) ?: defaultValue) as Type
        Int::class -> (kVault.int(key) ?: defaultValue) as Type
        Float::class -> (kVault.float(key) ?: defaultValue) as Type
        Boolean::class -> (kVault.bool(key) ?: defaultValue) as Type
        Long::class -> (kVault.long(key) ?: defaultValue) as Type
        Double::class -> (kVault.double(key) ?: defaultValue) as Type
        ByteArray::class -> (kVault.data(key) ?: defaultValue) as Type
        else -> {
            val stringObject = kVault.string(key)
            stringObject?.let {
                try {
                    Json.decodeFromString<Type>(it)
                } catch (e: Exception) {
                    defaultValue
                }
            } ?: defaultValue
        }
    }
}