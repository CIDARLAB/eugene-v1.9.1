/*
Copyright (c) 2012 Boston University.
All rights reserved.
Permission is hereby granted, without written agreement and without
license or royalty fees, to use, copy, modify, and distribute this
software and its documentation for any purpose, provided that the above
copyright notice and the following two paragraphs appear in all copies
of this software.

IN NO EVENT SHALL BOSTON UNIVERSITY BE LIABLE TO ANY PARTY
FOR DIRECT, INDIRECT, SPECIAL, INCIDENTAL, OR CONSEQUENTIAL DAMAGES
ARISING OUT OF THE USE OF THIS SOFTWARE AND ITS DOCUMENTATION, EVEN IF
BOSTON UNIVERSITY HAS BEEN ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.

BOSTON UNIVERSITY SPECIFICALLY DISCLAIMS ANY WARRANTIES,
INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF
MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE. THE SOFTWARE
PROVIDED HEREUNDER IS ON AN "AS IS" BASIS, AND BOSTON UNIVERSITY HAS
NO OBLIGATION TO PROVIDE MAINTENANCE, SUPPORT, UPDATES,
ENHANCEMENTS, OR MODIFICATIONS.
 */

package org.cidarlab.eugene;

import java.io.File;
import java.util.HashMap;
import java.util.logging.LogManager;

import org.cidarlab.eugene.dom.SavableElement;
import org.cidarlab.eugene.util.EugeneUtil;


/**
 * @author Ernst Oberortner
 */
public class Eugene {

	private final String NEWLINE = System.getProperty("line.separator");

	public Eugene(String sEugeneFile) {
		LogManager.getLogManager().reset();
		File eugFile = new File(sEugeneFile);
		try {

			/**
			// 1 ... print the output set to the console
			String[] results = (String[]) EugeneExecutor.execute(eugFile, 1);
			if (null != results && results.length > 0) {
				EugeneUtil.writeToFile(this.toString(results), new File(
						sEugeneFile + ".out"));
			}
			**/
			
			HashMap<String, SavableElement> results = (HashMap<String, SavableElement>) EugeneExecutor.execute(eugFile, 2);

			// 2 ... the EugeneExecutor returns a list of elements which were annotated with the save() function
			if(null!=results && !results.isEmpty()) {
				
				StringBuilder sb = new StringBuilder();
				for(String s:results.keySet()) {
					SavableElement se = results.get(s);
					sb.append(se.toString()).append(NEWLINE);
				}
				EugeneUtil.writeToFile(sb.toString(), new File(
						sEugeneFile + ".out"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
//		System.gc();

	}

	private String toString(String[] devices) {

		StringBuffer sb = new StringBuffer();
		if (null != devices) {
			for (int i = 0; i < devices.length; i++) {
				sb.append(devices[i]).append(";").append(NEWLINE);
			}
		}
		return sb.toString();
	}

	public static void main(String[] args) {
		if(args.length!=1) {
			System.err.println("Usage: java -jar eugene.jar <eugene-file>");
			System.exit(1); 
		} 
		new Eugene(args[0]);
	}
}