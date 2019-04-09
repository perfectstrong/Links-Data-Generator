package model;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Sort the links provided by user.
 */

/**
 * @author NHATHAN
 *
 */
public class LinksSorter {
	
	public String generate(ArrayList<String> pagenames) {
		ArrayList<String> pagenamesSorted = sortPagenames(pagenames);
		StringBuilder result = new StringBuilder();
		String nl = System.lineSeparator();
		for (int i = 0; i < pagenamesSorted.size(); i++) {
			result.append(nl + pagenamesSorted.get(i));
		}
		return result.toString();
	}

	private ArrayList<String> sortPagenames(ArrayList<String> pagenames) {
		ArrayList<String> pagenamesSorted = new ArrayList<String>();
		ArrayList<ChapterPosition> chPosList = new ArrayList<ChapterPosition>();
		for (int i = 0; i < pagenames.size(); i++) {
			ChapterPosition newChPos = new ChapterPosition(pagenames.get(i));
			int insertPos = 0;
			// search for the highest position which doesn't exceed
			while ((insertPos < pagenamesSorted.size())
					&& (newChPos.comparePositionTo(chPosList.get(insertPos)) < 1)) {
				insertPos++;
			}
			pagenamesSorted.add(insertPos, pagenames.get(i));
			chPosList.add(insertPos, newChPos);
		}
		Collections.reverse(pagenamesSorted);
		return pagenamesSorted;
	}
}
