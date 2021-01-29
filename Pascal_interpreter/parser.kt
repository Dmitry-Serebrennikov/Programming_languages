
class ParserException(message: String): Exception(message)

class Parser(private val lexer: Lexer) {
    private var currentToken: Token = lexer.nextToken()

    private fun checkTokenType(type: TokenType){
        if (currentToken.type == type) {
            currentToken = lexer.nextToken()
        }
        else {
            throw ParserException("invalid token order")
        }
    }

    private fun factor(): Node {
        val token = currentToken
        when (token.type) {
            TokenType.PLUS-> {
                checkTokenType(TokenType.PLUS)
                return UnaryOp(token, factor())
            }
            TokenType.MINUS -> {
                checkTokenType(TokenType.MINUS)
                return UnaryOp(token, factor())
            }
            TokenType.NUMBER-> {
                checkTokenType(TokenType.NUMBER)
                return Number(token)
            }
            TokenType.LPAREN-> {
                checkTokenType(TokenType.LPAREN)
                val result = expr()
                checkTokenType(TokenType.RPAREN)
                return result
            }
        }
        throw  ParserException("invalid factor")
    }

    private fun term(): Node {
        var result = factor()
        val ops = arrayListOf<TokenType>(TokenType.DIV,
                TokenType.MUL)
        while (ops.contains(currentToken.type)) {
            val token = currentToken
            when (token.type) {
                TokenType.MUL -> {
                    checkTokenType(TokenType.MUL)
                }
                TokenType.DIV -> {
                    checkTokenType(TokenType.DIV)
                }
            }
            return BinOp(result, token, factor())
        }
        return result
    }

    fun expr(): Node {
        /*currentToken = nextToken()
        val left = currentToken
        checkTokenType(TokenType.INTEGER)

        val op = currentToken
        if (op.type == TokenType.PLUS) {
            checkTokenType(TokenType.PLUS)
        }
        else {
            checkTokenType(TokenType.MINUS)
        }
        val right = currentToken
        checkTokenType(TokenType.INTEGER)
        if (op.type == TokenType.PLUS) {
            return left.value.toInt() + right.value.toInt()
        }
        else {
            return left.value.toInt() - right.value.toInt()
        } */
        //currentToken = lexer.nextToken()
        val ops = arrayListOf<TokenType>(TokenType.PLUS,
                TokenType.MINUS)
        var result = term()
        while (ops.contains(currentToken.type)) {
            val token = currentToken
            if (token.type == TokenType.PLUS) {
                checkTokenType(TokenType.PLUS)
            }
            if (token.type == TokenType.MINUS) {
                checkTokenType(TokenType.MINUS)
            }
            /*if (token.type == TokenType.MUL) {
                checkTokenType(TokenType.MUL)
                result *= term()
            }
            if (token.type == TokenType.DIV) {
                checkTokenType(TokenType.DIV)
                result /= term()
            } */
            return BinOp(result, token, term())
        }
        return result
    }
}

fun main(args: Array<String>){
    val parser = Parser(Lexer("-2 - -2"))
    print(parser.expr())
}