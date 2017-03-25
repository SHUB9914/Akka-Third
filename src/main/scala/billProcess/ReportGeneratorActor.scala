package billProcess

/**
  * Created by knoldus on 24/3/17.
  */
import akka.actor.{Actor, ActorRef, ActorSystem}
import caseClass.Report

import scala.concurrent.duration._


class ReportGeneratorActor(dbRef: ActorRef, system: ActorSystem) extends Actor{

  override def receive = {
    case Report =>
      import system.dispatcher
      val cancellable =
        system.scheduler.schedule(
          10 second,
          10 second,
          dbRef,
          Report)
  }

}