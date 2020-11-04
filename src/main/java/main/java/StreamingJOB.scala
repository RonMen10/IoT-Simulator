package main.java

import java.io.{BufferedReader, BufferedWriter, FileWriter, InputStreamReader, ObjectInputStream}
import java.net.ServerSocket
import java.io.PrintWriter

import org.apache.spark.sql.catalyst.util.StringUtils

import scala.tools.scalap.scalax.util.StringUtil

/** No used imports due to spark Streaming Context no working **/

import org.apache.spark.{SparkConf, SparkContext}
import com.univocity.parsers.csv.CsvWriter
import org.apache.spark.streaming.dstream.DStream
import org.apache.spark.streaming.{Seconds, StreamingContext}




object StreamingJOB {
  def main(args: Array[String]): Unit = {
    val clientS = new ClientS()
    val ss = new ServerSocket(4000) /** Socket connection on port: 4000 */

    /**Storage file creation and header insertion*/
    val outputFile = new PrintWriter(new FileWriter("out/Output.csv"))
    outputFile.println("device-id,temperature,latitude,longitude,timestamp")

    var i = 0

    /** While loop to simulate the streaming of the information received bay the server socket */

    while (i < 15) {
      val time = System.currentTimeMillis()
      clientS.clientsocket
      val socket = ss.accept()
      val inputStream = new ObjectInputStream(socket.getInputStream())
      val simulated = inputStream.readObject().asInstanceOf[Simulator]

      /** Three messages received from the simulated devices */
      outputFile.println(simulated.message1)
      outputFile.println(simulated.message2)
      outputFile.println(simulated.message3)

      i += 1
      Thread.sleep(1000 -time % 1000)
    }

    outputFile.close()

    /** Coding done for Streaming task not working due to Spark streaming context error **/
    /*clientS.clientsocket
    //val socket = ss.accept()
    val conf = new SparkConf().setAppName("StreamingJob").setMaster("local[2]")
    val sc = new SparkContext(conf)
    val ssc = new StreamingContext(sc, Seconds(1))
    val streaRDD = ssc.socketTextStream("127.0.0.1", 4000)
    //val lines = streaRDD.foreachRDD(rdd => rdd.flatMap(_.split(",")).saveAsTextFile("out/Output.text"))
    val lines = streaRDD.filter(line=>line.contains("c"))
    lines.saveAsTextFiles("out/veamos.text")

    ssc.start()
    ssc.awaitTermination()*/



  }
}