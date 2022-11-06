@file:Suppress("MemberVisibilityCanBePrivate", "unused")

package app

object Plugins {

    object KotlinPlugin {
        private const val version = deps.Kotlin.version
        const val plugin = "org.jetbrains.kotlin:kotlin-gradle-plugin:$version"
    }

    object SerializationPlugin {
        private const val version = deps.Kotlin.version
        const val plugin = "org.jetbrains.kotlin:kotlin-serialization:$version"
    }

}
