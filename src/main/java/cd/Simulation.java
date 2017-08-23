package cd;

import java.util.ArrayList;
import java.util.List;

import cd.commits.Commit;
import cd.programmer.PerfectProgrammer;
import cd.programmer.Programmer;

public class Simulation {

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
			codebase.addCommit(commit);
			pipeline.push(codebase);
		}
		pipeline.timeStepElapsed();
	}

	private void addProgrammer(Programmer programmer) {
		programmers.add(programmer);
	}
	
	public static void main(String[] args){
		Simulation simulation = new Simulation();
		Programmer programmer = new PerfectProgrammer();
		programmer.setRandomSeed(100);
		simulation.addProgrammer(programmer);
		for(int i=0; i<100; i++){
			simulation.runStep();
			System.out.println("step:"+i+","+simulation.getStatistics());
		}
	}

	private String getStatistics() {
		StringBuffer sb = new StringBuffer();
		sb.append("value:");
		sb.append(pipeline.getAccumulatedValue());
		return sb.toString();
	}


	
}
