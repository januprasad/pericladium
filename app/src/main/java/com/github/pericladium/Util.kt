package com.github.pericladium

infix fun <T> Boolean.then(param: T): T? = if (this) param else null
