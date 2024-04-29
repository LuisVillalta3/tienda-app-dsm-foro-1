package sv.edu.udb.vr181981.tiendavirtualsqlite.utils

import java.security.MessageDigest

object Utils {
    fun encryptPassword(password: String): String {
        val digest = MessageDigest.getInstance("SHA-256")
        val hashBytes = digest.digest(password.toByteArray())

        // Convert hash bytes to hexadecimal string
        val hexString = StringBuffer()
        for (byte in hashBytes) {
            // Convert each byte to hexadecimal representation
            val hex = Integer.toHexString(0xff and byte.toInt())
            if (hex.length == 1) hexString.append('0')
            hexString.append(hex)
        }

        return hexString.toString()
    }
}