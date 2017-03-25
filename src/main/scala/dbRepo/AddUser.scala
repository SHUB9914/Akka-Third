package dbRepo

import caseClass.{Billers, User}

import scala.collection.mutable

/**
  * Created by knoldus on 23/3/17.
  */
object AddUser {
  var users: mutable.Map[Long, (User,List[Billers])] = mutable.Map()

}
