package engine;

import java.io.Serializable;

public class GUID implements Serializable {
	private static final long serialVersionUID = 6478399689175314073L;
	
	private static String IP = null;
	private static long entityCount = 0;
	
	private String id;
	
	private GUID(String IP, long entityCount) {
		id = IP + "/" + entityCount;
	}
	
	private GUID(GUID guid) {
		id = guid.getID();
	}
	
	public static void initialize(String ip) {
		if(IP == null)
			IP = ip;
	}
	
	public static GUID create() {
		return new GUID(IP, entityCount++);
	}
	
	public static GUID copy(GUID guid) {
		return new GUID(guid);
	}
	
	public String getID() {
		return id;
	}
	
	public boolean equals(GUID guid) {
		return id.equals(guid.getID());
	}
}
