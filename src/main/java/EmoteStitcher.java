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

	public EmoteStitcher(File directory, ArrayList<BufferedImage> images) {
		this.directory = directory;
		this.images = images;
		this.numRows = (int) Math.sqrt(Math.pow(Math.ceil(Math.sqrt(images.size())), 2));
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
			if(!ImageIO.write(output, "png", new File(directory, "__MERGED.png")))
				System.err.println("Couldn't write output file!");
		} catch (IOException e) {
			throw new UncheckedIOException(e);
		}
	}

}
