# File & Directory Nesting

A JetBrains IDE plugin that nests directories under files with matching names in the project tree.

![Version](https://img.shields.io/badge/version-1.3.0-blue)
![License](https://img.shields.io/badge/license-Apache%202.0-green)
![Platform](https://img.shields.io/badge/platform-JetBrains-orange)

## What It Does

Organizes your project tree with Rails/Laravel-style concern patterns by nesting directories under their corresponding files:

```
Before:                          After:
├── User.php                    ├── User.php
├── User/                       │   └── User/
│   ├── HasRoles.php           │       ├── HasRoles.php
│   ├── HasPermissions.php     │       └── HasPermissions.php
│   └── Auditable.php          └── Order.php
├── Order.php                       └── Order/
└── Order/                              ├── Billable.php
    ├── Billable.php                    └── Shippable.php
    └── Shippable.php
```

The `User/` and `Order/` directories collapse under their matching files for a cleaner, more organized tree.

## Installation

### From JetBrains Marketplace (Recommended)

1. Open your IDE (PhpStorm, RubyMine, WebStorm, etc.)
2. Go to **Settings → Plugins → Marketplace**
3. Search for **"File & Directory Nesting"**
4. Click **Install**

### Manual Installation

1. Download the [latest release](https://github.com/Zawn-Digital/phpstorm-advanced-nesting/releases/latest)
2. Go to **Settings → Plugins → ⚙️ → Install Plugin from Disk**
3. Select the downloaded ZIP file

## Features

### Works Out of the Box

No configuration needed! The plugin automatically works for these file types:

- **PHP** - Laravel traits, concerns
- **Ruby** - Rails concerns
- **Vue** - Component folders
- **JavaScript** - React/Node component folders
- **TypeScript** - TypeScript component folders
- **JSX/TSX** - React components
- **Python** - Module folders
- **Go** - Package folders

### Configurable

Customize which file extensions trigger nesting:

**Settings → Editor → General → File & Directory Nesting**

- Add or remove file extensions
- Enable/disable the plugin globally
- Case-insensitive matching

## Compatibility

Works with all JetBrains IDEs:

- PhpStorm
- RubyMine
- WebStorm
- IntelliJ IDEA
- PyCharm
- GoLand
- And more

Requires **2023.3+** or later.

## How It Works

The plugin uses IntelliJ's `TreeStructureProvider` API to modify the project tree structure:

1. Detects files with enabled extensions (e.g., `User.php`)
2. Looks for matching directories (e.g., `User/`)
3. Nests the directory under the file in the tree view
4. No files are moved - it's purely visual organization

**Important:** This is different from IntelliJ's built-in File Nesting, which handles file-to-file relationships (e.g., `Component.tsx` → `Component.css`). This plugin handles file-to-directory relationships.

## Use Cases

### Laravel/PHP

```
Models/
├── User.php
│   └── User/
│       ├── HasRoles.php
│       ├── HasPermissions.php
│       └── Auditable.php
```

### Rails/Ruby

```
models/
├── user.rb
│   └── user/
│       ├── authenticatable.rb
│       ├── confirmable.rb
│       └── trackable.rb
```

### React/TypeScript

```
components/
├── Button.tsx
│   └── Button/
│       ├── Button.stories.tsx
│       ├── Button.test.tsx
│       └── types.ts
```

## Development

Built with:

- Kotlin 2.1.0
- IntelliJ Platform Plugin SDK 2.2.1
- Gradle 8.5

### Building from Source

```bash
./gradlew buildPlugin
```

The plugin will be in `build/distributions/`.

### Running Tests

```bash
./gradlew test
```

## Contributing

Contributions welcome! Please feel free to submit issues or pull requests.

## License

Apache 2.0 - See [LICENSE](LICENSE) for details.

## Links

- [JetBrains Marketplace](https://plugins.jetbrains.com/plugin/com.zawndigital.advanced-nesting) (pending approval)
- [GitHub Releases](https://github.com/Zawn-Digital/phpstorm-advanced-nesting/releases)
- [Issues](https://github.com/Zawn-Digital/phpstorm-advanced-nesting/issues)

---

**made with ☕ and 🤖 on a lazy Sunday afternoon**
