package eu.j0ntech.matchnumbers.save;

public class SaveDetail {

	private String path;

	private String name;

	public SaveDetail(String saveName, String savePath) {
		name = saveName;
		path = savePath;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
