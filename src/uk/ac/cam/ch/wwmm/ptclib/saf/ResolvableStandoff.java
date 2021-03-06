package uk.ac.cam.ch.wwmm.ptclib.saf;

/**A data structure that represents a span within a document. This may potentially be resolved
 * against other such spans, so as to remove low-priority non-overlapping spans.
 * 
 * @author ptc24
 *
 */
public abstract class ResolvableStandoff implements Comparable {

	protected String type;
	
	public abstract int compareTypeTo(ResolvableStandoff other);
	public abstract int compareTypeToIfSameString(ResolvableStandoff other);
	public abstract int compareStart(ResolvableStandoff other);
	public abstract int compareEnd(ResolvableStandoff other);
	public abstract int compareStartToEnd(ResolvableStandoff other);
	public abstract int compareEndToStart(ResolvableStandoff other);
	
	public abstract int compareConfidenceTo(ResolvableStandoff other);
	
	public int compareTo(Object o) {
		if(o instanceof ResolvableStandoff) {
			ResolvableStandoff other = (ResolvableStandoff)o;
			int sc = compareStart(other);
			int ec = compareEnd(other);
			if(sc == -1) {
				return -1;
			} else if(sc == 1) {
				return 1;
			} else if(ec == -1) {
				return -1;
			} else if(ec == 1) {
				return 1;
			} else {
				return 0;
			}			
		} else {
			return 0;
		}
	}
	
	public boolean conflictsWith(ResolvableStandoff other) {
		// If this starts after (or at) the end of the other, no conflict
		if(compareStartToEnd(other) != -1) return false;
		// If this ends before (or at) the start of the other, no conflict
		if(compareEndToStart(other) != 1) return false;
		// Therefore, there must be a conflict
		return true;
	}
	
}
