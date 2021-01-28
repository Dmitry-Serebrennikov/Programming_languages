import java.lang.Exception

class IndexError(message: String?) : Exception(message)
class PlocError(message: String?) : Exception(message)

fun isNumber(str: String): Boolean {
    for (ch in str.trim()) {
        if (!ch.isDigit()) {
            return false
        }
    }
    return true
}

class SpecialHashMap: HashMap<String, Int>() {

    val iloc = Iloc()
    val ploc = Ploc()

    inner class Iloc {
        operator fun get(index: Int): Int {
            val sortedKeys = this@SpecialHashMap.keys.toSortedSet().toList()
            if (index < 0 || index >= sortedKeys.size) {
                throw IndexError("")
            }
            return this@SpecialHashMap[sortedKeys[index]]!!
        }
    }

    inner class Ploc {
        operator fun get(condition: String): HashMap<String, Int> {
            var result = this@SpecialHashMap.clone() as HashMap<String, Int>
            val conditions = condition.split(',').toMutableList()
            for ((i, condition) in conditions.withIndex()) {
                conditions[i] = condition.trim()
            }
            val newResult = HashMap<String, Int>()
            for ((key, value) in result) {
                var clearKey = key
                if (clearKey[0] == '(') {
                    clearKey = clearKey.slice(1 until clearKey.length)
                }
                if (clearKey[clearKey.length - 1] == ')') {
                    clearKey = clearKey.slice(0 until clearKey.length - 1)
                }
                val indexes = clearKey.split(',')
                if (indexes.size == conditions.size) {
                    var matches = true
                    for (i in indexes.indices) {
                        if (isNumber(indexes[i])) {
                            val number = indexes[i].trim().toInt()

                            val twoCharOperator = conditions[i].slice(0 until 2)
                            val twoCharValue = conditions[i].slice(2 until conditions[i].length).toIntOrNull()

                            val oneCharOperator = conditions[i][0]
                            val oneCharValue = conditions[i].slice(1 until conditions[i].length).toIntOrNull()

                            if (oneCharValue == null && twoCharValue == null) {
                                throw PlocError("")
                            }
                            matches = matches && when(twoCharOperator) {
                                ">=" -> number >= twoCharValue!!
                                "<=" -> number <= twoCharValue!!
                                "<>" -> number != twoCharValue!!.toInt()
                                else -> when (oneCharOperator) {
                                    '>' -> number > oneCharValue!!
                                    '<' -> number < oneCharValue!!
                                    '=' -> number == oneCharValue!!.toInt()
                                    else -> throw PlocError("")
                                }
                            }
                        } else {
                            matches = false
                        }
                    }
                    if (matches) {
                        newResult[key] = value
                    }
                }
                result = newResult
            }
            return result
        }
    }
}