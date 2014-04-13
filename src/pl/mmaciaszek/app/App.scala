package pl.mmaciaszek.app

import net.sf.mpxj.mpp.MPPReader
import net.sf.mpxj.mspdi.MSPDIWriter
import net.sf.mpxj.ProjectFile
import net.sf.mpxj.Task
import scala.collection.JavaConverters._
import core.eval.Eval
import pl.mmaciaszek.env.Env
import pl.mmaciaszek.project._
import core.ProjectCloner
import pl.mmaciaszek.alghoritm.greedy.GreedyAlghoritm
import pl.mmaciaszek.alghoritm.greedy.TypeOptimization
import pl.mmaciaszek.alghoritm.bnb.BranchAndBoundAlghoritm

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
    
    // CREATE PROJECTS
    val dataFile                = FileOperation  .readDataFile     ("15_3_5_3.mpp")
    val project                 = new MPPReader().read             (dataFile      )
    val projectForGreedyAlgCOST = ProjectCloner  .createBaseProject(project, false)
    val projectForGreedyAlgTIME = ProjectCloner  .createBaseProject(project, false)
    val projectForBandBAlgCOST  = ProjectCloner  .createBaseProject(project, false)    
    val projectForBandBAlgTIME  = ProjectCloner  .createBaseProject(project, false)
    
    println("--- FIRST INFO ---")
//    TaskIteration.iterationOverTasks                 (project)
//    println("-----------------")
//    TaskIteration.iterationOverTaskByRelation        (project)
//    println("-----------------")
//    TaskIteration.iterationOverAssignments           (project)
//    println("-----------------")
//    TaskIteration.iterationOverAssignmentsByResources(project)
//    println("-----------------")
//    TaskIteration.iterationOverAssignmentsByTasks    (project)
//    println("-----------------")
    
    ProjectEvaluation.eval                  (project)
    ProjectEvaluation.getProjectCostInfo    (project)
    ProjectEvaluation.getProjectDurationInfo(project)
    
    println("--- GREEDY ALGHORITM COST OPTIMIZATION ---")
    val costEval = System.currentTimeMillis()
    GreedyAlghoritm  .eval                  (projectForGreedyAlgCOST)(TypeOptimization.COST)
    println("COST optimization time: " + (System.currentTimeMillis()-costEval))
    ProjectEvaluation.getProjectCostInfo    (projectForGreedyAlgCOST)
    ProjectEvaluation.getProjectDurationInfo(projectForGreedyAlgCOST)
    
    println("--- GREEDY ALGHORITM TIME OPTIMIZATION ---")
    val timeEval = System.currentTimeMillis()
    GreedyAlghoritm  .eval                  (projectForGreedyAlgTIME)(TypeOptimization.TIME)
    println("TIME optimization time: " + (System.currentTimeMillis()-timeEval))
    ProjectEvaluation.getProjectCostInfo	(projectForGreedyAlgTIME)
    ProjectEvaluation.getProjectDurationInfo(projectForGreedyAlgTIME)  
  }
 }