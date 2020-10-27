package obe;

import java.util.ArrayList;

public class OBEMethod extends OBE implements IArtifact{

	ArrayList<OBELocalVariable> LocalVariables;
	ArrayList<OBEInvocation> Invocations;
	ArrayList<OBEAccess> Accesses;
	OBESignature signature;
	
	public OBEMethod(String name) {
		super();
		this.name = name;
		LocalVariables = new ArrayList<OBELocalVariable>();
		Invocations = new ArrayList<OBEInvocation>();
		Accesses = new ArrayList<OBEAccess>();
	}

	public ArrayList<OBELocalVariable> getLocalVariables() {
		return LocalVariables;
	}
	public void setLocalVariables(ArrayList<OBELocalVariable> localVariables) {
		LocalVariables = localVariables;
	}
	public ArrayList<OBEInvocation> getInvocations() {
		return Invocations;
	}
	public void setInvocations(ArrayList<OBEInvocation> invocations) {
		Invocations = invocations;
	}
	public ArrayList<OBEAccess> getAccesses() {
		return Accesses;
	}
	public void setAccesses(ArrayList<OBEAccess> accesses) {
		Accesses = accesses;
	}
	public OBESignature getSignature() {
		return signature;
	}
	public void setSignature(OBESignature signature) {
		this.signature = signature;
	}

	@Override
	public String getName() {
		return this.name;
	}

	@Override
	public int getIdentifiant() {
		return getName().hashCode();
	}
	
	
}
