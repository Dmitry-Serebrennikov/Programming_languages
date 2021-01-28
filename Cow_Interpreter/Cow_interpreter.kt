import java.io.File

fun readCow(path: String): String {
    val file = File(path)
    val lines = file.readLines()
    return lines.joinToString(" ")
}

fun eval(source: List<String>){
    val buffer = Array<Char>(200){_ -> (0).toChar()}
    var ptr = 0
    val blocks = getLoopBlocks(source)
    var i = 0
    while (i < source.size) {
        when (source[i]){
            /*
            * MoO - значение текущей ячейки увеличить на 1
              MOo - значение текущей ячейки уменьшить на 1
              moO - следующая ячейка
              mOo - предыдущая ячейка
              moo - начало цикла
              MOO - конец цикла
              OOM - ввод значения в текущую ячейку
              oom - вывод значения текущей ячейки
              Moo - если значение в ячейке равно 0, то ввести с клавиатуры, если значение не 0, то вывести на экран
              OOO - обнулить значение в ячейке
            * */
            "MoO" -> buffer[ptr] = buffer[ptr] + 1
            "MOo" -> buffer[ptr] = buffer[ptr] - 1
            "moO" -> ptr += 1
            "mOo" -> ptr -= 1
            "MOO" -> {                                  //changed MOO <-> moo
                if (buffer[ptr] == (0).toChar()){
                    i = blocks[i]!!
                }
            }
            "moo" -> {
                if (buffer[ptr] != (0).toChar()){
                    i = blocks[i]!!
                }
            }
            "OOM" -> print(buffer[ptr].toInt())
            "oom" -> {
                print("Enter your value: ")
                buffer[ptr] = readLine()?.toCharArray()?.get(0)!!
            }
            "Moo" -> {
                if (buffer[ptr] == (0).toChar())
                    buffer[ptr] = readLine()?.toCharArray()?.get(0)!!
                else
                    print(buffer[ptr])
            }
            "OOO" -> buffer[ptr] = (0).toChar()
        }
        i += 1
    }
}

fun getLoopBlocks(source: List<String>): HashMap<Int, Int> {
    val blocks = HashMap<Int, Int>()
    val stack = mutableListOf<Int>()
    for ((i, char) in source.withIndex()){
        if (char == "MOO"){
            stack.add(i)
        }
        if (char == "moo"){
            blocks[i] = stack[stack.lastIndex]
            blocks[stack.removeAt(stack.lastIndex)] = i
        }
    }
    return blocks
}

fun main (args: Array<String>){
    val inputSource = readCow("hello.cow")
    println(inputSource)
    var outputSource = inputSource.split(" ")
    eval(outputSource)
}