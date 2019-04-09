package view;

import javax.swing.JTextPane;
import javax.swing.text.BadLocationException;
import javax.swing.text.StyledDocument;

/**
 * Logging what happened, including errors.
 * 
 * @author NHATHAN
 *
 */
public class Log extends JTextPane {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7743650268564194301L;

	private static Log uniqueLog;

	/**
	 * @return the uniqueLog
	 */
	public static Log getUniqueLog() {
		if (uniqueLog == null) {
			uniqueLog = new Log();
		}
		return uniqueLog;
	}

	/**
	 * @param uniqueLog
	 *            the uniqueLog to set
	 */
	public static void setUniqueLog(Log uniqueLog) {
		Log.uniqueLog = uniqueLog;
	}

	/**
	 * Logging.
	 * 
	 * @param str
	 *            what to log.
	 */
	public static void note(String str) {
		StyledDocument doc = getUniqueLog().getStyledDocument();
		try {
			doc.insertString(doc.getLength(), str + "\n\n", null);
		} catch (BadLocationException e) {
			// TODO Auto-generated catch block
		}
	}
}
