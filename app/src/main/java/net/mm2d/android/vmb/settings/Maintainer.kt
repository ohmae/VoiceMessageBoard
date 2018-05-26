/*
 * Copyright (c) 2017 大前良介 (OHMAE Ryosuke)
 *
 * This software is released under the MIT License.
 * http://opensource.org/licenses/MIT
 */

package net.mm2d.android.vmb.settings

import android.content.pm.ActivityInfo
import android.graphics.Color

/**
 * 設定値のメンテナー。
 *
 *
 * アプリ設定のバージョンを付与し、
 * 元に設定値のマイグレーション処理や初期値の書き込みを行う。
 *
 *
 * すでに使用しなくなった設定値にアクセスするため、
 * `@Deprecated`指定をしたOldKeysに唯一アクセスしてもよいクラス。
 *
 * @author [大前良介 (OHMAE Ryosuke)](mailto:ryo@mm2d.net)
 */
internal object Maintainer {
    /**
     * SharedPreferencesのバージョン。
     *
     * <table>
     * <tr><th>SETTINGS_VERSION</th><th>VersionName</th></tr>
     * <tr><td>0</td><td>1.2.4-</td></tr>
     * </table>
     */
    private const val SETTINGS_VERSION = 0

    /**
     * 起動時に一度だけ呼び出され、SharedPreferencesのメンテナンスを行う。
     *
     * @param storage SettingsStorage
     */
    fun maintain(storage: SettingsStorage) {
        val currentVersion = getSettingsVersion(storage)
        if (currentVersion == SETTINGS_VERSION) {
            return
        }
        storage.writeInt(Key.SETTINGS_VERSION, SETTINGS_VERSION)
        writeDefaultValue(storage, false)
    }

    /**
     * デフォルト値の書き込みを行う
     *
     * @param storage SettingsStorage
     */
    private fun writeDefaultValue(storage: SettingsStorage, overwrite: Boolean) {
        storage.writeInt(Key.KEY_BACKGROUND, Color.WHITE, overwrite)
        storage.writeInt(Key.KEY_FOREGROUND, Color.BLACK, overwrite)

        storage.writeBoolean(Key.SPEECH_RECOGNIZER, true, overwrite)
        storage.writeBoolean(Key.CANDIDATE_LIST, false, overwrite)
        // HISTORY
        storage.writeBoolean(Key.LIST_EDIT, false, overwrite)
        storage.writeBoolean(Key.LONG_TAP_EDIT, false, overwrite)

        storage.writeString(Key.SCREEN_ORIENTATION, Integer.toString(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED), overwrite)
        storage.writeBoolean(Key.USE_FONT, false, overwrite)
        // FONT_PATH
    }

    /**
     * SharedPreferencesのバージョンを取得する。
     *
     * @param storage SettingsStorage
     * @return バージョン
     */
    private fun getSettingsVersion(storage: SettingsStorage): Int =
            storage.readInt(Key.SETTINGS_VERSION, -1)
}
