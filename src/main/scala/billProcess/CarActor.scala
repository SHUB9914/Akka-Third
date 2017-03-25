package billProcess

import akka.actor.{Actor, ActorRef, Props}
import caseClass.Billers
import dbRepo.DataBaseRepo

/**
  * Created by knoldus on 24/3/17.
  */
class CarActor(billerActorRef:ActorRef) extends Actor{
  override def receive = {
    case accNo:Long => billerActorRef ! (accNo,"Car")
  }

}
