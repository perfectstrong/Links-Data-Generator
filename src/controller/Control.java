package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;

public class Control implements ActionListener {

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub

	}
	public ArrayList<String> importLinksList(String path) throws IOException {
		ArrayList<String> linksList = null;
		try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(path), "UTF-8"))) {
			String titleRead = "";
			linksList = new ArrayList<String>();
			while ((titleRead = br.readLine()) != null) {
				if (titleRead.length() >= 2) {// do not count the BOM character
					linksList.add(titleRead);
				}
			}
		}
		return linksList;
	}

	public void exportLinksList(String outputPath, String result) throws IOException {
		try (BufferedWriter writer = new BufferedWriter(
				new OutputStreamWriter(new FileOutputStream(outputPath), "UTF-8"))) {
			writer.write(result);
		}
	}
}
