fun main(args: Array<String>) {
    var code: String

    while (true) {
        print("Enter your code here: ")
        code = readLine().toString()

        if ((code == "q") or (code.isEmpty())) {
            break
        }
        try {
            val parser = Parser(Lexer(code))
            val interpreter = Interpreter(parser)
            interpreter.interpret()
            print("Result: ")
            println(interpreter.ID)
        }
        catch (e: InterpreterException){
            System.err.println("Error: $e")

        }
    }

    val test1 = "BEGIN END."
    val test2 = "BEGIN x := 2 + 3 * (2 + 3); y := 2 / 2 - 2 + 3 * ((1 + 1) + (1 + 1)); END."
    val test3 = "BEGIN y := 2; BEGIN a := 3; a := a; b := 10 + a + 10 * y / 4; c := a - b; END x := 11; END."

}