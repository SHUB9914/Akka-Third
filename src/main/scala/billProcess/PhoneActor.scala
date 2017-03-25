package billProcess

import akka.actor.{Actor, ActorRef}

/**
  * Created by knoldus on 24/3/17.
  */
class PhoneActor(billerActorRef:ActorRef) extends Actor{
  override def receive = {
    case accNo:Long =>  billerActorRef ! (accNo,"Phone")
  }
}
