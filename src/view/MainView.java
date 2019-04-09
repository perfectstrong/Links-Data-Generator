package view;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.SwingConstants;
import javax.swing.border.BevelBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.text.StyledDocument;

import controller.FileListAccessory;
import controller.LMGControl;
import controller.SorterControl;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

/**
 * Main view of tool.
 * 
 * @author NHATHAN
 *
 */
public class MainView extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1434216695759527061L;
	private JPanel contentPane;
	private JTable tableInputFiles;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainView frame = new MainView();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public MainView() {
		// overview
		setTitle("Navigator Generator");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 844, 613);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		GridBagLayout gbl_contentPane = new GridBagLayout();
		gbl_contentPane.columnWidths = new int[] { 550, 250 };
		gbl_contentPane.rowWeights = new double[] { 0.3, 1.0 };
		gbl_contentPane.columnWeights = new double[] { 1.0, 0.0 };
		contentPane.setLayout(gbl_contentPane);

		//
		//
		//
		//
		// panel for sorting
		final JPanel sortingPanel = new JPanel();
		sortingPanel.setBorder(new BevelBorder(BevelBorder.RAISED, Color.DARK_GRAY, Color.LIGHT_GRAY, null, null));
		GridBagConstraints gbc_sortingPanel = new GridBagConstraints();
		gbc_sortingPanel.insets = new Insets(0, 0, 5, 5);
		gbc_sortingPanel.fill = GridBagConstraints.BOTH;
		gbc_sortingPanel.weightx = 1.0;
		gbc_sortingPanel.weighty = 1.0;
		gbc_sortingPanel.gridx = 0;
		gbc_sortingPanel.gridy = 0;
		contentPane.add(sortingPanel, gbc_sortingPanel);
		GridBagLayout gbl_sortingPanel = new GridBagLayout();
		gbl_sortingPanel.columnWeights = new double[] { 1.0, 0.0 };
		gbl_sortingPanel.rowWeights = new double[] { 0.2, 0.8, 0.8 };
		sortingPanel.setLayout(gbl_sortingPanel);

		// the label of panel
		JLabel lblSortingLinks = new JLabel("Sắp xếp link (đường dẫn mỗi file cách nhau bằng một dấu \";\")");
		lblSortingLinks.setLabelFor(sortingPanel);
		GridBagConstraints gbc_lblSortingLinks = new GridBagConstraints();
		gbc_lblSortingLinks.gridwidth = 2;
		gbc_lblSortingLinks.anchor = GridBagConstraints.WEST;
		gbc_lblSortingLinks.insets = new Insets(0, 0, 5, 0);
		gbc_lblSortingLinks.gridx = 0;
		gbc_lblSortingLinks.gridy = 0;
		sortingPanel.add(lblSortingLinks, gbc_lblSortingLinks);

		// where the selected files show
		JScrollPane scrollPaneOfSorting = new JScrollPane();
		GridBagConstraints gbc_scrollPaneOfSorting = new GridBagConstraints();
		gbc_scrollPaneOfSorting.fill = GridBagConstraints.BOTH;
		gbc_scrollPaneOfSorting.gridheight = 2;
		gbc_scrollPaneOfSorting.insets = new Insets(5, 5, 5, 5);
		gbc_scrollPaneOfSorting.gridx = 0;
		gbc_scrollPaneOfSorting.gridy = 1;
		sortingPanel.add(scrollPaneOfSorting, gbc_scrollPaneOfSorting);

		JTextPane paneFilenamesToSort = new JTextPane();
		scrollPaneOfSorting.setViewportView(paneFilenamesToSort);
		final StyledDocument doc = paneFilenamesToSort.getStyledDocument();

		// button to choose files
		final JButton btnSelectFileForSorting = new JButton("Chọn file");
		GridBagConstraints gbc_btnSelectFileForSorting = new GridBagConstraints();
		gbc_btnSelectFileForSorting.insets = new Insets(5, 5, 5, 0);
		gbc_btnSelectFileForSorting.gridx = 1;
		gbc_btnSelectFileForSorting.gridy = 1;
		sortingPanel.add(btnSelectFileForSorting, gbc_btnSelectFileForSorting);

		// button to apply sorting algorithm for each selected file
		JButton btnSort = new JButton("Sắp xếp");
		GridBagConstraints gbc_btnSort = new GridBagConstraints();
		gbc_btnSort.insets = new Insets(5, 5, 5, 5);
		gbc_btnSort.gridx = 1;
		gbc_btnSort.gridy = 2;
		sortingPanel.add(btnSort, gbc_btnSort);

		// in order to choose multiple files
		final JFileChooser fcMultipleChoice = new JFileChooser();
		final FileListAccessory accessory = new FileListAccessory(fcMultipleChoice);
		fcMultipleChoice.setAccessory(accessory);
		btnSelectFileForSorting.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (arg0.getSource().equals(btnSelectFileForSorting)) {
					int returnVal = fcMultipleChoice.showOpenDialog(sortingPanel);
					if (returnVal == JFileChooser.APPROVE_OPTION) {
						DefaultListModel<File> model = accessory.getModel();
						for (int i = 0; i < model.size(); i++) {
							// add file path to filenamesToSortPane
							String filePath = model.get(i).getPath();
							try {
								if (i + 1 < model.size()) {
									doc.insertString(doc.getLength(), filePath + ";", null);
								} else {
									doc.insertString(doc.getLength(), filePath, null);
								}
							} catch (Exception e) {
								// TODO: handle exception
								Log.note("Chọn file bị lỗi - " + e.getMessage());
							}
						}
						accessory.getModel().clear();
					}
				}
			}
		});
		btnSort.addActionListener(new SorterControl(paneFilenamesToSort));

		//
		//
		//
		//
		//
		//
		// panel for generating module link
		final JPanel generatingPanel = new JPanel();
		generatingPanel.setBorder(new BevelBorder(BevelBorder.RAISED, Color.DARK_GRAY, Color.LIGHT_GRAY, null, null));
		GridBagConstraints gbc_generatingPanel = new GridBagConstraints();
		gbc_generatingPanel.insets = new Insets(0, 0, 5, 5);
		gbc_generatingPanel.fill = GridBagConstraints.BOTH;
		gbc_generatingPanel.weightx = 1.0;
		gbc_generatingPanel.weighty = 1.0;
		gbc_generatingPanel.gridx = 0;
		gbc_generatingPanel.gridy = 1;
		contentPane.add(generatingPanel, gbc_generatingPanel);
		GridBagLayout gbl_generatingPanel = new GridBagLayout();
		gbl_generatingPanel.columnWidths = new int[] { 368, 50 };
		gbl_generatingPanel.columnWeights = new double[] { 1.0, 0.0 };
		gbl_generatingPanel.rowWeights = new double[] { 0.1, 0.2, 0.2, 0.2 };
		generatingPanel.setLayout(gbl_generatingPanel);

		// label
		JLabel lblLMG = new JLabel("Tạo Module Link");
		lblLMG.setLabelFor(generatingPanel);
		GridBagConstraints gbc_lblLMG = new GridBagConstraints();
		gbc_lblLMG.gridwidth = 2;
		gbc_lblLMG.insets = new Insets(0, 0, 5, 0);
		gbc_lblLMG.anchor = GridBagConstraints.WEST;
		gbc_lblLMG.gridx = 0;
		gbc_lblLMG.gridy = 0;
		generatingPanel.add(lblLMG, gbc_lblLMG);

		// button to select file of links (should be sorted)
		final JButton btnSelectFileForLMG = new JButton("Chọn file");
		GridBagConstraints gbc_btnSelectFileForGenerating = new GridBagConstraints();
		gbc_btnSelectFileForGenerating.insets = new Insets(0, 0, 5, 0);
		gbc_btnSelectFileForGenerating.gridx = 1;
		gbc_btnSelectFileForGenerating.gridy = 1;
		generatingPanel.add(btnSelectFileForLMG, gbc_btnSelectFileForGenerating);

		JScrollPane scrollPaneOfLMG = new JScrollPane();
		GridBagConstraints gbc_scrollPaneOfLMG = new GridBagConstraints();
		gbc_scrollPaneOfLMG.gridheight = 3;
		gbc_scrollPaneOfLMG.insets = new Insets(0, 0, 0, 5);
		gbc_scrollPaneOfLMG.fill = GridBagConstraints.BOTH;
		gbc_scrollPaneOfLMG.gridx = 0;
		gbc_scrollPaneOfLMG.gridy = 1;
		generatingPanel.add(scrollPaneOfLMG, gbc_scrollPaneOfLMG);

		tableInputFiles = new JTable();
		scrollPaneOfLMG.setViewportView(tableInputFiles);
		final DefaultTableModel modelTable = new DefaultTableModel(new Object[][] {},
				new String[] { "File", "Mainpage" });
		tableInputFiles.setModel(modelTable);
		DefaultTableCellRenderer dtcr = new DefaultTableCellRenderer();
		dtcr.setHorizontalAlignment(SwingConstants.RIGHT);
		tableInputFiles.getColumn("File").setCellRenderer(dtcr);
		// input multiple files
		final JFileChooser fcLinksList = new JFileChooser();
		final FileListAccessory accessoryLL = new FileListAccessory(fcLinksList);
		fcLinksList.setAccessory(accessoryLL);
		btnSelectFileForLMG.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (arg0.getSource().equals(btnSelectFileForLMG)) {
					int returnVal = fcLinksList.showOpenDialog(generatingPanel);
					if (returnVal == JFileChooser.APPROVE_OPTION) {
						DefaultListModel<File> model = accessoryLL.getModel();
						for (int i = 0; i < model.size(); i++) {
							// add file path to "File" column in
							String filePath = model.get(i).getPath();
							try {
								modelTable.addRow(new Object[] { filePath, "" });
							} catch (Exception e) {
								// TODO: handle exception
								Log.note("Lỗi - " + e.getMessage());
							}
						}
						accessoryLL.getModel().clear();
					}
				}
			}
		});

		// Adding new row into table
		JButton btnAddingRow = new JButton("Thêm dòng");
		btnAddingRow.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				modelTable.addRow(new Object[]{"", ""});
			}
		});
		GridBagConstraints gbc_btnAddingRow = new GridBagConstraints();
		gbc_btnAddingRow.insets = new Insets(0, 0, 5, 0);
		gbc_btnAddingRow.gridx = 1;
		gbc_btnAddingRow.gridy = 2;
		generatingPanel.add(btnAddingRow, gbc_btnAddingRow);

		JButton btnMakeLM = new JButton("Tạo Module Link");
		GridBagConstraints gbc_btnMakeLM = new GridBagConstraints();
		gbc_btnMakeLM.gridx = 1;
		gbc_btnMakeLM.gridy = 3;
		generatingPanel.add(btnMakeLM, gbc_btnMakeLM);
		btnMakeLM.addActionListener(new LMGControl(tableInputFiles));

		//
		//
		//
		//
		// log of history
		JTextPane logPane = Log.getUniqueLog();
		logPane.setEditable(false);
		GridBagConstraints gbc_logPane = new GridBagConstraints();
		gbc_logPane.gridheight = 3;
		gbc_logPane.insets = new Insets(5, 5, 5, 5);
		gbc_logPane.fill = GridBagConstraints.BOTH;
		gbc_logPane.gridx = 1;
		gbc_logPane.gridy = 0;
		contentPane.add(logPane, gbc_logPane);
	}

}
