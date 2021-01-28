import java.io.File
import kotlin.math.log2

abstract class Rule() {
    open fun check(password: String):Boolean {
        return false
    }
    open val description: String = ""
}

class lengthValidation () : Rule() {
    override val description: String = "Ops! Password length must be between 8 and 40 characters!"
    override fun check(password: String): Boolean {
        if (password.length < 8 || password.length  > 40) {
            return false
        }
        return true
    }
}

class upperLowerCase (): Rule(){
    override val description: String = "Error! Password must contain lowercase and uppercase Latin letters!"
    override fun check(password: String): Boolean {
        var upper = false
        var lower = false

        for (c: Char in password){
            if (c in 'a'..'z')
                lower = true
            else if (c in 'A'..'Z')
                upper = true
        }
        return (lower && upper)
    }
}

class specSigns (): Rule() {
    override val description: String = "Hmm... Password must contain special characters!"
    val signs = arrayListOf('!', '@', '#', '$', '%', '^', '&', '*', '(', ')', '-', '+', '_', '=', '`', '~',
        '<', '>', '?', '/', ':', '\'', '[', ']', '{', '}', "/\\", '|', '"')
    override fun check(password: String) : Boolean {
        for (c: Char in password) {
            if (c in signs)
                return true
        }
        return false
    }
}

class noDictinaryWords (): Rule() {
    override val description: String = "Incorrect! Password must not contain dictionary words!"
    override fun check(password: String): Boolean {
        val dictionary = File("pswd-dict.txt")
        val words = ArrayList<String>()
        dictionary.readLines().forEach {
            words.addAll(it.split(" "))
        }
        if (password in words)
            return false
        return true
    }
}

class entropyLevel (): Rule() {
    override val description: String = "Password must have a high level of entropy! Please, try again..."
    override fun check(password: String): Boolean {
        var H = 0.0
        val frequence = HashMap<Char, Double>()

        for (c: Char in password){
            if (!frequence.containsKey(c)) {
                frequence.put(c, 1.0)
            }
            else {
                frequence.put(c, frequence.getValue(c) + 1.0)
            }
        }
        val dictSize = frequence.size

        for (f in frequence){
            f.setValue(f.value / dictSize)
            H += f.value * log2(f.value)
        }
        H = -H
        if (H > 1)
            return true
        return false
    }
}








