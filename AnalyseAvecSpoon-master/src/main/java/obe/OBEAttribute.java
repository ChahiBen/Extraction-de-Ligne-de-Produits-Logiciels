package obe;

import java.util.ArrayList;

public class OBEAttribute extends OBE implements IArtifact{
	ArrayList<OBEAccess> Accesses;
	
	public OBEAttribute(String name) {
		super();
		this.name = name;
	}

	public ArrayList<OBEAccess> getAccesses() {
		return Accesses;
	}
	public void setAccesses(ArrayList<OBEAccess> accesses) {
		Accesses = accesses;
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
