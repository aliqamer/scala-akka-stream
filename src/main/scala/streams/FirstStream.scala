package streams

import akka.NotUsed
import akka.actor.ActorSystem
import akka.stream.ActorMaterializer
import akka.stream.scaladsl.Flow
import akka.stream.scaladsl.{Sink, Source}

object FirstStream extends App {

  implicit val actorSystem = ActorSystem("FirstStream")
// implicit val materializer = ActorMaterializer()

  //source
  val source = Source(1 to 100)

  //sink
  val sink = Sink.foreach[Int](println)

  val graph = source.to(sink)
//  graph.run()

  //flows transform elements
  val flow = Flow[Int].map(x => x + 1)
  val sourceWithFlow = source.via(flow)
  val flowWithSink = flow.to(sink)

  //sourceWithFlow.to(sink).run()
  // from name list print first 2 name with len > 5
  val names = List("Ali", "Bob", "Uncle", "Martin", "AkkaStream", "Scala", "charlie")
  val nameSource = Source(names)
  val flowFilter = Flow[String].filter(x => x.length > 5)
  val flowLimit = Flow[String].take(2)
  val sinkName = Sink.foreach[String](println)
  val nameGraph = nameSource.via(flowFilter).via(flowLimit).to(sinkName)
//  nameGraph.run()
  //below does same thing
  nameSource.filter(_.length > 5).take(2).runForeach(println)
}
