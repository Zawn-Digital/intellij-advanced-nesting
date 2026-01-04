package com.zawndigital.advancednesting.settings

import com.intellij.openapi.application.ApplicationManager
import com.intellij.openapi.components.PersistentStateComponent
import com.intellij.openapi.components.State
import com.intellij.openapi.components.Storage
import com.intellij.util.xmlb.XmlSerializerUtil

/**
 * Persistent settings for Advanced Nesting plugin.
 *
 * Stores which file extensions should support directory nesting.
 */
@State(
    name = "AdvancedNestingSettings",
    storages = [Storage("advancedNesting.xml")]
)
class AdvancedNestingSettings : PersistentStateComponent<AdvancedNestingSettings> {

    /**
     * File extensions that support directory nesting (without the dot).
     * For example: ["php", "js", "ts"]
     */
    var enabledExtensions: MutableList<String> = mutableListOf("php")

    /**
     * Whether the plugin is enabled globally.
     */
    var isEnabled: Boolean = true

    override fun getState(): AdvancedNestingSettings = this

    override fun loadState(state: AdvancedNestingSettings) {
        XmlSerializerUtil.copyBean(state, this)
    }

    companion object {
        val instance: AdvancedNestingSettings
            get() = ApplicationManager.getApplication().getService(AdvancedNestingSettings::class.java)
    }
}
