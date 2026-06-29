# Keep Room entities
-keep class com.meharenterprises.originsms.data.db.** { *; }

# Keep manifest-declared components (receivers/services) used by Telephony framework
-keep class com.meharenterprises.originsms.receivers.** { *; }
-keep class com.meharenterprises.originsms.services.** { *; }

-keepattributes *Annotation*
-keepattributes Signature
-dontwarn kotlinx.coroutines.**
