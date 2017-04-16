
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

    @Override
    public T getStart() {
        // TODO Auto-generated method stub
    	return start;
    }

    @Override
    public T getEnd() {
        // TODO Auto-generated method stub
    	return end;
    }

    @Override
    public String getLabel() {
        // TODO Auto-generated method stub
    	return label;
    }

    @Override
    public boolean overlaps(IntervalADT<T> other) {
    	//Throw IllegalArgumentException if other is null
    	if(other == null)
    		throw new IllegalArgumentException();
    	
    	//If other's start or end is overlapping, return true.
    	
//    	if( (start.compareTo(other.getStart()) <= 0 && end.compareTo(other.getStart()) >= 0) || 
//    			(start.compareTo(other.getEnd()) <= 0 && end.compareTo(other.getEnd()) >= 0))
    	
    	if(getEnd().compareTo(other.getStart()) >= 0 || getStart().compareTo(other.getEnd()) >= 0)
    		return true;
    	
    	//Otherwise, return false
    	return false;
    }

    @Override
    public boolean contains(T point) {
    	//Return true if point is within the interval, false otherwise 
    	if(getStart().compareTo(point) <= 0 && getEnd().compareTo(point) >= 0)
    		return true;
    	return false;
    }
    

    @Override
    public int compareTo(IntervalADT<T> other) {
        // TODO Auto-generated method stub
    	
    	if(getStart().compareTo(other.getStart()) > 0) return 1;
    	
    	else if(getStart().compareTo(other.getStart()) < 0 ) return -1;
    	
    	else if(getStart().compareTo(other.getStart()) == 0)
    	{
    		if(getEnd().compareTo(other.getEnd()) > 0) return 1;
    		
    		else if(getEnd().compareTo(other.getEnd()) < 0) return -1;
    	}
    	
    	return 0;
    }
    
    public String toString()
    {
    	String s = label + " [" + start.toString() + ", " + end.toString() + "]";
    	return s;
    }
}
