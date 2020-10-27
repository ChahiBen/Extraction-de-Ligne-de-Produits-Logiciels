package obe;

import java.util.ArrayList;

public class OBEPackage extends OBE{
	
	private ArrayList<OBEInterface> listInterface = new ArrayList<OBEInterface>();
	private ArrayList<OBEClass> listClass = new ArrayList<OBEClass>();
	
	public OBEPackage(String name) {
		super();
		this.name = name;
	}
	
	public ArrayList<OBEInterface> getListInterface() {
		return listInterface;
	}
	public void setListInterface(ArrayList<OBEInterface> listInterface) {
		this.listInterface = listInterface;
	}
	public ArrayList<OBEClass> getListClass() {
		return listClass;
	}
	public void setListClass(ArrayList<OBEClass> listClass) {
		this.listClass = listClass;
	}

	
}
