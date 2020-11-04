package main.java

import java.io.{ObjectOutputStream, PrintStream, PrintWriter}
import java.net.Socket

class ClientS {
  def main(args: Array[String]): Unit = {
    clientsocket
  }

  def clientsocket: Unit ={
    val socket = new Socket("localHost", 4000)
      //val output = new ObjectOutputStream(socket.getOutputStream)
      val output = new PrintWriter(socket.getOutputStream, true)
      val simulated = new Simulator()
      val message = simulated.textGenerator(2)
        output.write(message.toString)
        //output.writeObject(simulated.messageAgregator())
  }

}
