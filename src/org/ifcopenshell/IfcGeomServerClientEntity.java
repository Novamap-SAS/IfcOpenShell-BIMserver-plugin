package org.ifcopenshell;

import org.bimserver.geometry.Matrix;
import org.bimserver.plugins.renderengine.RenderEngineException;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class IfcGeomServerClientEntity {
	private int id;
	private String guid;
	private String name;
	private String type;
	private int parentId;
	private double[] matrix;
	private int repId;
	private float[] positions;
	private float[] normals;
	private int[] indices;
	private float[] colors;
	private int[] materialIndices;
	private JsonObject extendedData;
	
	public IfcGeomServerClientEntity(int id, String guid, String name,
			String type, int parentId, double[] matrix, int repId,
			float[] positions, float[] normals, int[] indices, float[] colors,
			int[] materialIndices, String messageRemainder) {
		super();
		this.id = id;
		this.guid = guid;
		this.name = name;
		this.type = type;
		this.parentId = parentId;
		this.matrix = Matrix.changeOrientation(matrix);
		this.repId = repId;
		this.positions = positions;
		this.normals = normals;
		this.indices = indices;
		this.colors = colors;
		this.materialIndices = materialIndices;
		
		this.extendedData = null;
		if (messageRemainder != null && messageRemainder.length() > 0) {
			// un-pad string
			this.extendedData = new JsonParser().parse(messageRemainder.trim()).getAsJsonObject();
		}
	}
	
	public int getId() {
		return id;
	}

	public String getGuid() {
		return guid;
	}

	public String getName() {
		return name;
	}

	public String getType() {
		return type;
	}

	public int getParentId() {
		return parentId;
	}

	public double[] getMatrix() {
		return matrix;
	}

	public int getRepId() {
		return repId;
	}

	public float[] getPositions() {
		return positions;
	}

	public float[] getNormals() {
		return normals;
	}

	public int[] getIndices() {
		return indices;
	}

	public float[] getColors() {
		return colors;
	}

	public int[] getMaterialIndices() {
		return materialIndices;
	}
	
	public int getNumberOfPrimitives() {
		return indices.length / 3;
	}
	
	public int getNumberOfColors() {
		return colors.length / 4;
	}
	
	public float getExtendedDataAsFloat(String name) throws RenderEngineException {
		if (this.extendedData == null) {
			throw new RenderEngineException("No extended data for Entity " + this.guid);
		}
		JsonElement elem = extendedData.get(name);
		if (elem == null) {
			throw new RenderEngineException("No extended data entry found for " + name);
		}
		return elem.getAsFloat();
	}
}