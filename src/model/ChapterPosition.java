package model;

import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Determine the relative identification of a chapter based on its volume and its chapter number.
 * <br/>
 * <ul>Some special values for chapter number: (defined in <b>otherPatterns.txt</b>)
 * <li>null: full-text.</li>
 * <li>-1: illustrations.</li>
 * <li>0: prologue.</li>
 * <li>9990: after-word.</li>
 * <li>9989: epilogue.</li>
 * <li>9991: appendix.</li>
 * <li>9990: intermission.(need to arrange manually)</li>
 * <li>etc.</li>
 * </ul>
 */

/**
 * @author NHATHAN
 *
 */
public class ChapterPosition extends HashMap<String, Integer> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5051284589602425381L;

	/**
	 * Create a new empty ChapterPosition object.
	 */
	public ChapterPosition() {
		put("volume", null);
		put("chapter", null);
	}

	/**
	 * Create a new ChapterPosition object basing on its given volume, chapter
	 * numbers.
	 * 
	 * @param volume
	 *            Can be arc number instead. Always positive.
	 * @param chapter
	 *            Always positive.
	 */
	public ChapterPosition(int volume, int chapter) {
		put("volume", new Integer(Math.max(volume, 0)));
		put("chapter", new Integer(Math.max(chapter, -1)));
	}

	/**
	 * Create a new ChapterPosition object basing on its title.
	 * 
	 * @param title
	 */
	public ChapterPosition(String title) {
		String refinedTitle = deleteContinuousSpaces(title);
		if (!PatternNorms.getUniquePatternNorms().isSUCCESSFUL_LOAD()) {
			System.out.println("Error on loading patterns!");
		} else {
			put("volume", calculeVolumeNumber(refinedTitle));
			put("chapter", calculeChapterNumber(refinedTitle));
		}
	}

	private Integer calculeChapterNumber(String title) {
		// TODO Auto-generated method stub
		Matcher m = Pattern.compile(PatternNorms.CHAPTER_PATTERN, Pattern.CASE_INSENSITIVE|Pattern.UNICODE_CASE).matcher(title);
		if (m.find()) {
			return new Integer(Integer.parseInt(m.group(0)));
		} else {// Maybe illustrations, after-word, prologue, epilogue,etc.
			String[] alternativePatterns = PatternNorms.OTHER_PATTERNS;
			Integer[] value = PatternNorms.VALUE_OF_OTHER_PATTERNS;
			for (int i = 0; i < alternativePatterns.length; i++) {
				if (Pattern.compile(alternativePatterns[i], Pattern.CASE_INSENSITIVE|Pattern.UNICODE_CASE).matcher(title).find()) {
					return value[i];
				}
			}
		}
		return null;
	}

	private Integer calculeVolumeNumber(String title) {
		// TODO Auto-generated method stub
		Matcher m = Pattern.compile(PatternNorms.VOLUME_PATTERN, Pattern.CASE_INSENSITIVE|Pattern.UNICODE_CASE).matcher(title);
		if (m.find()) {
			return new Integer(Integer.parseInt(m.group(0)));
		}
		return null;
	}

	private String deleteContinuousSpaces(String title) {
		// TODO Auto-generated method stub
		Pattern p = Pattern.compile("\\s{2,}");
		Matcher m = p.matcher(title);
		return m.replaceAll(" ");
	}

	/**
	 * Compare positions of 2 chapters (in the ascending list of chapters).
	 * 
	 * @param otherChPosition
	 * @return 0 if equal, -1 if <b>this</b> stands before otherChPosition, 1
	 *         otherwise.
	 */
	public int comparePositionTo(ChapterPosition otherChPosition) {
		int valOfThisPos = this.convertToInt();
		int valOfThatPost = otherChPosition.convertToInt();
		if (valOfThisPos < valOfThatPost) {
			return -1;
		} else if (valOfThisPos > valOfThatPost) {
			return 1;
		}
		return 0;
	}

	/**
	 * In order to compare with others.
	 * 
	 * @return
	 */
	public int convertToInt() {
		// TODO Auto-generated method stub
		Integer volume = get("volume");
		Integer chapter = get("chapter");
		int value = 0;
		if (volume == null) {
			if (chapter == null) {
				value = 1000;
			} else {
				value = chapter.intValue();
			}
		} else if (volume != null) {
			if (chapter == null) {
				value = volume.intValue();
			} else {
				value = volume.intValue() * 10000 + chapter.intValue();
			}
		}
		return value;
	}
}
