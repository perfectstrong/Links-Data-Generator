/**
 * 
 */
package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.JTable;

import model.LinkModuleGenerator;
import view.Log;

/**
 * @author NHATHAN
 *
 */
public class LMGControl extends Control implements ActionListener {
	
	private JTable inputTable;

	/* (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	
	public LMGControl(JTable table) {
		// TODO Auto-generated constructor stub
		inputTable = table;
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		LinkModuleGenerator lmg = new LinkModuleGenerator();
		for (int i = 0; i < inputTable.getRowCount(); i++) {
			String inputPath = (String) inputTable.getValueAt(i, 0);
			String mainpage = (String) inputTable.getValueAt(i, 1);
			System.out.println(inputPath + " " + mainpage);
			if (inputPath != null && mainpage != null){
				String filenameDetailed = inputPath.replaceAll("\\\\", "/").replaceAll("[\u0000-\u001f]", "");
				String filenameWithoutExt = filenameDetailed.substring(filenameDetailed.lastIndexOf("/") + 1)
						.replaceAll(".[\\w]+$", "");
				String outputPath = filenameWithoutExt + " Module Link.txt";
				try {
					exportLinksList(outputPath, lmg.generate(importLinksList(inputPath), mainpage));
					Log.note(filenameWithoutExt + ": Tạo Module Link xong!");
				} catch (IOException e2) {
					// TODO: handle exception
					Log.note(filenameWithoutExt + ": Lỗi - " + e2.getMessage());
				}
			}
		}
	}

}
