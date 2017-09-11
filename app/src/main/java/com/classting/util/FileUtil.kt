package com.classting.util

import com.classting.log.Logger
import java.io.*
import java.text.SimpleDateFormat
import java.util.*

/**
 * Created by DavidHa on 2017. 9. 10..
 */
class FileUtil {
    companion object {
        private val fileName = "videoInfo.txt"
        private val dateFormat = SimpleDateFormat("yyyy-MM-dd hh:mm:ss")

        fun recordVideoInfo(fileDir: File, feedId: Long?) {
            if (feedId != null && feedId != -1L) {
                val file = File(fileDir, fileName)
                Logger.v("record video info")

                if(!file.exists()) file.createNewFile()
                val stringBuilder = StringBuilder()
                val timeStamp = getTimeStamp().toString()
                stringBuilder.append("Feed id: ").append(feedId).append(", ")
                val copyStr = searchFeedId(stringBuilder, timeStamp, file)
                Logger.v("copy str\n: $copyStr")

                val writer = BufferedWriter(FileWriter(file))
                writer.write(copyStr)
                writer.close()
                Logger.v("record: " + stringBuilder.toString())
            }
        }

        private fun searchFeedId(stringBuilder: StringBuilder, timeStamp: String, file: File): String {
            val reader = BufferedReader(FileReader(file))
            val copyStr = StringBuilder()
            var line: String?
            var isFind : Boolean = false
            do {
                line = reader.readLine()
                //Logger.v("line: " + line)
                if (line == null) {
                    if(!isFind) {
                        stringBuilder.append(timeStamp)
                        copyStr.append(stringBuilder.toString() + "\r\n")
                    }
                    reader.close()
                    break
                }

                if (line.contains(stringBuilder)) {
                    isFind = true
                    //Logger.v("find contains")
                    val replaceLine = line.replaceRange(0, line.length, stringBuilder.append(timeStamp).toString())
                    //Logger.v("replace line: $replaceLine")
                    copyStr.append(replaceLine + "\r\n")
                } else {
                    //Logger.v("write line: $line")
                    copyStr.append(line + "\r\n")
                }
            } while (true)
            return copyStr.toString()
        }

        private fun getTimeStamp(): StringBuilder {
            return StringBuilder("Time Stamp: ").append(dateFormat.format(Date()))
        }
    }
}