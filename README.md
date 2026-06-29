# OriginSMS

A native Android default-SMS messaging app with per-chat PIN/biometric locking,
built with original branding and original code — no Google Messages assets reused.

## Features

- Full default SMS/MMS handler (SEND_SMS, RECEIVE_SMS, RoleManager integration)
- Conversation list, threaded messaging, multipart SMS, MMS attachments (send + receive)
- Per-chat lock: Normal / Locked (visible, PIN+biometric to open) / Hidden (vault)
- PIN stored as salted SHA-256 hash inside EncryptedSharedPreferences (Keystore-backed AES)
- BiometricPrompt (fingerprint/face) as an alternative to PIN, toggleable per-device
- Brute-force throttling on PIN attempts (exponential backoff, capped at 10 min)
- Blocked-number list (silently drops incoming SMS at the receiver level)
- Notification quick-reply / mark-as-read, with locked-chat content masking
- Draft auto-save per thread
- Light/dark theme, original indigo + coral brand palette

## Project structure

```
app/src/main/java/com/meharenterprises/originsms/
  core/          SmsRepository, ContactsHelper, MmsPduBuilder, data models
  data/db/       Room database (lock state, blocked numbers, drafts)
  receivers/     SMS_DELIVER, WAP_PUSH_DELIVER, boot, notification actions
  services/      HeadlessSmsSendService, RespondViaMessageService, MMS download, notifications
  lock/          PinManager, LockSetupActivity, LockUnlockActivity
  ui/            ConversationList, Thread, Compose, Settings, BlockedNumbers
```

## Building in Termux

1. Install the Android SDK command-line tools in Termux (one-time setup):
   ```
   pkg install openjdk-17 git
   ```
2. Set up local.properties (copy local.properties.example, point sdk.dir
   at your SDK location).
3. Generate the Gradle wrapper once (this creates gradlew + gradle-wrapper.jar,
   which are intentionally not committed to the repo):
   ```
   gradle wrapper --gradle-version 8.7
   ```
4. From the project root:
   ```
   ./gradlew assembleDebug
   ```
   The unsigned debug APK lands in app/build/outputs/apk/debug/.
5. Install directly:
   ```
   ./gradlew installDebug
   ```

Note: CI (GitHub Actions) generates the wrapper automatically on every run,
so no wrapper jar needs to live in version control.

## Building via GitHub Actions

Push to main to trigger a debug build (unsigned, downloadable from the
Actions run's Artifacts tab).

To produce a signed release APK, push a tag like v1.0.0. This requires
four repository secrets to be configured first (Settings -> Secrets and
variables -> Actions):

- KEYSTORE_BASE64: output of `base64 -w0 your-release.keystore`
- KEYSTORE_PASSWORD: keystore password
- KEY_ALIAS: key alias inside the keystore
- KEY_PASSWORD: key password (often same as keystore password)

Generate a release keystore (one-time, keep it safe — losing it means you can
never update the app under the same package again):
```
keytool -genkey -v -keystore release.keystore -alias originsms \
  -keyalg RSA -keysize 2048 -validity 10000
```

## Setting OriginSMS as the default SMS app

On first launch, a banner offers to set OriginSMS as the default SMS app via
RoleManager (Android 10+) or the legacy ACTION_CHANGE_DEFAULT intent on
older versions. Also available anytime from Settings -> Default SMS app.

## Per-chat lock — how it works

- Lock chat (long-press a conversation): requires a PIN to be configured
  the first time; afterward the chat stays visible in the list but opening it
  requires PIN or biometric.
- Hide chat: implies locked, and additionally removes the conversation
  from the main list entirely. Access it via the toolbar overflow menu ->
  "Hidden chats", which itself requires authentication.
- Remove lock: requires re-authentication before the lock is stripped, so
  a stranger holding an unlocked phone can't disable protection in one tap.
- Forgetting your PIN: Settings -> "Forgot PIN?" requires authenticating with
  the current PIN/biometric before a new one can be set — this is a
  deliberate security tradeoff, since there is no server component or
  account-recovery backend at all.

## Known limitations (disclosed, not hidden)

- MMS PDU encoding: MmsPduBuilder implements the core OMA-WAP-MMS-ENC
  fields needed for outgoing MMS (message type, transaction ID, version,
  from/to, date, multipart body). It covers what the large majority of
  carriers expect, but a few carriers require additional optional headers.
  If MMS sending fails on a specific network, that carrier's required headers
  need to be added to buildSendRequest.
- Incoming MMS download: MmsDownloadForegroundService receives the
  WAP-push notification PDU and establishes the foreground-service lifecycle
  needed to retrieve the full message, but the carrier-specific MMSC HTTP
  retrieval step (APN routing, M-Notification.ind to M-Retrieve.conf) is the
  next integration point if full incoming-MMS auto-download is required;
  until then, incoming MMS notifications are received but not auto-fetched.
- No build verification in the authoring environment: this codebase was
  authored and statically cross-checked (every R.layout/R.id/R.string/
  R.drawable reference verified against actual resource files) but not
  compiled, because the authoring sandbox has no Android SDK or Google Maven
  access. First real build should happen via GitHub Actions or Termux —
  please report the exact error if anything fails to compile.
- RCS is not implemented. This is SMS/MMS only, consistent with what was
  scoped at the start of the project.

## Permissions requested

SEND_SMS, RECEIVE_SMS, READ_SMS, WRITE_SMS, RECEIVE_WAP_PUSH,
RECEIVE_MMS, READ_CONTACTS, WRITE_CONTACTS, POST_NOTIFICATIONS,
USE_BIOMETRIC, FOREGROUND_SERVICE, RECEIVE_BOOT_COMPLETED, INTERNET
(MMS network retrieval only — no analytics, no remote logging, no ads SDK).
