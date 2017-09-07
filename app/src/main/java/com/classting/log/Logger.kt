package com.classting.log

import android.util.Log
import com.classting.BuildConfig


/**
 * Created by DavidHa on 2017. 9. 7..
 */
object Logger {

    private val TAG = "UnivReview"
    private val needLong = true
    val LOG_SEPARATE_LENGTH = 3000

    fun longInfo(str: String): ArrayList<String> {
        var str = str
        val response = ArrayList<String>()
        if (needLong) {
            if (str.length > LOG_SEPARATE_LENGTH) {
                while (str.length > LOG_SEPARATE_LENGTH) {
                    response.add(str.substring(0, LOG_SEPARATE_LENGTH))
                    str = str.substring(LOG_SEPARATE_LENGTH)
                }
                response.add(str)
            } else {
                response.add(str)
            }
        } else {
            response.add(str)
        }
        return response
    }

    fun e(tag: String, message: Any) {
        if (BuildConfig.DEBUG) {
            val fullClassName = Thread.currentThread().stackTrace[3].className
            val className = fullClassName.substring(fullClassName.lastIndexOf(".") + 1)
            val methodName = Thread.currentThread().stackTrace[3].methodName
            val lineNumber = Thread.currentThread().stackTrace[3].lineNumber
            val response = longInfo("$className.$methodName():$lineNumber   $message")
            for (s in response) {
                Log.e(tag, s)
            }
        }
    }

    fun v(tag: String, message: Any) {
        if (BuildConfig.DEBUG) {
            val fullClassName = Thread.currentThread().stackTrace[3].className
            val className = fullClassName.substring(fullClassName.lastIndexOf(".") + 1)
            val methodName = Thread.currentThread().stackTrace[3].methodName
            val lineNumber = Thread.currentThread().stackTrace[3].lineNumber
            val response = longInfo("$className.$methodName():$lineNumber   $message")
            for (s in response) {
                Log.v(tag, s)
            }
        }
    }

    fun e(message: Any) {
        val fullClassName = Thread.currentThread().stackTrace[3].className
        val className = fullClassName.substring(fullClassName.lastIndexOf(".") + 1)
        val methodName = Thread.currentThread().stackTrace[3].methodName
        val lineNumber = Thread.currentThread().stackTrace[3].lineNumber
        val response = longInfo("$className.$methodName():$lineNumber   $message")
        for (s in response) {
            Log.e(TAG, s)
        }
    }


    fun a(message: Any) {
        if (BuildConfig.DEBUG) {
            val fullClassName = Thread.currentThread().stackTrace[3].className
            val className = fullClassName.substring(fullClassName.lastIndexOf(".") + 1)
            val methodName = Thread.currentThread().stackTrace[3].methodName
            val lineNumber = Thread.currentThread().stackTrace[3].lineNumber
            val response = longInfo("$className.$methodName():$lineNumber   $message")
            for (s in response) {
                Log.println(Log.ASSERT, TAG, s)
            }
        }
    }

    fun v(message: Any) {
        if (BuildConfig.DEBUG) {
            val fullClassName = Thread.currentThread().stackTrace[3].className
            val className = fullClassName.substring(fullClassName.lastIndexOf(".") + 1)
            val methodName = Thread.currentThread().stackTrace[3].methodName
            val lineNumber = Thread.currentThread().stackTrace[3].lineNumber
            val response = longInfo("$className.$methodName():$lineNumber   $message")
            for (s in response) {
                Log.v(TAG, s)
            }
        }
    }

    fun d(tag: String, message: Any) {
        if (BuildConfig.DEBUG) {
            val fullClassName = Thread.currentThread().stackTrace[3].className
            val className = fullClassName.substring(fullClassName.lastIndexOf(".") + 1)
            val methodName = Thread.currentThread().stackTrace[3].methodName
            val lineNumber = Thread.currentThread().stackTrace[3].lineNumber
            val response = longInfo("$className.$methodName():$lineNumber   $message")
            for (s in response) {
                Log.d(tag, s)
            }
        }
    }

    fun d(message: Any) {
        if (BuildConfig.DEBUG) {
            val fullClassName = Thread.currentThread().stackTrace[3].className
            val className = fullClassName.substring(fullClassName.lastIndexOf(".") + 1)
            val methodName = Thread.currentThread().stackTrace[3].methodName
            val lineNumber = Thread.currentThread().stackTrace[3].lineNumber
            val response = longInfo("$className.$methodName():$lineNumber   $message")
            for (s in response) {
                Log.d(TAG, s)
            }
        }
    }

    fun i(message: Any) {
        if (BuildConfig.DEBUG) {
            val fullClassName = Thread.currentThread().stackTrace[3].className
            val className = fullClassName.substring(fullClassName.lastIndexOf(".") + 1)
            val methodName = Thread.currentThread().stackTrace[3].methodName
            val lineNumber = Thread.currentThread().stackTrace[3].lineNumber
            val response = longInfo("$className.$methodName():$lineNumber   $message")
            for (s in response) {
                Log.i(TAG, s)
            }
        }
    }

    fun objs(vararg objects: Any) {
        if (BuildConfig.DEBUG) {
            val fullClassName = Thread.currentThread().stackTrace[3].className
            val className = fullClassName.substring(fullClassName.lastIndexOf(".") + 1)
            val methodName = Thread.currentThread().stackTrace[3].methodName
            val lineNumber = Thread.currentThread().stackTrace[3].lineNumber
            val sb = StringBuilder()
            for (`object` in objects) {
                if (`object` != null) {
                    sb.append(`object`.toString())
                    sb.append("  ")
                }
            }
            val response = longInfo(className + "." + methodName + "():" + lineNumber + "   " + sb.toString())
            for (s in response) {
                Log.i(TAG, s)
            }
        }
    }

    fun i(vararg objects: Any) {
        if (BuildConfig.DEBUG) {
            val fullClassName = Thread.currentThread().stackTrace[3].className
            val className = fullClassName.substring(fullClassName.lastIndexOf(".") + 1)
            val methodName = Thread.currentThread().stackTrace[3].methodName
            val lineNumber = Thread.currentThread().stackTrace[3].lineNumber
            val sb = StringBuilder()
            for (`object` in objects) {
                if (`object` != null) {
                    sb.append(`object`.toString())
                    sb.append("  ")
                }
            }
            val response = longInfo(className + "." + methodName + "():" + lineNumber + "   " + sb.toString())
            for (s in response) {
                Log.i(TAG, s)
            }
        }
    }

    fun clean(message: Any?) {
        if (BuildConfig.DEBUG && message != null) {
            val response = longInfo(message.toString())
            for (s in response) {
                Log.e(TAG, s)
            }
        }
    }

    private fun toString(vararg objects: Any): String {
        val sb = StringBuilder()
        for (o in objects) {
            sb.append(o)
        }
        return sb.toString()
    }
}