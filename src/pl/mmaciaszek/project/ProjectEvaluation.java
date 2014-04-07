package pl.mmaciaszek.project;

import core.eval.Eval;
import net.sf.mpxj.ProjectFile;

public class ProjectEvaluation {
	public static void getProjectDurationInfo(ProjectFile project) {
		System.out.println("Czas trwania projektu: "     + Eval.getProjectDuration(project));   
	}
	
	public static void getProjectCostInfo(ProjectFile project) {
		System.out.println("Koszt realizacji projektu: " + Eval.getProjectCost    (project));
	}
	
	public static int  getProjectCost(ProjectFile project) { return Eval.getProjectCost  (project); }
	
	public static double getProjectDuration(ProjectFile project) { return Eval.getProjectDuration(project); }
	
	public static void eval(ProjectFile project) {
		double time_component_weight = 0.4;
		double eval 				 = Eval.evalScheduling(project, time_component_weight);
		System.out.println("Wartosc funkcji oceny dla projektu: " + eval);
	}
}
