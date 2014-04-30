package org.cidarlab.eugene.examples;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import org.cidarlab.eugene.EugeneExecutor;
import org.cidarlab.eugene.dom.SavableElement;


public class RunExamples {

	// just uncomment what example to run
	public static void main(String[] args) {

		// Priority Encoder
//		new RunExamples().test("./examples/priority-encoder.eug");

		// Lu's NOR
//		new RunExamples().test("./examples/invertase-nor.eug");

		// TOGGLE-SWITCH
//		new RunExamples().test("./examples/toggle-switch.eug");
		
		// REPRESSILATOR
//		new RunExamples().test("./examples/repressilator.eug");
		
		// NOR Gate
//		new RunExamples().test("./examples/nor-gate.eug");

		// Counters
//		new RunExamples().test("./examples/counters.eug");
		
	}

	public void test(String sFile) {
		try {
			long t1 = System.nanoTime();
			Map<String, SavableElement> m = 
					(HashMap<String, SavableElement>)EugeneExecutor.execute(new File(sFile), 2);
			long tProcessing = System.nanoTime() - t1;
			
			System.out.println("processing time: "+tProcessing*Math.pow(10, -9)+"sec");
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
}
