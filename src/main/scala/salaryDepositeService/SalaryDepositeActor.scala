package salaryDepositeService

import akka.actor.{Actor, ActorRef}
import dbRepo.{AddUser, DataBaseRepo}

/**
  * Created by knoldus on 23/3/17.
  */
class SalaryDepositeActor(BillProcessActorRef:ActorRef) extends Actor{
  override def receive = {
    case (accno:Long,salary:Double) =>

      val (user , bills )=DataBaseRepo.getter(accno)
      val totalSalary = user.intialAmount+salary
      val updateUser = user.copy(intialAmount = totalSalary)
      val msg: String = DataBaseRepo.setter(updateUser,bills)

      if(msg=="Success") //println("salary update successFully---" + DataBaseRepo.getter(accno))
      BillProcessActorRef ! accno

    case _ => println("your account number should be in Long and salary should be Double")
  }
}
