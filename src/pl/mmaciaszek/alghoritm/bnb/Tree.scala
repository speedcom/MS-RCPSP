package pl.mmaciaszek.alghoritm.bnb

import scala.collection.mutable.MutableList
import net.sf.mpxj.Task
import net.sf.mpxj.Resource

object Root {
  val tree = new MutableList[Tree]()
}
case class Tree(task: Task, resource: Resource, tree: MutableList[Tree])


