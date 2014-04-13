package pl.mmaciaszek.project

import net.sf.mpxj.ProjectFile
import net.sf.mpxj.Task

import scala.collection.JavaConverters._

object TaskIteration {
   
 def iterationOverAssignments(project: ProjectFile) {
   val assignments = project.getAllResourceAssignments().asScala.toList
   assignments foreach { assignment =>
   		
    val msg	     = new StringBuffer("Przydzial: Zadanie= ")
    
    val task     = Option(assignment getTask    ())
   	val resource = Option(assignment getResource())
   		
   	task match {
      case Some(t) => msg append t.getName()  + " "
      case None	   => msg append "(puste zadanie) "
    }
    
    resource match {
      case Some(r) => msg append r.getName()
      case None    => msg append "(pusty zasob)"
    }
    
    println(msg)
   }
 }
 def iterationOverAssignmentsByTasks(project: ProjectFile) {
   val tasks = project.getAllTasks().asScala.toList
   tasks foreach { task => 
   	println("Przydzialy do zadania " + task.getName() + ":")
   	
   	val assignments = task.getResourceAssignments().asScala.toList
   	assignments foreach { assignment =>
   		val resource = Option(assignment.getResource())
   		resource match {
   		  case Some(r) => println("\t" + r.getName())
   		  case None    => println("\t (pusty zasob)")
   		}
   	}
   }
 }
 def iterationOverAssignmentsByResources(project: ProjectFile) {
   val resources = project.getAllResources().asScala.toList
   resources foreach { resource => 
   	println("Przydzialy dla zasobu " + resource.getName() + ":")
   	
   	val assignments = resource.getTaskAssignments().asScala.toList
   	assignments foreach { assignment => 
   		val task = assignment.getTask()
   		println("\t" + task.getName())
   	}
   }
 }
 
 def iterationOverTasks(project: ProjectFile) {
   val tasks = project.getAllTasks().asScala.toList
   tasks foreach { task => println("Zadanie: " + task.getName()) }
 }
 def iterationOverTaskByRelation(project: ProjectFile) {
   val tasks = project.getAllTasks().asScala.toList
   tasks foreach { task => 
   	 val successors = Option(task.getSuccessors())     
     successors.foreach { s =>
       println(task.getName() + " nastepniki:")
       s.asScala.foreach { successor => 
         println("Poprzednik: " + successor.getSourceTask())
         println("Nastepnik:  " + successor.getTargetTask())
         println("Typ:        "	+ successor.getType      ())
         println("Opoznienie: " + successor.getLag       ())	
         println()
       }
     }
   }
 }
 def iterationOverAgregatedTasks(project: ProjectFile) {
   val tasks = project.getChildTasks().asScala.toList
   tasks foreach { task => 		
		println("Zadanie: " + task.getName())
		listHierarchy(task, " ")
	}
	println()
	
	def listHierarchy(task: Task, indent: String) {
		val childs = task.getChildTasks().asScala.toList
	    childs foreach { child => 
			println(indent + "Zadanie: " + child.getName())
			listHierarchy(child, indent + " ")
		}
	} 
 }
 
}