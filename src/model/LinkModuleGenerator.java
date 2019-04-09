package model;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LinkModuleGenerator {
	
	private enum nodeTypes {
		MAINPAGE, CHAPTER, FULLTEXT
	};
	
	private String tab = "        ";
	private String nl = System.lineSeparator();
	private String cm = "--";
	
	public String generate(ArrayList<String> pagenames, String mainpage) {
		ArrayList<String> nodes = constructNodes(pagenames, mainpage);
		StringBuilder result = new StringBuilder();
		result.append("return {" + nl);
		for (int i = 0; i < nodes.size(); i++) {
			result.append(nl + nodes.get(i));
		}
		result.append(nl + "}");
		return result.toString();
	}
	
	private String arcsStructure() {
		// TODO Auto-generated method stub
		return tab + cm + "The stucture of arcs" + nl
				+ cm + tab + "arcsStructure = {" + nl
				+ cm + tab + tab + "{id = \"\",-- Identification given for arc" + nl
				+ cm + tab + tab + "fullname = \"\", -- Name of arc" + nl
				+ cm + tab + tab + "including = {} -- Id of arcs or volumes to be included in this arc" + nl
				+ cm + tab + tab + "}," + nl;
	}
	
	private ArrayList<String> constructNodes(ArrayList<String> pagenames, String mainpage){
		int mainPos = pagenames.indexOf(mainpage);
		ArrayList<String> result = new ArrayList<String>();
		result.add(arcsStructure());
		for (int i = 0; i < pagenames.size(); i++) {
			if (i < mainPos){
				if (i == 0){
					result.add(tab + cm + "Fulltexts" + nl);
				}
				result.add(node(pagenames.get(i), nodeTypes.FULLTEXT));
			} else if (i == mainPos){
				result.add(tab + cm + "Mainpage" + nl);
				result.add(node(pagenames.get(i), nodeTypes.MAINPAGE));
			} else {
				if (i == mainPos + 1){
					result.add(tab + cm + "Chapters" + nl);
				}
				result.add(node(pagenames.get(i), nodeTypes.CHAPTER));
			}
		}
		return result;
	}
	

	private String node(String name, nodeTypes type) {
		if (type == nodeTypes.MAINPAGE) {
			return tab + "{fullname = \"" + name + "\"," + nl
					+ tab + "mainpage = true}," + nl;
		} else {
			ChapterPosition chPos = new ChapterPosition(name);
			String fullname = name;
			String longname, shortname;
			switch (type) {
			case FULLTEXT:
				String id = "";
				if (chPos.get("volume") != null) {
					id = chPos.get("volume").toString();
					longname = "Tập " + id;
					shortname = longname;
				} else {
					longname = fullname;
					shortname = longname;
				}
				return tab + "{fullname = \"" + fullname + "\"," + nl
						+ tab + "shortname = \"" + shortname + "\"," + nl
						+ tab + "longname = \"" + longname + "\"," + nl
						+ tab + "id = " + id + "}," + nl;

			case CHAPTER:
				String childOf = null;
				if (chPos.get("volume") != null) {
					childOf = chPos.get("volume").toString();
					childOf = chPos.get("volume").toString();
					shortname = shortenedName(name);
					longname = "Tập " + childOf + " " + shortname;
				} else {
					childOf = "";
					longname = fullname;
					shortname = shortenedName(name);
				}
				return tab + "{fullname = \"" + fullname + "\"," + nl
						+ tab + "shortname = \"" + shortname + "\"," + nl
						+ tab + "longname = \"" + longname + "\"," + nl
						+ tab + "childOf = " + childOf + "}," + nl;
			default:
				break;
			}
		}
		return "";
	}
	
	private String shortenedName(String pagename) {
		// TODO Auto-generated method stub
		ChapterPosition chPos = new ChapterPosition(pagename);
		Matcher m = Pattern.compile(PatternNorms.CHAPTER_PATTERN).matcher(pagename);
		if (m.find()) {
			return "Chương " + chPos.get("chapter").intValue();
		} else {
			// check behind the "volume XX" if there is still something
			m = Pattern.compile(PatternNorms.VOLUME_PATTERN).matcher(pagename);
			if (m.find()) {
				return pagename.substring(m.end()).trim();
			}
		}
		return pagename;
	}
}
