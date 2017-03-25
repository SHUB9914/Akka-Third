package billProcess

import akka.actor.{Actor, ActorLogging}
import caseClass.{Billers, Report, User}
import dbRepo.{AddUser, DataBaseRepo}

import scala.collection.mutable

/**
  * Created by knoldus on 24/3/17.
  */
class ReportProcess extends Actor with ActorLogging {
  override def receive = {
    case Report => //log.info("hello shubham")
      val information: mutable.Map[Long, (User, List[Billers])] = AddUser.users
      val accNumbers: List[Long] = information.keys.toList
      accNumbers.map{x=>
        val (user,bills) = DataBaseRepo.getter(x)
        val uname = user.userName
        val accno = user.accNo
        log.info(s"user name ====$uname and Account number is $accno")
        log.info(s"Bills Associated with this user ====$bills")
      }

  }

}
