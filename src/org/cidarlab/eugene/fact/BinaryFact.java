package org.cidarlab.eugene.fact;

public abstract class BinaryFact 
	implements Fact {

	private String A_name;
	private long A_id;
	
	private String B_name;
	private long B_id;
	
	public BinaryFact(String A, String B) {
		this.A_name = A;
		this.B_name = B;
		
		this.A_id = -1;
		this.B_id = -1;
	}
	
	public BinaryFact(long A, long B) {
		this.A_id = A;
		this.B_id = B;
		
		this.A_name = null;
		this.B_name = null;				
	}
	
	public String getAName() {
		return this.A_name;
	}
	
	public long getAId() {
		return this.A_id;
	}
	
	public String getBName() {
		return this.B_name;
	}
	
	public long getBId() {
		return this.B_id;
	}
	
	public abstract String getRelationType();
}
