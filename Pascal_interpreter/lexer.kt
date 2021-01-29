class Lexer(val text: String) {
    private var pos: Int = 0
    private var currentChar: Char? = null

    init {
        currentChar = text[pos]
    }


    private fun forward() {
        pos += 1
        if (pos > text.length - 1) {
            currentChar = null
        }
        else {
            currentChar = text[pos]
        }
    }

    private fun skip() {
        while (currentChar != null && (currentChar!!.isWhitespace())){
            forward()
        }
    }

    private fun number(): String {
        var result = arrayListOf<Char>()
        while ((currentChar != null) && ((currentChar == '.') || (currentChar!!.isDigit()))) {
            result.add(currentChar!!)
            forward()
        }
        return result.joinToString("")
    }

    public fun nextToken() : Token {
        var value: String
        var type: TokenType?
        while (currentChar != null) {
            if (currentChar!!.isWhitespace()) {
                skip()
                continue
            }
            if (currentChar!!.isDigit()) {
                return Token(TokenType.NUMBER, number())
            }
            type = null
            value = "$currentChar"

            when (currentChar) {
                '+' -> type = TokenType.PLUS
                '-' -> type = TokenType.MINUS
                '*' -> type = TokenType.MUL
                '/' -> type = TokenType.DIV
                '(' -> type = TokenType.LPAREN
                ')' -> type = TokenType.RPAREN
            }
            /*if (type != null) {
                forward()
                return Token(type, value)
            }*/
            type?.let {
                forward()
                return Token(it, value)
            }
            /*if (currentChar == '+') {
                forward()
                return Token(TokenType.PLUS, "$currentChar")
            }
            if (currentChar == '-') {
                forward()
                return Token(TokenType.MINUS, "$currentChar")
            }
            if (currentChar == '*') {
                forward()
                return Token(TokenType.MUL, "$currentChar")
            }
            if (currentChar == '/') {
                forward()
                return Token(TokenType.DIV, "$currentChar")
            }
            if (currentChar == '(') {
                forward()
                return Token(TokenType.LPAREN, "$currentChar")
            }
            if (currentChar == ')') {
                forward()
                return Token(TokenType.RPAREN, "$currentChar")
            } */
            throw InterpreterException("invalid token order")
        }
        return Token(TokenType.EOL, "")
    }

}
fun main(args: Array<String>){
    val lexer = Lexer("2 + 2")
    print(lexer.nextToken())
    print(lexer.nextToken())
    print(lexer.nextToken())
}