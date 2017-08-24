package cd;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.util.HashSet;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import cd.commits.ErrorCommit;
import cd.commits.FatalErrorCommit;

public class PipelineTest {

	Pipeline pipeline;
	Codebase codebase;
	
	@Before
	public void setUp() throws Exception {
		pipeline = new Pipeline();
	}
	
	@Test
	public void newCodebaseEntersTestingInmediatelyInEmptyPipeline(){
		Build b = givenBuildWithValidationTime(0);
		whenRunThroughPipeline(b);
		thenBuildIsIn(b, Stages.TEST);
	}

	@Test
	public void codebaseNotDetectingErrorsMakesItToProdAfterRunningAllTests() {
		Build b = givenBuildWithValidationTime(0);
		whenRunThroughPipeline(b);
		givenTimeStepsElapsed(1);
		thenBuildIsIn(b, Stages.PROD);
	}
	
	@Test
	public void newCodebaseWaitsForTestIfItIsAlreadyBusy(){
		Build b1 = givenBuildWithValidationTime(10);
		Build b2 = givenBuildWithValidationTime(10);
		whenRunThroughPipeline(b1);
		whenRunThroughPipeline(b2);
		thenBuildIsIn(b1, Stages.TEST);
		thenBuildIsIn(b2, Stages.WAITING_FOR_TEST);
	}
		
	@Test
	public void codebasePassingValidationsMovesToProd(){
		Build b = givenBuildWithValidationTime(10);
		whenRunThroughPipeline(b);
		givenTimeStepsElapsed(10);
		thenBuildIsIn(b, Stages.PROD);
	}

	@Test
	public void ifNotEnoughTimeHasPassedForValidationToHaveFinishedCodebaseStaysInTest(){
		Build b = givenBuildWithValidationTime(10);
		whenRunThroughPipeline(b);
		givenTimeStepsElapsed(5);
		thenBuildIsIn(b, Stages.TEST);
	}

	
	@Test
	public void codebaseWaitingForTestMovesToTestWhenValidationIsFinished(){
		Build b1 = givenBuildWithValidationTime(10);
		Build b2 = givenBuildWithValidationTime(10);
		whenRunThroughPipeline(b1);
		whenRunThroughPipeline(b2);
		givenTimeStepsElapsed(10);
		thenBuildIsIn(b2, Stages.TEST);
	}

	@Test
	public void newCodebasePushedIntoPipelineBumpsOutPreviousCodebaseWaitingForTest(){
		Build b1 = givenBuildWithValidationTime(10);
		Build b2 = givenBuildWithValidationTime(10);
		Build b3 = givenBuildWithValidationTime(10);
		whenRunThroughPipeline(b1);
		whenRunThroughPipeline(b2);
		whenRunThroughPipeline(b3);
		thenBuildIsIn(b3, Stages.WAITING_FOR_TEST);
	}

	@Test
	public void emptyPipelineDoesntGenerateValue(){
		thenAccumulatedValueIs(0);
	}
	
	@Test
	public void pipelineWithNothingInProdDoesntGenerateValue(){
		Build b1 = givenBuildWithValidationTime(10);
		Build b2 = givenBuildWithValidationTime(10);
		whenRunThroughPipeline(b1);
		whenRunThroughPipeline(b2);
		thenAccumulatedValueIs(0);
	}
	
	@Test
	public void pipelineWithCodebaseInProdIncrementsValueByCodebaseValueInEachStep(){
		Build b = givenBuildWithValidationTimeAndValue(1, 11);
		whenRunThroughPipeline(b);
		givenTimeStepsElapsed(1);
		thenAccumulatedValueIs(0);
		givenTimeStepsElapsed(10);
		thenAccumulatedValueIs(110);
	}
	
	@Test
	public void complexAccumulatedValueWithTwoDifferentCodebasesMakingItToProd(){
		Build b1 = givenBuildWithValidationTimeAndValue(5, 10);
		Build b2 = givenBuildWithValidationTimeAndValue(10, 20);
		whenRunThroughPipeline(b1);
		whenRunThroughPipeline(b2);
		givenTimeStepsElapsed(7);
		thenAccumulatedValueIs(20);
		givenTimeStepsElapsed(10);
		thenBuildIsIn(b2, Stages.PROD);
		thenAccumulatedValueIs(140);
	}
	
