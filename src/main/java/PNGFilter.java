import java.io.File;
import java.io.FilenameFilter;

public class PNGFilter implements FilenameFilter {

	@Override
	public boolean accept(File dir, String name) {
		return name.endsWith(".png");
	}

}
