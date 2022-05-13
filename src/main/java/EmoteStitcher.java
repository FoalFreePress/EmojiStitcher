import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

public class EmoteStitcher implements Runnable {

	private static final int IMAGE_SIZE = 500;
	private File directory;
	private ArrayList<BufferedImage> images;
	private int numRows;

	public EmoteStitcher(File directory) {

		this.images = new ArrayList<BufferedImage>(0);
		this.directory = directory;
		this.addImages(directory.listFiles());
		this.numRows = (int) Math.sqrt(Math.pow(Math.ceil(Math.sqrt(images.size())), 2));
	}

	private void addImages(File[] files) {
		try {
			for (File file : files)
				images.add(ImageIO.read(file));
		} catch (Exception e) {
			Main.printError(e.getClass().getCanonicalName() + ": " + e.getMessage());
			throw new IllegalStateException("Unexpected IOException", e);
		}
	}

	@Override
	public void run() {
		try {
			int totalImages = 0;
			BufferedImage output = new BufferedImage(numRows * IMAGE_SIZE, numRows * IMAGE_SIZE,
					images.get(0).getType());
			Graphics graphics = output.getGraphics();
			for (int yCol = 0; yCol < numRows; yCol++) {
				for (int xCol = 0; xCol < numRows; xCol++) {
					if (totalImages >= images.size())
						break;
					BufferedImage image = images.get(totalImages++);
					graphics.drawImage(image, xCol * IMAGE_SIZE, yCol * IMAGE_SIZE, null);
				}
			}
			ImageIO.write(output, "png", new File(directory, "__MERGED.png"));
		} catch (IOException e) {
			throw new UncheckedIOException(e);
		}
	}

}
