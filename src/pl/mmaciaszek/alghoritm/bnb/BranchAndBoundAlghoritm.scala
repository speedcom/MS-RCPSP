package pl.mmaciaszek.alghoritm.bnb

import net.sf.mpxj.ProjectFile
import scala.collection.JavaConverters._
import scala.collection.mutable.MutableList
import net.sf.mpxj.Task
import core.SkillsUtilities
import core.ProjectCloner
import pl.mmaciaszek.alghoritm.greedy.TypeOptimization
import net.sf.mpxj.Resource

object BranchAndBoundAlghoritm {
	def eval(projectFile: ProjectFile)(implicit typeOptimization: String) {
	  
	    val project			 = ProjectCloner.createBaseProject(projectFile, false) 
	    val root 			 = Root.tree
	    val assignedTasksIDs = List()
		val sortedTasks 	 = sortTasksByStartTime(projectFile)
		var tree 		     = root 
		val schedule		 = List[(Task, Resource)]()
		
		while(assignedTasksIDs.size != sortedTasks.size) {
		  sortedTasks foreach { task =>
		    if(assignedTasksIDs.exists(_ != task.getID())) {
		      val resourcesForTask = SkillsUtilities.resourcesCapablePerformingTask(task).asScala.toList
		      resourcesForTask foreach { resource =>
				tree += Tree(task, resource, new MutableList[Tree]())
			  }
		    }
		  }
		  // wybierzmy lisc po DO lub CO
		  typeOptimization match {
		    case TypeOptimization.COST => 
		      
		    case TypeOptimization.TIME =>
		    	tree.sortBy(_.task.getDuration())
		    case _					   => throw new Exception("Wrog TypeOptimization")
		  }
		}
		
		
	}
	
	
	def sortTasksByStartTime(project: ProjectFile) = {
	  val tasks       = project.getAllTasks().asScala.toList
	  val sortedTasks = tasks  .sortBy(_.getStart())
	  sortedTasks
	}
}