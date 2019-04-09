package model;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

/**
 * Loading all patterns for researching and sorting.
 * 
 * @author NHATHAN
 *
 */
public class PatternNorms {
	public static String VOLUME_PATTERN;

	public static String CHAPTER_PATTERN;
	/**
	 * Including prologue, epilogue, intermission, illustration, etc.
	 */
	public static String[] OTHER_PATTERNS;
	public static Integer[] VALUE_OF_OTHER_PATTERNS;
	private static PatternNorms uniquePatternNorms;
	public static PatternNorms getUniquePatternNorms() {
		if (uniquePatternNorms == null) {
			uniquePatternNorms = new PatternNorms();
		}
		return uniquePatternNorms;
	}

	/**
	 * Failure or success in loading.
	 */
	private boolean SUCCESSFUL_LOAD = loadVolChapPattern("res/volumePatterns.txt")
			&& loadVolChapPattern("res/chapterPatterns.txt") && loadOtherPattern();

	public boolean isSUCCESSFUL_LOAD() {
		return SUCCESSFUL_LOAD;
	}

	private boolean loadOtherPattern() {
		// TODO Auto-generated method stub
		ArrayList<String> listOfAlternativePatterns = new ArrayList<String>();
		ArrayList<Integer> listOfValue = new ArrayList<Integer>();
		// Open otherPatterns to load
		String txtFile = "res/otherPatterns.txt";
		String lineRead = "";
		String separator = "\\s*;\\s*";
		StringBuilder pattern = null;
		try (BufferedReader br = new BufferedReader(
				new InputStreamReader(this.getClass().getClassLoader().getResourceAsStream(txtFile), "UTF-8"))) {
			while ((lineRead = br.readLine()) != null) {
				pattern = null;
				// use comma as separator
				// constructing pattern
				if (lineRead.indexOf(";") >= 0) {
					String[] mixedTable = lineRead.split(separator);
					// the first value stands for representative value of the
					// line
					try {
						listOfValue.add(new Integer(Integer.parseInt(mixedTable[0])));
					} catch (NumberFormatException e) {
						// TODO: handle exception
						listOfValue.add(null);
					}
					// the followings are possible cases
					pattern = new StringBuilder("");
					for (int i = 1; i < mixedTable.length; i++) {
						pattern.append(mixedTable[i]);
						if (i < mixedTable.length - 1) {
							pattern.append("|");
						}
					}
					listOfAlternativePatterns.add(pattern.toString());
				}
			}
			br.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			return false;
		}
		OTHER_PATTERNS = listOfAlternativePatterns.toArray(new String[listOfAlternativePatterns.size()]);
		VALUE_OF_OTHER_PATTERNS = listOfValue.toArray(new Integer[listOfValue.size()]);
		return true;
	}

	private boolean loadVolChapPattern(String filePath) {
		// Loading the possibilities marking chapter and constructing the
		// pattern of regex
		String lineRead = "";
		String separator = "\\s*;\\s*";
		StringBuilder pattern = null;
		StringBuilder x = new StringBuilder(filePath);// test if volume or
														// chapter
		try (BufferedReader br = new BufferedReader(
				new InputStreamReader(this.getClass().getClassLoader().getResourceAsStream(filePath), "UTF-8"))) {
			while ((lineRead = br.readLine()) != null) {
				// use comma as separator
				// constructing pattern
				if (lineRead.indexOf(";") >= 0) {
					pattern = new StringBuilder("(?<=");
					String[] chapterPatterns = lineRead.split(separator);
					for (int i = 0; i < chapterPatterns.length; i++) {
						pattern.append(chapterPatterns[i] + "\\s" + "|" + chapterPatterns[i]);
						if (i < chapterPatterns.length - 1) {
							pattern.append("|");
						}
					}
					pattern.append(")\\d+");
					if (x.indexOf("volume") >= 0) {
						VOLUME_PATTERN = pattern.toString();
					} else if (x.indexOf("chapter") >= 0) {
						CHAPTER_PATTERN = pattern.toString();
					}
				}
			}
			br.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}

		return true;
	}
}
