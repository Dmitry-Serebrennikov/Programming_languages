enum class TokenType {
    INTEGER,
    PLUS,
    MINUS,
    MUL,
    DIV,
    LPAREN,
    RPAREN,
    EOL,
    ID,
    ASSIGNMENT,
    BEGIN,
    END,
    EOF
}

class Token(val type: TokenType, val value: String) {

    override fun toString(): String {
        return "Token ($type, $value)"
    }
}