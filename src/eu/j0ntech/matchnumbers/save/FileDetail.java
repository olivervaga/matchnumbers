package eu.j0ntech.matchnumbers.save;

public class FileDetail {

	private String path;
	
	private String name;

	public FileDetail(String fileName, String filePath) {
		name = fileName;
		path = filePath;
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
