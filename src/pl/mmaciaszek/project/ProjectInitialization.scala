package pl.mmaciaszek.project

import net.sf.mpxj.ProjectFile
import scala.collection.JavaConverters._
import core.SkillsUtilities
import core.conflicts.ConflictFixer

object ProjectInitialization {

  def assignResourcesToTasks(project: ProjectFile) {
	  val tasks = project.getAllTasks().asScala.toList
	  tasks foreach { task => 
	  	val resources      = SkillsUtilities.resourcesCapablePerformingTask(task).asScala.toList
	  	val resource       = resources.reduceLeft((r1, r2) => {
	  	  if(r1.getStandardRate().getAmount() < r2.getStandardRate().getAmount()) {
	  	    r1
	  	  } else r2
	  	}) 
	  	val resourceAssign = task.addResourceAssignment(resource)
	  	
	  	resourceAssign.setStart        (task          .getStart                     ())
	  	resourceAssign.setWork         (task          .getDuration                  ())
	  	resourceAssign.setRemainingWork(resourceAssign.getWork                      ())
	  	resourceAssign.setCost         (resourceAssign.getWork()        .getDuration() * 
	  									resource      .getStandardRate().getAmount  ())
	  }
  }
  
  def packProject(project: ProjectFile) {
    ConflictFixer.pack(project)
  }
  def fixConflicts(project: ProjectFile) {
    ConflictFixer.fixConflicts(project)
  }
  
}