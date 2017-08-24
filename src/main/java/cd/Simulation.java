package cd;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.logging.Logger;

import cd.commits.Commit;
import cd.programmer.ImperfectProgrammer;

import cd.programmer.Programmer;

public class Simulation {
	
	private final static Logger LOGGER = Logger.getLogger(Simulation.class.getName());

	List<Programmer> programmers;
	Codebase codebase;
	Pipeline pipeline;
	
	public Simulation(){
		programmers = new ArrayList<Programmer>();
		codebase = new Codebase();
		pipeline = new Pipeline();
	}
	
	private void runStep() {
		for(Programmer programmer : programmers){
			Commit commit = programmer.code();
			LOGGER.info(commit.toString());
			codebase.addCommit(commit);
			pipeline.push(codebase.build());
		}
		pipeline.timeStepElapsed();
		Set<Functionality> detectedErrors = pipeline.getDetectedErrors();
		for(Programmer programmer : programmers){
			programmer.errorsDetected(detectedErrors);
		}
		LOGGER.info(pipeline.toString());
	}

	private void addProgrammer(Programmer programmer) {
		programmers.add(programmer);
	}
	
	public static void main(String[] args){
		LOGGER.info("Starting the simulation");
		Simulation simulation = new Simulation();
		addImperfectProgrammer(simulation);
		for(int i=0; i<200; i++){
			LOGGER.info("Step:" + i);
			simulation.runStep();
			LOGGER.info(simulation.getStatistics());
		}
	}

	private static void addImperfectProgrammer(Simulation simulation) {
		ImperfectProgrammer programmer = new ImperfectProgrammer();
		programmer.setErrorRate(0.1);
		programmer.setFatalErrorRate(0.3);
		programmer.setFixRate(0.75);
		programmer.setTestRate(0.2);
		programmer.setMaxTestExecutionTime(10);
		programmer.setRandomSeed(100);
		simulation.addProgrammer(programmer);
	}

	private String getStatistics() {
		Build prod = pipeline.getBuildIn(Stages.PROD);
		Build test = pipeline.getBuildIn(Stages.TEST);
		Build wait = pipeline.getBuildIn(Stages.WAITING_FOR_TEST);
		StringBuffer sb = new StringBuffer();
		if(prod != null){
			sb.append("PROD - ").append(prod.toString()).append("\n");
		}
		if(test != null){
			sb.append("TEST - ").append(test.toString()).append("\n");
		}
		if(wait != null){
			sb.append("WAIT - ").append(wait.toString()).append("\n");
		}
		return sb.toString();
	}
	
}
