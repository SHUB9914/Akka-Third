package billProcess

import akka.actor.{Actor, ActorRef}
import caseClass.Billers
import dbRepo.DataBaseRepo

/**
  * Created by knoldus on 24/3/17.
  */
class InternetActor(billerActorRef:ActorRef) extends Actor {
  override def receive = {
    case accNo:Long =>  billerActorRef ! (accNo,"Internet")
  }
}
