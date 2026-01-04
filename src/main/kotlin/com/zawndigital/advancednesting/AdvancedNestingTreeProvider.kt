package com.zawndigital.advancednesting

import com.intellij.ide.projectView.TreeStructureProvider
import com.intellij.ide.projectView.ViewSettings
import com.intellij.ide.projectView.impl.nodes.PsiDirectoryNode
import com.intellij.ide.projectView.impl.nodes.PsiFileNode
import com.intellij.ide.util.treeView.AbstractTreeNode
import com.zawndigital.advancednesting.settings.AdvancedNestingSettings

/**
 * Modifies the project tree structure to nest directories under files with matching names.
 *
 * For example, if both `User.php` and `User/` directory exist as siblings,
 * the directory will be visually nested under the file in the project tree.
 *
 * Matching is case-insensitive: `User.php` will match `User/`, `user/`, or `USER/`.
 *
 * Supports multiple file extensions configured via Settings → Editor → Advanced Nesting.
 */
class AdvancedNestingTreeProvider : TreeStructureProvider {

    override fun modify(
        parent: AbstractTreeNode<*>,
        children: Collection<AbstractTreeNode<*>>,
        settings: ViewSettings?
    ): Collection<AbstractTreeNode<*>> {
        val pluginSettings = AdvancedNestingSettings.instance

        // Early exit: if plugin is disabled or no children
        if (!pluginSettings.isEnabled || children.isEmpty()) return children

        val enabledExtensions = pluginSettings.enabledExtensions.map { it.lowercase() }.toSet()

        // Early exit: if no extensions configured
        if (enabledExtensions.isEmpty()) return children

        val matchingFiles = mutableMapOf<String, PsiFileNode>()
        val directories = mutableMapOf<String, PsiDirectoryNode>()

        // First pass: categorize children by type and name
        children.forEach { child ->
            when (child) {
                is PsiFileNode -> {
                    val file = child.virtualFile
                    val extension = file?.extension?.lowercase()
                    if (file != null && extension in enabledExtensions) {
                        matchingFiles[file.nameWithoutExtension.lowercase()] = child
                    }
                }
                is PsiDirectoryNode -> {
                    child.value?.name?.lowercase()?.let { dirName ->
                        directories[dirName] = child
                    }
                }
            }
        }

        // Early exit: if no matching files or no directories, no nesting is possible
        if (matchingFiles.isEmpty() || directories.isEmpty()) return children

        // Identify which directories have matching files
        val matchedDirectories = matchingFiles.keys.intersect(directories.keys)

        // Early exit: if no matches, return original children unchanged
        if (matchedDirectories.isEmpty()) return children

        // Build result list: process all children, creating grouped nodes for matches
        val result = mutableListOf<AbstractTreeNode<*>>()

        children.forEach { child ->
            when (child) {
                is PsiFileNode -> {
                    val file = child.virtualFile
                    val extension = file?.extension?.lowercase()
                    if (file != null && extension in enabledExtensions) {
                        val baseName = file.nameWithoutExtension.lowercase()
                        val matchingDir = directories[baseName]

                        if (matchingDir != null) {
                            // Create grouped node: file with directory's children
                            result.add(NestingGroupNode(child, matchingDir, settings))
                        } else {
                            // No matching directory, add file as-is
                            result.add(child)
                        }
                    } else {
                        // Non-matching extension file, add as-is
                        result.add(child)
                    }
                }
                is PsiDirectoryNode -> {
                    val dirName = child.value?.name?.lowercase()
                    // Skip directories that have a matching file (they're nested under the file)
                    if (dirName == null || !matchedDirectories.contains(dirName)) {
                        result.add(child)
                    }
                    // Matched directories are hidden (nested under their corresponding file)
                }
                else -> {
                    // Other node types, add as-is
                    result.add(child)
                }
            }
        }

        return result
    }
}
