package obe;

import java.util.ArrayList;

public class OBEClass extends OBE implements IArtifact{
	String name;
	ArrayList<OBEMethod> method = new ArrayList<OBEMethod>();
	ArrayList<OBEAttribute> aBelongToClass = new ArrayList<OBEAttribute>();
	ArrayList<OBEMethod> mBelongToClass = new ArrayList<OBEMethod>();
	public OBEClass(String name) {
		super();
		this.name = name;
	}
	public ArrayList<OBEMethod> getMethod() {
		return method;
	}
	public void setMethod(ArrayList<OBEMethod> method) {
		this.method = method;
	}
	public String getName() {
		return this.name;
	}
	public ArrayList<OBEAttribute> getaBelongToClass() {
		return aBelongToClass;
	}
	public void setaBelongToClass(ArrayList<OBEAttribute> aBelongToClass) {
		this.aBelongToClass = aBelongToClass;
	}
	public ArrayList<OBEMethod> getmBelongToClass() {
		return mBelongToClass;
	}
	public void setmBelongToClass(ArrayList<OBEMethod> mBelongToClass) {
		this.mBelongToClass = mBelongToClass;
	}
	@Override
	public int getIdentifiant() {
		return getName().hashCode();
	}
	
}
