# Galaxy Shutter Mute Manager

[日本語](README.md) · English

Install, update, and grant the required permissions to [Galaxy Shutter Mute](https://github.com/OneUI-Mods/galaxy-shutter-mute).

## Requirements

- A supported Samsung Galaxy device.
- A running [Shizuku](https://shizuku.rikka.app/) instance and authorization for the Manager.
- Internet access to download distributed APKs.

The Manager requires Android 8+. Availability of the main app's shutter-sound setting also depends on the device and camera version. Previous validation extends through Android 16 / One UI 8.5; this update does not add Android 17 validation.

## Usage

1. Install the Manager's release APK from [Releases](https://github.com/OneUI-Mods/galaxy-shutter-mute-manager/releases).
2. Start Shizuku and authorize the Manager.
3. Open the Manager and choose **Install** or **Update**.
4. Open Galaxy Shutter Mute after installation and configure the shutter sound.

An update button appears when a newer main-app version is available. The distribution source can be changed in settings. OneUI Mods is the default; old URLs still reach it through GitHub redirects.

## Limitations

The Manager does not itself toggle camera shutter sound. It assists installation and permission setup for the main app. If Shizuku has stopped, restart it before using the Manager.

## Development and licenses

Build instructions, architecture, and verification are in the [developer guide (Japanese)](docs/development.md). Report problems through [Issues](https://github.com/OneUI-Mods/galaxy-shutter-mute-manager/issues), including device, OS and target-app versions, and reproduction steps.

See [LICENSE](LICENSE) for the app's license and the in-app or bundled notices for dependencies. This is not an official Samsung app.

[OneUI Mods app catalog](https://github.com/OneUI-Mods)
