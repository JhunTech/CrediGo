# =========================================================================
# ProGuard / R8 Keep Rules for CrediGo Android App
# =========================================================================

# --- General Kotlin & Annotations ---
-keepattributes *Annotation*, Signature, InnerClasses, EnclosingMethod, SourceFile, LineNumberTable
-keepclassmembers class * {
    @kotlin.jvm.Volatile <fields>;
}

# --- Data Models & Serialization ---
-keep class com.redfrogec.credigo.data.model.** { *; }
-keepclassmembers class * {
    @kotlinx.serialization.SerialName <fields>;
    @kotlinx.serialization.Serializable <fields>;
}
-keep interface kotlinx.serialization.KSerializer
-keepclassmembers class * implements kotlinx.serialization.KSerializer {
    *** INSTANCE;
}

# --- SQLDelight Generated Code ---
-keep class com.redfrogec.* { *; }
-keep class app.cash.sqldelight.db.** { *; }
-dontwarn app.cash.sqldelight.**

# --- Ktor Client ---
-keep class io.ktor.** { *; }
-dontwarn io.ktor.**

# --- Koin Dependency Injection ---
-keep class io.insertkoin.** { *; }
-dontwarn io.insertkoin.**

# --- KotlinCrypto ---
-keep class org.kotlincrypto.** { *; }
-dontwarn org.kotlincrypto.**

# --- Multiplatform Settings ---
-keep class com.russhwolf.settings.** { *; }

# --- Compose & AndroidX ---
-dontwarn androidx.compose.**
