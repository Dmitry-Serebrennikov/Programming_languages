class InterpreterException(message: String): Exception(message)

class Interpreter(val parser: Parser, val verbose: Boolean = false): NodeVisitor {
    val ID = HashMap<String, Double>()
    override fun visit(node: Node) {
        val body: Body = node as Body
        var action: Node? = body.next()
        var isNextAction = action != null

        while (isNextAction) {
            when (action) {
                is Writer -> visitWriter(action)
                is Body -> visit(action)
                is Eol -> return
                else -> visitCalc(action!!)
            }
            action = body.next()
            isNextAction = action != null
        }
    }

    private fun visitNumber(node: Node): Double {
        val number = node as Number
        print("VisitNumber $number")
        return number.token.value.toDouble()
    }

    private fun visitUnaryOp(node: Node) : Double {
        val operator = node as UnaryOp

        print("VisitUnaryOp $operator")

        when (operator.op.type) {
            TokenType.PLUS -> return + visitCalc(operator.expr)
            TokenType.MINUS -> return -visitCalc(operator.expr)
        }
        throw InterpreterException("Invalid UnaryOp")
    }

    private fun visitBinOp(node: Node): Double {
        val operator = node as BinOp

        print("VisitBinOp $operator")

        when (operator.op.type) {
            TokenType.PLUS -> return visitCalc(operator.left) + visitCalc(operator.right)
            TokenType.MINUS -> return visitCalc(operator.left) - visitCalc(operator.right)
            TokenType.DIV -> return visitCalc(operator.left) / visitCalc(operator.right)
            TokenType.MUL -> return visitCalc(operator.left) * visitCalc(operator.right)
        }
        throw InterpreterException("invalid BinOp $operator")
    }

    public fun interpret() {
        val tree = parser.parse()
        if (tree == null){
            throw InterpreterException("Null tree")
        }
        visit(tree)
    }

    private fun visitWriter(node: Node) {
        if (node is Writer) {
            val value = visitCalc(node.expr)
            ID.put(node.id.toString(), value)
        }
    }

    private fun visitCalc(node: Node): Double {
        when (node){
            is Number -> return visitNumber(node)
            is BinOp -> return  visitBinOp(node)
            is UnaryOp -> return visitUnaryOp(node)
            is Variable -> {
                val s: String = node.toString()
                return ID[s]!!
            }
        }
        throw InterpreterException("visitCalc Error")
    }
    private fun print(s: String) = verbose.ifTrue { println(s) }
}

fun Boolean.ifTrue(action: () -> Unit) {
    if (this) {
        action()
    }
}
