package billProcess

import akka.actor.{Actor, Props}
import dbRepo.{AddUser, DataBaseRepo}

class BillProcessActor extends Actor{
  val billerActorRef = context.actorOf(Props[BillerActor].withDispatcher("my-dispatcher"), "Biller-Actor")
  override def receive = {

    case accNo:Long =>   val (user , billes)=DataBaseRepo.getter(accNo)
      val totalBills: List[String] = billes.map(_.category)

      totalBills.foreach{
        case "Car" => val ref = context.actorOf(Props(new CarActor(billerActorRef)).withDispatcher("my-dispatcher"), "Car-Actor")
          ref ! accNo
        case "Electricity" => val ref = context.actorOf(Props(new ElectricityActor(billerActorRef)).withDispatcher("my-dispatcher"), "Electricity-Actor")
          ref ! accNo
        case "Food" =>  val ref = context.actorOf(Props(new FoodActor(billerActorRef)).withDispatcher("my-dispatcher"), "Food-Actor")
          ref ! accNo
        case "Internet" =>val ref = context.actorOf(Props(new InternetActor(billerActorRef)).withDispatcher("my-dispatcher"), "Internet-Actor")
          ref ! accNo
        case "Phone" =>val ref = context.actorOf(Props(new PhoneActor(billerActorRef)).withDispatcher("my-dispatcher"), "Phone-Actor")
          ref ! accNo
        case _ => println("you give some wrong bills")
      }

  }

}
