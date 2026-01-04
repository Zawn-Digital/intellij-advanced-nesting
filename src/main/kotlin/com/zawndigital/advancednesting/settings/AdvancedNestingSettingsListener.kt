package com.zawndigital.advancednesting.settings

import com.intellij.util.messages.Topic

/**
 * Listener interface for Advanced Nesting settings changes.
 *
 * Used to trigger project tree refresh when settings are modified.
 */
interface AdvancedNestingSettingsListener {

    fun settingsChanged()

    companion object {
        @JvmStatic
        val TOPIC = Topic.create(
            "Advanced Nesting Settings Changed",
            AdvancedNestingSettingsListener::class.java
        )
    }
}
