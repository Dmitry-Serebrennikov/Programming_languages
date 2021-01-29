import java.io.File
import java.net.ServerSocket
import java.net.SocketTimeoutException

class Server(private val port: Int) {
    private val serverSocket = ServerSocket(port)

    fun start() {
        val interpreter = Interpreter()
        Logger.attach(ConsoleHandler())
        Logger.attach((FileHandler(File("C:\\Users\\argen\\log.txt"))))

        while (true) {
            Logger.info("Wait for new connection")
            val clientSocket = serverSocket.accept()
            clientSocket.soTimeout = 30000
            Logger.info("Proccess new client ${clientSocket.localAddress}")
            val inStream = clientSocket.getInputStream().bufferedReader()
            val outStream = clientSocket.getOutputStream().bufferedWriter()

            while(!clientSocket.isOutputShutdown) {
                try {
                    Logger.info("Wait for new message")
                    val data = inStream.readLine()
                    if (data != null) {
                        Logger.info(data)
                        val result = interpreter.interpret(Parser(Lexer(data)).expr())
                        outStream.write("Result = $result\n")
                        outStream.flush()
                    }
                    else {
                        Logger.info("Client closed connection")
                        break
                    }
                }
                catch (e: SocketTimeoutException) {
                    Logger.warning("Disconnect client due timeout")
                    clientSocket.close()
                    break
                }
            }
        }
    }
}fun main(args: Array<String>) {
    Server(5555).start()
}