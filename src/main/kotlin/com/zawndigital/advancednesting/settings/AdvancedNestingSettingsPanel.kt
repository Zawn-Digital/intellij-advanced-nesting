package com.zawndigital.advancednesting.settings

import com.intellij.ui.components.JBCheckBox
import com.intellij.ui.components.JBList
import com.intellij.ui.components.JBScrollPane
import com.intellij.ui.components.JBTextField
import com.intellij.util.ui.FormBuilder
import com.intellij.util.ui.JBUI
import java.awt.BorderLayout
import javax.swing.*

/**
 * UI panel for Advanced Nesting settings.
 *
 * Allows users to configure which file extensions support directory nesting.
 */
class AdvancedNestingSettingsPanel {

    private val enabledCheckBox = JBCheckBox("Enable Advanced Nesting")
    private val extensionListModel = DefaultListModel<String>()
    private val extensionList = JBList(extensionListModel)
    private val addExtensionField = JBTextField()
    private val addButton = JButton("Add")
    private val removeButton = JButton("Remove")

    var isEnabled: Boolean
        get() = enabledCheckBox.isSelected
        set(value) {
            enabledCheckBox.isSelected = value
        }

    var enabledExtensions: List<String>
        get() = extensionListModel.elements().toList()
        set(value) {
            extensionListModel.clear()
            value.forEach { extensionListModel.addElement(it) }
        }

    fun createPanel(): JPanel {
        setupListeners()

        val listPanel = JPanel(BorderLayout()).apply {
            border = JBUI.Borders.empty(5)
            add(JBScrollPane(extensionList), BorderLayout.CENTER)

            val buttonPanel = JPanel().apply {
                layout = BoxLayout(this, BoxLayout.Y_AXIS)
                add(removeButton)
                add(Box.createVerticalStrut(5))
            }
            add(buttonPanel, BorderLayout.EAST)
        }

        val addPanel = JPanel(BorderLayout()).apply {
            border = JBUI.Borders.empty(5, 0, 0, 0)
            add(JLabel("File extension (without dot):"), BorderLayout.WEST)
            add(addExtensionField, BorderLayout.CENTER)
            add(addButton, BorderLayout.EAST)
        }

        return FormBuilder.createFormBuilder()
            .addComponent(enabledCheckBox)
            .addSeparator()
            .addLabeledComponent("Enabled extensions:", listPanel, true)
            .addComponentToRightColumn(addPanel)
            .addComponentFillVertically(JPanel(), 0)
            .panel
    }

    private fun setupListeners() {
        addButton.addActionListener {
            val extension = addExtensionField.text.trim().lowercase()
            if (extension.isNotEmpty() && !extensionListModel.contains(extension)) {
                extensionListModel.addElement(extension)
                addExtensionField.text = ""
            }
        }

        removeButton.addActionListener {
            extensionList.selectedValue?.let { selected ->
                extensionListModel.removeElement(selected)
            }
        }

        addExtensionField.addActionListener {
            addButton.doClick()
        }
    }
}
