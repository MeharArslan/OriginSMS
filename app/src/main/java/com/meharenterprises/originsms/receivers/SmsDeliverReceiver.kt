Run ./gradlew assembleDebug --no-daemon
Fetching distribution.
Downloading https://services.gradle.org/distributions/gradle-8.7-bin.zip
............10%.............20%.............30%.............40%............50%.............60%.............70%.............80%.............90%............100%
Welcome to Gradle 8.7!
Here are the highlights of this release:
 - Compiling and testing with Java 22
 - Cacheable Groovy script compilation
 - New methods in lazy collection properties
For more details see https://docs.gradle.org/8.7/release-notes.html
To honour the JVM settings for this build a single-use Daemon process will be forked. For more on this, please refer to https://docs.gradle.org/8.7/userguide/gradle_daemon.html#sec:disabling_the_daemon in the Gradle documentation.
Daemon will be stopped at the end of the build 
> Configure project :
w: file:///home/runner/work/OriginSMS/OriginSMS/build.gradle.kts:8:24: 'getter for buildDir: File!' is deprecated. Deprecated in Java
> Task :app:preBuild UP-TO-DATE
> Task :app:preDebugBuild UP-TO-DATE
> Task :app:mergeDebugNativeDebugMetadata NO-SOURCE
> Task :app:checkKotlinGradlePluginConfigurationErrors
> Task :app:generateDebugResValues
> Task :app:dataBindingMergeDependencyArtifactsDebug
> Task :app:generateDebugResources
> Task :app:packageDebugResources
> Task :app:mergeDebugResources
> Task :app:parseDebugLocalResources
> Task :app:mapDebugSourceSetPaths
> Task :app:checkDebugAarMetadata
> Task :app:dataBindingGenBaseClassesDebug
> Task :app:createDebugCompatibleScreenManifests
> Task :app:extractDeepLinksDebug
> Task :app:processDebugMainManifest
> Task :app:processDebugManifest
> Task :app:javaPreCompileDebug
> Task :app:mergeDebugShaders
> Task :app:compileDebugShaders NO-SOURCE
> Task :app:generateDebugAssets UP-TO-DATE
> Task :app:mergeDebugAssets
> Task :app:compressDebugAssets
> Task :app:desugarDebugFileDependencies
> Task :app:mergeDebugStartupProfile
> Task :app:mergeDebugJniLibFolders
> Task :app:mergeDebugNativeLibs NO-SOURCE
> Task :app:checkDebugDuplicateClasses
> Task :app:stripDebugDebugSymbols NO-SOURCE
> Task :app:processDebugManifestForPackage
> Task :app:mergeExtDexDebug
> Task :app:mergeLibDexDebug
> Task :app:processDebugResources
> Task :app:validateSigningDebug
> Task :app:writeDebugAppMetadata
> Task :app:writeDebugSigningConfigVersions
> Task :app:kspDebugKotlin
e: file:///home/runner/work/OriginSMS/OriginSMS/app/src/main/java/com/meharenterprises/originsms/receivers/SmsDeliverReceiver.kt:32:46 Unresolved reference: getMessages
e: file:///home/runner/work/OriginSMS/OriginSMS/app/src/main/java/com/meharenterprises/originsms/receivers/SmsDeliverReceiver.kt:37:64 Unresolved reference: it
> Task :app:compileDebugKotlin FAILED
e: file:///home/runner/work/OriginSMS/OriginSMS/app/src/main/java/com/meharenterprises/originsms/receivers/SmsDeliverReceiver.kt:47:25 Overload resolution ambiguity: 
public open fun put(p0: String!, p1: Boolean!): Unit defined in android.content.ContentValues
public open fun put(p0: String!, p1: Byte!): Unit defined in android.content.ContentValues
public open fun put(p0: String!, p1: ByteArray!): Unit defined in android.content.ContentValues
public open fun put(p0: String!, p1: Double!): Unit defined in android.content.ContentValues
public open fun put(p0: String!, p1: Float!): Unit defined in android.content.ContentValues
public open fun put(p0: String!, p1: Int!): Unit defined in android.content.ContentValues
public open fun put(p0: String!, p1: Long!): Unit defined in android.content.ContentValues
public open fun put(p0: String!, p1: Short!): Unit defined in android.content.ContentValues
public open fun put(p0: String!, p1: String!): Unit defined in android.content.ContentValues
e: file:///home/runner/work/OriginSMS/OriginSMS/app/src/main/java/com/meharenterprises/originsms/receivers/SmsDeliverReceiver.kt:48:25 Overload resolution ambiguity: 
public open fun put(p0: String!, p1: Boolean!): Unit defined in android.content.ContentValues
public open fun put(p0: String!, p1: Byte!): Unit defined in android.content.ContentValues
public open fun put(p0: String!, p1: ByteArray!): Unit defined in android.content.ContentValues
public open fun put(p0: String!, p1: Double!): Unit defined in android.content.ContentValues
public open fun put(p0: String!, p1: Float!): Unit defined in android.content.ContentValues
public open fun put(p0: String!, p1: Int!): Unit defined in android.content.ContentValues
public open fun put(p0: String!, p1: Long!): Unit defined in android.content.ContentValues
public open fun put(p0: String!, p1: Short!): Unit defined in android.content.ContentValues
public open fun put(p0: String!, p1: String!): Unit defined in android.content.ContentValues
e: file:///home/runner/work/OriginSMS/OriginSMS/app/src/main/java/com/meharenterprises/originsms/receivers/SmsDeliverReceiver.kt:49:25 Overload resolution ambiguity: 
public open fun put(p0: String!, p1: Boolean!): Unit defined in android.content.ContentValues
public open fun put(p0: String!, p1: Byte!): Unit defined in android.content.ContentValues
public open fun put(p0: String!, p1: ByteArray!): Unit defined in android.content.ContentValues
public open fun put(p0: String!, p1: Double!): Unit defined in android.content.ContentValues
public open fun put(p0: String!, p1: Float!): Unit defined in android.content.ContentValues
public open fun put(p0: String!, p1: Int!): Unit defined in android.content.ContentValues
public open fun put(p0: String!, p1: Long!): Unit defined in android.content.ContentValues
public open fun put(p0: String!, p1: Short!): Unit defined in android.content.ContentValues
public open fun put(p0: String!, p1: String!): Unit defined in android.content.ContentValues
e: file:///home/runner/work/OriginSMS/OriginSMS/app/src/main/java/com/meharenterprises/originsms/services/NotificationHelper.kt:105:29 Type mismatch: inferred type is android.app.RemoteInput but androidx.core.app.RemoteInput? was expected
FAILURE: Build failed with an exception.
* What went wrong:
Execution failed for task ':app:compileDebugKotlin'.
> A failure occurred while executing org.jetbrains.kotlin.compilerRunner.GradleCompilerRunnerWithWorkers$GradleKotlinCompilerWorkAction
   > Compilation error. See log for more details
* Try:
> Run with --stacktrace option to get the stack trace.
> Run with --info or --debug option to get more log output.
> Run with --scan to get full insights.
> Get more help at https://help.gradle.org.
BUILD FAILED in 2m 18s
31 actionable tasks: 31 executed
Error: Process completed with exit code 1.
