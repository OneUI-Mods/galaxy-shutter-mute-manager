# Galaxy Shutter Mute Manager

[Galaxy Shutter Mute](https://github.com/OneUI-Mods/galaxy-shutter-mute)の導入・更新と、必要な権限の付与を行うアプリです。

## 必要なもの

- 対応するSamsung Galaxy端末。
- 起動済みの[Shizuku](https://shizuku.rikka.app/)と、Managerへのアクセス許可。
- 配布APKを取得するためのインターネット接続。

ManagerのインストールにはAndroid 8以降が必要です。本体のシャッター音設定が利用できるかどうかは端末・カメラのバージョンにも依存します。従来の確認範囲はAndroid 16 / One UI 8.5までで、Android 17の動作確認をこの更新で追加したものではありません。

## 使い方

1. [Releases](https://github.com/OneUI-Mods/galaxy-shutter-mute-manager/releases)からManagerのrelease APKをインストールします。
2. Shizukuを起動してManagerの認証を許可します。
3. Managerを開き、「インストール」または「アップデート」を押します。
4. 導入後にGalaxy Shutter Muteを開き、シャッター音を設定します。

本体の新しい版がある場合は更新ボタンを表示します。設定画面から配布先を変更することもできます。標準の配布先はOneUI Modsです。旧URLからもGitHubの転送で到達できます。

## 制限

Manager自身がカメラのシャッター音を切り替えるわけではありません。本体のインストールと権限設定を支援します。Shizukuが停止している場合は、起動し直してから操作してください。

## 開発者向け・ライセンス

ビルド、内部構造、検証方法は[開発者向けガイド](docs/development.md)に分けています。不具合報告は[Issues](https://github.com/OneUI-Mods/galaxy-shutter-mute-manager/issues)へ、端末・OS・対象アプリの版と再現手順を添えてください。

本体のライセンスは[LICENSE](LICENSE)、依存ライブラリの表記はアプリ内または同梱の第三者ライセンスを参照してください。Samsungの公式アプリではありません。

[OneUI Modsのアプリ一覧](https://github.com/OneUI-Mods)
