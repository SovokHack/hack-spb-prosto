package com.hack.hackathon.exception

import java.lang.RuntimeException

abstract class AbstractException(val messageLocal: Exceptions): RuntimeException() {
}