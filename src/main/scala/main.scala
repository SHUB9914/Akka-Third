import akka.actor.{ActorSystem, Props}
import caseClass.{Billers, Report, User}
import dbRepo.AddUser
import salaryDepositeService.SalaryDepositeActor
import userAccountService.{AccountService, BillAssociaterActor, UserAccountGenerator}
import akka.pattern.ask
import akka.util.Timeout
import billProcess.{BillProcessActor, ReportGeneratorActor, ReportProcess}
import com.typesafe.config.ConfigFactory

import scala.concurrent.Future
import scala.concurrent.duration._
import scala.util.{Failure, Success}
import scala.concurrent.ExecutionContext.Implicits.global


/**
  * Created by knoldus on 23/3/17.
  */
object main extends App{
   val conf = ConfigFactory.load()
   val system = ActorSystem("Process", conf)
 // val system = ActorSystem("AccountingSystem")

  val UserAccountGeneratorRef = system.actorOf(Props[UserAccountGenerator].withDispatcher("my-dispatcher"), "myactor1")
  val BillProcessActorRef=system.actorOf(Props[BillProcessActor].withDispatcher("my-dispatcher"), "myactor2")
  val SalaryDepositeActorRef=system.actorOf(Props(new SalaryDepositeActor(BillProcessActorRef)).withDispatcher("my-dispatcher"), "myactor3")
  val BillAssociaterActorRef = system.actorOf(Props[BillAssociaterActor].withDispatcher("my-dispatcher"))

  val user1 = User(1234,"shubham","noida","shubham123@gmail.com",500.0)
  val user2 = User(123456,"Rahul","Agra","Rahul123@gamil.com",1000.0)
  val bill1 = Billers("Car","shubham",1234,"1/1/2001",500.0,3,2,1000)
  val bill2 = Billers("Food","shubham",1234,"1/1/2001",500.0,3,2,1000)
  val bill3 = Billers("Car","Rahul",1234,"1/1/2001",500.0,3,2,1000)
  val bill4 = Billers("Food","Rahul",1234,"1/1/2001",500.0,3,2,1000)

  val AccountServiceObj = new AccountService(UserAccountGeneratorRef,BillAssociaterActorRef)


  AccountServiceObj.addUser(user1)
  AccountServiceObj.addUser(user2)
  Thread.sleep(100)
  AccountServiceObj.billes(1234,List(bill1))
  AccountServiceObj.billes(1234,List(bill2))
  AccountServiceObj.billes(123456,List(bill3))
  AccountServiceObj.billes(123456,List(bill4))
  println(AddUser.users.toString())
  implicit val timeout = Timeout(1000 seconds)

  SalaryDepositeActorRef ! (1234.toLong,1000.toDouble)
  //Thread.sleep(1000)
  println(AddUser.users.toString())

 Thread.sleep(1000)
  println("----------------------------"+AddUser.users.toString())
  val ReportProcessRef = system.actorOf(Props[ReportProcess].withDispatcher("my-dispatcher"))
  val ReportGeneratorActorRef = system.actorOf(Props(new ReportGeneratorActor(ReportProcessRef,system)).withDispatcher("my-dispatcher"))
 ReportGeneratorActorRef ! Report




}
