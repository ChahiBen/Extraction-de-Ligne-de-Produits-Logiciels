package obe;

public class OBEAccess {
	OBEAttribute attribute;
	String name;
		
	public OBEAccess(OBEAttribute attribute) {
		super();
		this.attribute = attribute;
	}

	public OBEAccess(String simpleName, OBEAttribute newAttribute) {
		// TODO Auto-generated constructor stub
		super();
		this.name = simpleName;
		this.attribute = newAttribute;
	}

	public OBEAttribute getAttribute() {
		return attribute;
	}

	public void setAttribute(OBEAttribute attribute) {
		this.attribute = attribute;
	}

	
	
}
