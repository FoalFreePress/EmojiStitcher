import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

public class Main {

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
				new EmoteStitcher(chooser.getSelectedFile()).run();
			} catch (Exception e) {
				printError("An error occured while running.");
				e.printStackTrace();
			}
		} else {

		}
	}

	public static void printError(String error) {
		JOptionPane.showMessageDialog(null, error, "ERROR", JOptionPane.ERROR_MESSAGE);
	}

}
