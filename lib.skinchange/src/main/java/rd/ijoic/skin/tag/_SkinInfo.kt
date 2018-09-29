package rd.ijoic.skin.tag

import rd.ijoic.skin.SkinPreference

/**
 * Returns final display style.
 *
 * @param prefs skin preference.
 */
internal fun SkinInfo.getFinalStyle(prefs: SkinPreference): String? {
  return displayStyle ?: displayGroup?.let { prefs.getStyle(it) }
}