package org.cidarlab.eugene.rules.tree.predicate;

import java.util.List;

import org.cidarlab.eugene.dom.components.Component;
import org.cidarlab.eugene.dom.components.Device;
import org.cidarlab.eugene.exception.EugeneException;
import org.cidarlab.eugene.rules.LogicalOperator;

import JaCoP.constraints.Constraint;
import JaCoP.core.IntVar;
import JaCoP.core.Store;

public class LogicalXor 
	implements LogicalPredicate {
	
	private Predicate A;
	private Predicate B;
	
	public LogicalXor(Predicate A, Predicate B) {
		this.A = A;
		this.B = B;
	}
	
	public Predicate getA() {
		return this.A;
	}

	public Predicate getB() {
		return this.B;
	}
	
	@Override
	public String getOperator() {
		return LogicalOperator.XOR.toString();
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(this.getA()).append(" XOR ").append(this.getB());
		return sb.toString();
	}
	


	@Override
	public boolean evaluate(long[] l) 
			throws EugeneException {
		return this.getA().evaluate(l) ^ this.getB().evaluate(l);
	}
	
	@Override
	public boolean evaluate(long deviceId) 
			throws EugeneException {
		return this.getA().evaluate(deviceId) ^ this.getB().evaluate(deviceId);
	}
	
	@Override
	public boolean evaluate(String sDeviceName) 
			throws EugeneException {
		return this.getA().evaluate(sDeviceName) ^ this.getB().evaluate(sDeviceName);
	}

	@Override
	public boolean evaluate(Device device) 
			throws EugeneException {
		return this.getA().evaluate(device) ^ this.getB().evaluate(device);
	}

	@Override
	public Constraint toJaCoP(Store store, IntVar[] variables, Device device,
			List<Component> components) throws EugeneException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Constraint toJaCoPNot(Store store, IntVar[] variables,
			Device device, List<Component> components) throws EugeneException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Constraint toJaCoPAnd(Store store, IntVar[] variables,
			Device device, List<Component> components) throws EugeneException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Constraint toJaCoPOr(Store store, IntVar[] variables, Device device,
			List<Component> components) throws EugeneException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Constraint toJaCoPXor(Store store, IntVar[] variables,
			Device device, List<Component> components) throws EugeneException {
		// TODO Auto-generated method stub
		return null;
	}


}
