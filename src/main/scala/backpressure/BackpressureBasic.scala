package backpressure

import akka.actor.ActorSystem
import akka.stream.OverflowStrategy
import akka.stream.scaladsl.{Flow, Sink, Source}

object BackpressureBasic extends App {

  implicit val actorSystem = ActorSystem("Backpressure")

  val fastSource = Source(1 to 1000)
  val slowSink = Sink.foreach[Int](x => {
    Thread.sleep(1000)
    println(s"Sink: $x")
  })

  //everything runs on single actor
//  fastSource.to(slowSink).run()

  //no backpressure
//  fastSource.async.to(slowSink).run()

  val simpleFlow = Flow[Int].map(x => {
    println(s"Incoming: $x")
    x + 1
  })

  //backpressure
//  fastSource.async
//    .via(simpleFlow).async
//    .to(slowSink).run()

  /*
    reactions to backpressure (in order)
    - try to slow down if possible
    - buffer elements until there is demand
    - drop down elements from the buffer if it overflows
    - tear down/kill the whole stream (failure)
   */

  //drop most recent element
  /*
    1-16 nobody backpressured
    17-26 flow will buffer, flow will start dropping at next element
    26-1000 flow will always drop the oldest element
    991- 1000 => 992 - 1001 => sink
   */
//  val bufferedFlow = simpleFlow.buffer(10, overflowStrategy = OverflowStrategy.dropHead)
//  fastSource.async
//    .via(bufferedFlow).async
//    .to(slowSink).run()

  //throttling
  import scala.concurrent.duration._
  import scala.language.postfixOps
  fastSource.throttle(3, 1 second).runWith(Sink.foreach(println))
}
