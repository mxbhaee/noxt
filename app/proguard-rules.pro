-keepattributes *Annotation*, InnerClasses
-dontnote kotlinx.serialization.SerializationKt
-keep,includedescriptorclasses class com.yourcompany.yourpackage.**$$serializer { *; } # <-- change package name to your app's
-keepclassmembers class com.noxt.** { # <-- change package name to your app's
    *** Companion;
}
-keepclasseswithmembers class com.noxt.** { # <-- change package name to your app's
    kotlinx.serialization.KSerializer serializer(...);
}
-keepnames class androidx.navigation.fragment.NavHostFragment

#realm
-keepnames public class * extends io.realm.RealmObject
-keep @io.realm.annotations.RealmModule class *
-keep class io.realm.** { *; }
-dontwarn javax.**
-dontwarn io.realm.**