package billProcess

import akka.actor.{Actor, Props}
import dbRepo.{AddUser, DataBaseRepo}

class BillProcessActor extends Actor{
  val billerActorRef = context.actorOf(Props[BillerActor])
  override def receive = {

    case accNo:Long =>   val (user , billes)=DataBaseRepo.getter(accNo)
      val totalBills: List[String] = billes.map(_.category)

      totalBills.foreach{
        case "Car" => val ref = context.actorOf(Props(new CarActor(billerActorRef)))
          ref ! accNo
        case "Electricity" => val ref = context.actorOf(Props[ElectricityActor])
          ref ! accNo
        case "Food" =>  val ref = context.actorOf(Props(new FoodActor(billerActorRef)))
          ref ! accNo
        case "Internet" =>val ref = context.actorOf(Props(new InternetActor(billerActorRef)))
          ref ! accNo
        case "Phone" =>val ref = context.actorOf(Props(new PhoneActor(billerActorRef)))
          ref ! accNo
        case _ => println("you give some wrong bills")
      }

  }

}
