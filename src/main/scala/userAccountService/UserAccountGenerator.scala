package userAccountService

import akka.actor.{Actor, ActorLogging}
import caseClass.{Billers, User}
import dbRepo.{AddUser, DataBaseRepo}


/**
  * Created by knoldus on 23/3/17.
  */
class UserAccountGenerator extends Actor with ActorLogging{
  override def receive = {

    case user: User =>
      val accNo: List[Long] = DataBaseRepo.getAllAccNo()
      if(accNo.isEmpty){
        DataBaseRepo.newUserSetter(user)
      } else{
        val values: Iterable[User]  =  AddUser.users.values.map(_._1)
        val usr: List[User] =  values.filter(_.userName==user.userName).toList
        if(usr.isEmpty){
          DataBaseRepo.newUserSetter(user)
        } else log.info("sorry this username already exist")

      }

    case _ => log.info("plz give user properly")
  }
}
