package obe;

import java.util.ArrayList;

public class OBEInvocation {
	String name;
	ArrayList<OBEMethod> HasCandidates;
	public OBEInvocation(String name) {
		super();
		this.name = name;
	}
	
	public ArrayList<OBEMethod> getHasCandidates() {
		return HasCandidates;
	}
	public void setHasCandidates(ArrayList<OBEMethod> hasCandidates) {
		HasCandidates = hasCandidates;
	}
	
	
}
