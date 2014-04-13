package pl.mmaciaszek.alghoritm.greedy

import net.sf.mpxj.ProjectFile
import scala.collection.JavaConverters._
import core.SkillsUtilities
import net.sf.mpxj.Resource
import net.sf.mpxj.Task
import core.ProjectCloner
import pl.mmaciaszek.project.ProjectEvaluation
import pl.mmaciaszek.project.ProjectInitialization

object TypeOptimization extends Enumeration {
	val TIME, COST = Value
}

object GreedyAlghoritmOptimization {    
  def findBestAssignment(project: ProjectFile, task: Task, resources: List[Resource])(implicit typeOptimization: TypeOptimization.Value) = typeOptimization match {
    case TypeOptimization.COST => 
      var theBestResource = resources.head
      
      var mainProject     = ProjectCloner.createBaseProject(project, false)
      val mainProjectTask = mainProject  .getTaskByID      (task.getID()  )      
      mainProjectTask.addResourceAssignment(resources.head)
                    
      resources.tail foreach { resource =>
        val localProject      = ProjectCloner.createBaseProject(project, false)
        val localProjectTask  = localProject .getTaskByID      (task.getID()  )       
        localProjectTask.addResourceAssignment(resource)
        
      	if(ProjectEvaluation.getProjectCost(mainProject) > ProjectEvaluation.getProjectCost(localProject)) {
      	  theBestResource = resource
      	  mainProject     = ProjectCloner.createBaseProject(localProject, false)
      	}       
      }
      
      theBestResource
    case TypeOptimization.TIME =>
      var theBestResource = resources.head
      
      var mainProject     = ProjectCloner.createBaseProject(project, false)
      val mainProjectTask = mainProject  .getTaskByID      (task.getID()  )      
      mainProjectTask.addResourceAssignment(resources.head)
      ProjectInitialization.packProject (mainProject)
      ProjectInitialization.fixConflicts(mainProject)
      
      resources.tail foreach { resource => 
      	val localProject      = ProjectCloner.createBaseProject(project, false)
        val localProjectTask  = localProject .getTaskByID      (task.getID()  )       
        localProjectTask.addResourceAssignment(resource)
        ProjectInitialization.packProject (localProject)
        ProjectInitialization.fixConflicts(localProject)
        
        if(ProjectEvaluation.getProjectDuration(mainProject) > ProjectEvaluation.getProjectDuration(localProject)) {
      	  theBestResource = resource
      	  mainProject     = ProjectCloner.createBaseProject(localProject, false)
      	}
      }
      
      theBestResource
    case _					  => 
      throw new Exception("Wrong TypeOptimization")	
  }
}

object GreedyAlghoritm {
	
  def eval(project: ProjectFile)(implicit typeOptimization: TypeOptimization.Value) {
    val tasks = project.getAllTasks().asScala.toList
    tasks foreach { task => 
    	val resources      = SkillsUtilities.resourcesCapablePerformingTask(task).asScala.toList
    	val resource       = GreedyAlghoritmOptimization.findBestAssignment(project, task, resources)      	
    	val resourceAssign = task.addResourceAssignment(resource)

      resourceAssign.setStart(task.getStart())
      resourceAssign.setWork(task.getDuration())
      resourceAssign.setRemainingWork(resourceAssign.getWork())
      resourceAssign.setCost(resourceAssign.getWork().getDuration() * resource.getStandardRate().getAmount())
    }
    ProjectInitialization.packProject (project)
    ProjectInitialization.fixConflicts(project)
  }
}