package obe;

import java.util.ArrayList;

public class OBEInheritance {
	String name;
	
	ArrayList<OBEClass> hasSuperClass = new ArrayList<OBEClass> ();
	OBEClass hasSubClass;
	public OBEInheritance(ArrayList<OBEClass> hasSuperClass, OBEClass hasSubClass) {
		super();
		this.hasSuperClass = hasSuperClass;
		this.hasSubClass = hasSubClass;
	}
	public ArrayList<OBEClass> getHasSuperClass() {
		return hasSuperClass;
	}
	public void setHasSuperClass(ArrayList<OBEClass> hasSuperClass) {
		this.hasSuperClass = hasSuperClass;
	}
	public OBEClass getHasSubClass() {
		return hasSubClass;
	}
	public void setHasSubClass(OBEClass hasSubClass) {
		this.hasSubClass = hasSubClass;
	}
	
	
}
