package model;

import java.util.ArrayList;

public class EntityFields extends ArrayList<String> {
	private static final long serialVersionUID = -6194750710600891061L;
	
	private ArrayList<String> types;
	public ArrayList<String> getTypes() {
		return types;
	}
	
	public EntityFields() {
		super();
		this.types = new ArrayList<String>();
	}
	public EntityFields(EntityFields parent, String[] newNames, String[] newTypes) {
		this();
		
		if (parent != null) {
			addAll(parent);
			types.addAll(parent.getTypes());
		}
		
		int i;
		for (i = 0; i < newNames.length; i++)
			add(newNames[i]);
		for (i = 0; i < newTypes.length; i++)
			types.add(newTypes[i]);
	}
	public EntityFields(String[] newNames, String[] newTypes) {
		this(null, newNames, newTypes);
	}
	
	public String getType(int index) {
		return types.get(index);
	}
	public String getType(String name) {
		int index = indexOf(name);
		if (index == -1)
			return null;
		
		return getType(index);
	}
}