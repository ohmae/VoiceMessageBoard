/*
 * Copyright (c) 2018 大前良介 (OHMAE Ryosuke)
 *
 * This software is released under the MIT License.
 * http://opensource.org/licenses/MIT
 */

package net.mm2d.android.vmb.util

import android.content.Context
import androidx.annotation.AttrRes
import androidx.annotation.ColorInt
import androidx.annotation.StyleRes

/**
 * @author [大前良介 (OHMAE Ryosuke)](mailto:ryo@mm2d.net)
 */
object AttrUtils {
    @ColorInt
    fun resolveColor(context: Context, @AttrRes attr: Int, @ColorInt defaultColor: Int): Int {
        return resolveColor(context, 0, attr, defaultColor)
    }

    @ColorInt
    private fun resolveColor(context: Context, @StyleRes style: Int, @AttrRes attr: Int, @ColorInt defaultColor: Int): Int {
        val a = context.obtainStyledAttributes(style, intArrayOf(attr))
        try {
            return a.getColor(0, defaultColor)
        } finally {
            a.recycle()
        }
    }
}
