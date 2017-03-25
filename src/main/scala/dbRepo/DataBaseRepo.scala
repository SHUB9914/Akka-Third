package dbRepo

import caseClass.{Billers, User}

/**
  * Created by knoldus on 23/3/17.
  */
object DataBaseRepo {

  def newUserSetter(user:User): String ={
    val accno = user.accNo
    AddUser.users += (accno -> (user,Nil))
    "Success"
  }
  def setter(user:User, bills:List[Billers]): String ={
    val accNo = user.accNo
    AddUser.users += (accNo -> (user,bills))
    "Success"

  }
  def getter(accNo:Long): (User, List[Billers]) ={
    val (user , bills ) = AddUser.users(accNo)
    (user , bills)

  }
  def getAllAccNo(): List[Long] ={
     AddUser.users.keys.toList

  }
  def billProcess(accNo:Long,category:String): String ={
    val (user , billes)=DataBaseRepo.getter(accNo)
    println("cata=========================="+category)
    val updatBill: List[Billers] =  billes.filter(_.category!=category)
    println(">>>>>>"+updatBill)
    val amount: List[Double] = billes.filter(_.category=="Car").map(_.paidAmount)
    val paidAmount = amount(0)

    if(user.intialAmount >=paidAmount){
      val netBalance = user.intialAmount-paidAmount
      val updateUser = user.copy(intialAmount = netBalance)
      println("////////////////////"+(updateUser,updatBill))
      val result = setter(updateUser,updatBill)
      "Success"
    }
    else {
      "Fail"
    }

  }

}
