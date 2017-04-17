/**
 *  * <p>This interval can be used to represent various things. For example, in
 * scheduling problem, this will represent the start and end dates for an
 * assignment. This Interval will be stored as a data member inside our
 * IntervalTree.</p>
 *  
 * <p>Note: there are no <i>setter</i> methods for the data members of this type,
 * therefore the data must be passed in as arguments to the constructor and 
 * saved as data members accordingly. The <code>Interval</code> class that you 
 * define that implements this ADT, must include a constructor of the form:</p>
 * 
 * <pre>public Interval(T start, T end, String label)</pre>
 * 
 * @param <T>
 *            the template param for start/end fields.
 * @author Meggie Cook
 */
public class Interval<T extends Comparable<T>> implements IntervalADT<T> {

    // TODO declare any needed data members
	private T start;
	private T end;
	private String label;
	
	
    public Interval(T start, T end, String label) {
        // TODO Auto-generated constructor stub
    	this.start = start;
    	this.end = end;
    	this.label = label;
    }

    /** Returns the start value (must be Comparable<T>) of the interval. */
    @Override
    public T getStart() {
        // TODO Auto-generated method stub
    	return start;
    }

    /** Returns the end value (must be Comparable<T>) of the interval. */
    @Override
    public T getEnd() {
        // TODO Auto-generated method stub
    	return end;
    }

    /** Returns the label for the interval. */
    @Override
    public String getLabel() {
        // TODO Auto-generated method stub
    	return label;
    }

	/**
	 * Return true if this interval overlaps with the other interval. 
	 * 
	 * <p>Note: two intervals [a, b], [c, d] will NOT overlap if either b &lt; c or d
	 * &lt; a. </p>
	 * 
	 * <p>In all other cases, they will overlap.</p>
	 * 
	 * @param other target interval to compare for overlap
	 * @return true if it overlaps, false otherwise.
	 * @throws IllegalArgumentException
	 *             if the other interval is null.
	 */
    @Override
    public boolean overlaps(IntervalADT<T> other) {
    	//Throw IllegalArgumentException if other is null
    	if(other == null)
    		throw new IllegalArgumentException();
    	
    	//If other's start or end is overlapping, return true.
    	
    	if( (start.compareTo(other.getStart()) <= 0 && end.compareTo(other.getStart()) >= 0) || 
    			(start.compareTo(other.getEnd()) <= 0 && end.compareTo(other.getEnd()) >= 0))
    		return true;
    	
    	//Otherwise, return false
    	return false;
    }

    /**
	 * Returns true if given point lies inside the interval.
	 * 
	 * @param point
	 *            to search
	 * @return true if it contains the point
	 */
    @Override
    public boolean contains(T point) {
    	//Return true if point is within the interval, false otherwise 
    	if(getStart().compareTo(point) <= 0 && getEnd().compareTo(point) >= 0)
    		return true;
    	return false;
    }
    
	/**
	 * Compares this interval with the other and return a negative value 
	 * if this interval comes before the "other" interval.  Intervals 
	 * are compared first on their start time.  The end time is only considered
	 * if the start time is the same.
	 * 
	 * <p>For example, if start times are different:</p>
	 * 
	 * <pre>
	 * [0,1] compared to [2,3]: returns -1 because 0 is before 2
	 * [2,3] compared to [0,1]: return 1 because 2 is after 0
	 * [0,4] compared to [2,3]: return -1 because 0 is before 2
	 * [2,3] compared to [0,4]: return 1 because 2 is after 0
	 * [0,3] compared to [2,4]: return -1 because 0 is before 2
	 * [2,4] compared to [0,3]: return 1 because 2 is after 0
	 * </pre>
	 * 
	 * <p>If start times are the same, compare based on end time:</p>
	 * <pre>
	 * [0,3] compared to [0,4]: return -1 because start is same and 3 is before 4
	 * [0,4] compared to [0,3]: return 1 because start is same and 4 is after 3</pre>
	 * 
	 * <p>If start times and end times are same, return 0</p>
	 * <pre>[0,4] compared to [0,4]: return 0</pre>
	 *
	 * @param other
	 *            the second interval to which compare this interval with
	 *            
	 * @return negative if this interval's comes before the other interval, 
	 * positive if this interval comes after the other interval,
	 * and 0 if the intervals are the same.  See above for details.
	 */
    @Override
    public int compareTo(IntervalADT<T> other) {
        // TODO Auto-generated method stub
    	
    	if(other == null)
    		throw new IllegalArgumentException();
    	
    	if(getStart().compareTo(other.getStart()) > 0) return 1;
    	
    	else if(getStart().compareTo(other.getStart()) < 0 ) return -1;
    	
    	else if(getStart().compareTo(other.getStart()) == 0)
    	{
    		if(getEnd().compareTo(other.getEnd()) > 0) return 1;
    		
    		else if(getEnd().compareTo(other.getEnd()) < 0) return -1;
    	}
    	
    	return 0;
    }
    
	/**
	 * Returns a specific string representation of the interval. It must return
	 * the interval in this form.
	 * 
	 *  <p>For example: If the interval's label is p1 and the start is 24 and the end is 45,
	 *  then the string returned is:</p>
	 *  
	 *  <pre>p1 [24, 45]</pre>
	 */
    public String toString()
    {
    	String s = label + " [" + start.toString() + ", " + end.toString() + "]";
    	return s;
    }
}
