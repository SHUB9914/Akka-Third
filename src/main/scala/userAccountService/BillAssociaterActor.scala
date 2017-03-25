package userAccountService

import akka.actor.Actor
import caseClass.Billers
import dbRepo.DataBaseRepo


class BillAssociaterActor extends Actor{
  override def receive = {
    case (accno:Long , bill:List[Billers]) =>
      val accNumbers: List[Long] = DataBaseRepo.getAllAccNo()
      if(accNumbers.contains(accno)){
        val (user , bills ) = DataBaseRepo.getter(accno)
        val newBills: List[Billers] =(bills++bill)
        val result = DataBaseRepo.setter(user,newBills)
      }
      else {
        println("sorry this Account Number does not exist so bill can not associate to it")
      }



  }
}
