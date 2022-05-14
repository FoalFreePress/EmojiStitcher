import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import javax.imageio.ImageIO;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

public class Main {
	
	ArrayList<File> files = new ArrayList<File>(0);


	public static void main(String[] args) {

		JFileChooser chooser = new JFileChooser();
		chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

		chooser.setAcceptAllFileFilterUsed(false);

		if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
			try {
				File selectedFile = chooser.getSelectedFile();
				if (!selectedFile.isDirectory()) {
					Main.printError("Selected file " + selectedFile + " is not a directory.");
					return;
				}
				File oldImage = new File(selectedFile, "__MERGED.png");
				oldImage.delete();
				FileCollector fc = new FileCollector();
				fc.run(selectedFile);
				new EmoteStitcher(selectedFile, getImages(fc.getFiles())).run();
			} catch (Exception e) {
				printError("An error occured while running.");
				e.printStackTrace();
				return;
			}
		} else {
			JOptionPane.showMessageDialog(null, "You must select a directory.", "ERROR", JOptionPane.ERROR_MESSAGE);
			return;
		}

		JOptionPane.showMessageDialog(null, "Success! Saved as __MERGED.png in that directory!", "SUCCESS",
				JOptionPane.INFORMATION_MESSAGE);
	}

	public static void printError(String error) {
		JOptionPane.showMessageDialog(null, error, "ERROR", JOptionPane.ERROR_MESSAGE);
	}
	
	

	private static ArrayList<BufferedImage> getImages(ArrayList<File> files) {
		ArrayList<BufferedImage> images = new ArrayList<BufferedImage>(files.size());
		for(File file : files)
			try {
				images.add(ImageIO.read(file));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		return images;
	}

}
