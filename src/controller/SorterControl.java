/**
 * 
 */
package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import javax.swing.JTextPane;

import model.LinksSorter;
import view.Log;

/**
 * Control of sorting links.
 * 
 * @author NHATHAN
 *
 */
public class SorterControl extends Control implements ActionListener {

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	/**
	 * Paths of selected files.
	 */
	private JTextPane inputFiles;

	public SorterControl(JTextPane inputFiles) {
		// TODO Auto-generated constructor stub
		this.inputFiles = (inputFiles);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		String[] listFilenames = inputFiles.getText().split("\\s*;\\s*");
		LinksSorter ls = new LinksSorter();
		for (int i = 0; i < listFilenames.length; i++) {
			String inputPath = listFilenames[i];
			// Getting the original name of file
			String filenameDetailed = inputPath.replaceAll("\\\\", "/").replaceAll("[\u0000-\u001f]", "");
			String filenameWithoutExt = filenameDetailed.substring(filenameDetailed.lastIndexOf("/") + 1)
					.replaceAll(".[\\w]+$", "");
			String outputPath = filenameWithoutExt + " Sorted.txt";
			try {
				exportLinksList(outputPath, ls.generate(importLinksList(inputPath)));
				Log.note(filenameWithoutExt + ": Sắp xếp xong!");
			} catch (IOException e2) {
				// TODO: handle exception
				Log.note(filenameWithoutExt + ": Lỗi - " + e2.getMessage());
			}
		}
	}

}
