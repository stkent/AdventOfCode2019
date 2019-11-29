@file:Suppress("unused", "MemberVisibilityCanBePrivate")

import java.io.File

fun resourceFile(name: String) = File(ClassLoader.getSystemResource(name).file)
