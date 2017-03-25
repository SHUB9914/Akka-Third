package userAccountService

import akka.actor.{ActorRef}
import caseClass.{Billers, User}


class AccountService(userAccountGeneratorRef:ActorRef , BillAssociaterActorRef:ActorRef) {

  def addUser(user:User): Unit ={
    userAccountGeneratorRef ! user
  }
  def billes(accno:Long , bill:List[Billers]): Unit ={

    BillAssociaterActorRef ! (accno,bill)
  }

}