	@Test
	public void returnErrorsOfFinishedBuildInTest(){
		Build b1 = givenBuildWithValidationTimeAndValue(5, 10);
		HashSet<Functionality> detectedErrors = new HashSet<Functionality>();
		givenBuildWithDetectedErrors(b1, detectedErrors);
		whenRunThroughPipeline(b1);
		givenTimeStepsElapsed(5);
		thenPipelineReturnsDetectedErrors(detectedErrors);
	}
	
	@Test
	public void returnsErrorsOfLastTestedIfCurrentTestIsOngoing(){
		Build b1 = givenBuildWithValidationTimeAndValue(5, 10);
		HashSet<Functionality> detectedErrors1 = new HashSet<Functionality>();
		detectedErrors1.add(new Functionality(new ErrorCommit(1)));
		givenBuildWithDetectedErrors(b1, detectedErrors1);
		Build b2 = givenBuildWithValidationTimeAndValue(10, 20);
		HashSet<Functionality> detectedErrors2 = new HashSet<Functionality>();
		detectedErrors2.add(new Functionality(new ErrorCommit(2)));
		givenBuildWithDetectedErrors(b2, detectedErrors2);
		whenRunThroughPipeline(b1);
		whenRunThroughPipeline(b2);
		givenTimeStepsElapsed(7);
		thenPipelineReturnsDetectedErrors(detectedErrors1);
	}
	
	@Test
	public void fatalErrorsInProdAreDetected(){
		HashSet<Functionality> fatalErrors = new HashSet<Functionality>();
		fatalErrors.add(new Functionality(new FatalErrorCommit(12)));
		fatalErrors.add(new Functionality(new FatalErrorCommit(23)));
		Build b = givenBuildWithFatalErrorAndNoTests(fatalErrors);
		whenRunThroughPipeline(b);
		givenTimeStepsElapsed(1);
		thenBuildIsIn(b,Stages.PROD);
		thenFatalErrorsAreDetected(b, fatalErrors);
	}
		
	private void thenPipelineReturnsDetectedErrors(HashSet<Functionality> expectedErrors) {
		Set<Functionality> detectedErrors = pipeline.getDetectedErrors();
		assertEquals(expectedErrors, detectedErrors);
	}

	private void givenBuildWithDetectedErrors(Build build, HashSet<Functionality> detectedErrors) {
		when(build.getDetectedErrors()).thenReturn(detectedErrors);
	}

	private void thenFatalErrorsAreDetected(Build build, Set<Functionality> fatalErrors) {
		Set<Functionality> detectedErrors = pipeline.getDetectedErrors();
		assertEquals(fatalErrors.size(), detectedErrors.size());
		assertTrue(fatalErrors.containsAll(detectedErrors));
		assertTrue(detectedErrors.containsAll(fatalErrors));
	}

	private Build givenBuildWithFatalErrorAndNoTests(Set<Functionality> fatalErrors) {
		Build b = Mockito.mock(Build.class);
		when(b.detectsErrors()).thenReturn(false);
		when(b.getErrors()).thenReturn(fatalErrors);
		return b;
	}

	private Build givenBuildWithValidationTimeAndValue(int validationTime, int value) {
		Build b = Mockito.mock(Build.class);
		when(b.detectsErrors()).thenReturn(false);
		when(b.getValidationTime()).thenReturn(validationTime);
		when(b.getValue()).thenReturn(value);
		return b;
	}

	private void thenAccumulatedValueIs(int value) {
		assertEquals(value, pipeline.getAccumulatedValue());
	}

	private void thenBuildIsIn(Build b, Stages stage) {
		assertEquals(stage, pipeline.stageOf(b));
	}

	private void givenTimeStepsElapsed(int timeSteps) {
		for(int i=0; i<timeSteps; i++){
			pipeline.timeStepElapsed();
		}
	}

	private void whenRunThroughPipeline(Build b) {
		pipeline.push(b);
	}

	private Build givenBuildWithValidationTime(int validationTime) {
		Build b = Mockito.mock(Build.class);
		when(b.detectsErrors()).thenReturn(false);
		when(b.getValidationTime()).thenReturn(validationTime);
		return b;
	}

}
