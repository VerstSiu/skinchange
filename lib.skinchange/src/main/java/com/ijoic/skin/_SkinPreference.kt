package com.ijoic.skin

import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

/**
 * Skin preference extension.
 *
 * @author verstsiu on 2018/5/21.
 * @version 2.0
 */

/**
 * Bind string property.
 *
 * @param key key.
 * @param defValue default value.
 */
internal fun SkinPreference.bindString(key: String, defValue: String? = null) = object: ReadWriteProperty<SkinPreference, String?> {

  override fun getValue(thisRef: SkinPreference, property: KProperty<*>): String? {
    return thisRef.innerPrefs.getString(key, defValue)
  }

  override fun setValue(thisRef: SkinPreference, property: KProperty<*>, value: String?) {
    thisRef.innerPrefs.edit()
        .putString(key, value)
        .apply()
  }
}

/**
 * Bind boolean property.
 *
 * @param key key.
 * @param defValue default value.
 */
internal fun SkinPreference.bindBoolean(key: String, defValue: Boolean = false) = object: ReadWriteProperty<SkinPreference, Boolean> {

  override fun getValue(thisRef: SkinPreference, property: KProperty<*>): Boolean {
    return thisRef.innerPrefs.getBoolean(key, defValue)
  }

  override fun setValue(thisRef: SkinPreference, property: KProperty<*>, value: Boolean) {
    thisRef.innerPrefs.edit()
        .putBoolean(key, value)
        .apply()
  }
}