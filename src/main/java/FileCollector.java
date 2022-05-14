import java.io.File;
import java.util.ArrayList;

public class FileCollector {

	private final ArrayList<File> files = new ArrayList<File>(0);

	public FileCollector() {
	}

	public void run(File directory) {
		for (final File fileEntry : directory.listFiles()) {
			if (fileEntry.isDirectory()) {
				run(fileEntry);
			} else {
				if (fileEntry.toString().endsWith(".png")) {
					files.add(fileEntry);
				}
			}
		}
	}

	public ArrayList<File> getFiles() {
		return files;
	}
}
