package org.cidarlab.eugene.rules;

import java.util.ArrayList;
import java.util.List;

public class RuleMemo {

	private List<String> lstErrorMessages;
	
	public RuleMemo() {
		this.lstErrorMessages = new ArrayList<String>();
	}
	
	public void addViolation(String sMessage) {
		this.lstErrorMessages.add(sMessage);
	}
	
	public List<String> getViolations() {
		return this.lstErrorMessages;
	}
}
