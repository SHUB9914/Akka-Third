package billProcess

import akka.actor.Actor
import caseClass.Billers
import dbRepo.{AddUser, DataBaseRepo}

/**
  * Created by knoldus on 25/3/17.
  */
class BillerActor extends Actor{
  override def receive = {

    case (accNo:Long,category:String) =>

      val (user , billes)=DataBaseRepo.getter(accNo)
      val updatBill: List[Billers] =  billes.filter(_.category!=category)
      val amount: List[Double] = billes.filter(_.category==category).map(_.paidAmount)
      val paidAmount = amount(0)

      if(user.intialAmount >=paidAmount){
        val netBalance = user.intialAmount-paidAmount
        val updateUser = user.copy(intialAmount = netBalance)
        val result = DataBaseRepo.setter(updateUser,updatBill)
      }
      else {
        println("amount is not fulfill for this transection-----------"+category)
      }
  }

}
