package pl.mmaciaszek.app

import net.sf.mpxj.mpp.MPPReader
import net.sf.mpxj.mspdi.MSPDIWriter
import net.sf.mpxj.ProjectFile
import net.sf.mpxj.Task
import scala.collection.JavaConverters._
import core.eval.Eval
import pl.mmaciaszek.env.Env
import pl.mmaciaszek.task.TaskOperation

object FileOperation {
  def readDataFile(fileName: String) = Env.userDir + "\\test-data\\" + fileName
  def saveProjectFile(project: ProjectFile, fileName: String) {
    val outputFilePathFolder = Env.userDir 			+ "\\output-data\\"
    val outputFile			 = outputFilePathFolder + fileName
    new MSPDIWriter().write(project, outputFile)
    println("Success. File was saved.")
  } 
}

object App {

  def main(args: Array[String]): Unit = {
    
    // READ FILE
    val fileName = "10_3_5_3.mpp"
    val dataFile = FileOperation  .readDataFile(fileName)
    val project  = new MPPReader().read        (dataFile)
    
      
//    TaskOperation.iterationOverTaskByRelation(project)
    TaskOperation.iterationOverAssignments   (project)
    
    // SUMMARY TASK
//    val projectTasks = project.getChildTasks()
//    val task  		 = projectTasks  .get(0)
//    val tasks		 = task.getChildTasks()
    
    // AGREGATED TASKS
  // TaskOperation.listHierarchy(project)
   
   // ITERATION OVER TASKS RELATIONS
   TaskOperation.iterationOverTaskByRelation(project)
    
//   val time_component_weight = 0.4
//   val eval 				 = Eval..evalScheduling(project, time_component_weight)
//   println("Wartosc funkcji oceny dla projektu: " + eval)
  
//  println("Czas trwania projektu: "    + Eval.getProjectDuration(project))
//  println("Koszt realizacji projektu: "+ Eval.getProjectCost    (project))
  }
}