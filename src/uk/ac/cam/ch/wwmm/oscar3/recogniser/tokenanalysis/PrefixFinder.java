package uk.ac.cam.ch.wwmm.oscar3.recogniser.tokenanalysis;

import java.util.regex.Pattern;

import uk.ac.cam.ch.wwmm.oscar3.models.ExtractTrainingData;
import uk.ac.cam.ch.wwmm.ptclib.string.StringTools;

public class PrefixFinder {
	private static String primesRe = "[" + StringTools.primes + "]*";
	private static String locantRe = "(\\d+" + primesRe + "[RSEZDLH]?|" +
	"\\(([RSEZDLH\u00b1]|\\+|" + StringTools.hyphensRe + ")\\)|" +
		"[DLRSEZ]|" +
			"([CNOS]|Se)\\d*|" +
			"\\d*[" + StringTools.lowerGreek + "]|" +
	"cis|trans|o(rtho)?|m(eta)?|p(ara)?|asym|sym|sec|tert|catena|closo|enantio|ent|endo|exo|" +
	"fac|mer|gluco|nido|aci|erythro|threo|arachno|meso|syn|anti|tele|cine" +
	")" + primesRe;
	private static String prefixRe = "(" + locantRe + "(," + locantRe + ")*)";
	
	public static Pattern prefixPattern = Pattern.compile(prefixRe + 
					"[" + StringTools.hyphens + "](\\S*)");
	
	public static Pattern prefixBody = Pattern.compile(prefixRe);
	
	public static String getPrefix(String s) {
		if(prefixPattern.matcher(s).matches()) {
			int idx = s.indexOf("-");
			// Check if it's a not-splitting word
			if(ExtractTrainingData.getInstance().notForPrefix.contains(s.substring(idx+1))) {
				return null;
			}
			if(idx == 0) return null;
			return s.substring(0, idx+1);
		}
		return null;		
	}

}
