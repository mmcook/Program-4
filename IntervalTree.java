/**
 * 
 * The Interval Tree Class is responsible for handling the storage of the 
 * Interval Nodes. The code organizes the tree properly and also can 
 * handle the calculation of the MaxEnd variable.
 * 
 * @author Jake Vande Walle, Meggie Cook
 * 
 * 
 */

import java.util.ArrayList;
import java.util.List;

public class IntervalTree<T extends Comparable<T>> implements IntervalTreeADT<T> {
	
	// TODO declare any data members needed for this class
	private IntervalNode<T> tree;
	private int size = 0;
	
	public IntervalTree() {
		tree = null;
	}
	
	public IntervalTree(IntervalNode<T> root) {
		tree = root;
	}

	@Override
	public IntervalNode<T> getRoot() {
		return tree;
	}

	@Override
	public void insert(IntervalADT<T> interval)
					throws IllegalArgumentException {
		// TODO Auto-generated method stub
		if (interval == null) {
			throw new IllegalArgumentException();
		}
		
		if (contains(interval) == true) {
			throw new IllegalArgumentException();
		}
		if (tree == null) {
			tree = new IntervalNode<T>(interval);
		}
		else {
			insertHelper(interval, getRoot());
		}
		
		size++;
	}

	private void insertHelper(IntervalADT<T> interval, IntervalNode<T> node) {
		
		if (interval.compareTo(node.getInterval()) < 0 && node.getLeftNode() == null) 
		{
			if(interval.getEnd().compareTo(node.getMaxEnd()) > 0)
				node.setMaxEnd(interval.getEnd());
			node.setLeftNode(new IntervalNode<T>(interval));
		} 
		else if (interval.compareTo(node.getInterval()) < 0 && node.getLeftNode() != null)
		{
			insertHelper(interval, node.getLeftNode());
		}
		
		if (interval.compareTo(node.getInterval()) > 0 && node.getRightNode() == null) 
		{
			node.setRightNode(new IntervalNode<T>(interval));
		} 
		
		else if (interval.compareTo(node.getInterval()) > 0 && node.getRightNode() != null)
		{
			if(interval.getEnd().compareTo(node.getMaxEnd()) > 0)
				node.setMaxEnd(interval.getEnd());
			insertHelper(interval, node.getRightNode());
		}
	}
	
	@Override
	public void delete(IntervalADT<T> interval)
					throws IntervalNotFoundException, IllegalArgumentException {
		if (interval == null) {
			throw new IllegalArgumentException();
		}
		if (contains(interval) == false) {
			throw new IntervalNotFoundException("delete");
		}
		deleteHelper(getRoot(), interval);
		size--;
	}

	@Override
	public IntervalNode<T> deleteHelper(IntervalNode<T> node,
					IntervalADT<T> interval)
					throws IntervalNotFoundException, IllegalArgumentException {
						
		if (node == null) {
			throw new IntervalNotFoundException("Something");
		}
		
		if(interval == null)
			throw new IllegalArgumentException();
		
		
		if (node.getInterval().compareTo(interval) == 0) 
		{
			
			if (node.getRightNode() != null)
        	{
        		//get successor
        		IntervalNode<T> successor = node.getSuccessor();
        		node.setInterval(successor.getInterval());
        		
        		//delete
        		node.setRightNode(deleteHelper(node.getRightNode(), successor.getInterval()));
        		recalculateMaxEnd(node);
        		return node;
        	}
        	
        	else{
        		return node.getLeftNode();
        	}
			
		}
		
		//Traverse right subtree
		else if (node.getInterval().compareTo(interval) < 0) {
			if (node.getRightNode() == null)
        	{
        		throw new IntervalNotFoundException("<0" + interval.toString());
        	}
        	node.setRightNode(deleteHelper(node.getRightNode(), interval));
        	recalculateMaxEnd(node);
        	return node;
		}
		
		//Traverse left subtree
		else {
			
			if (node.getLeftNode() == null)
        	{
        		throw new IntervalNotFoundException(">0" + interval.toString());
        	}
        	node.setLeftNode(deleteHelper(node.getLeftNode(), interval));
		}	
		recalculateMaxEnd(node);
		return node;
	}
	
	private T recalculateMaxEnd(IntervalNode<T> nodeToRecalculate)
	{
		IntervalNode<T> curr = nodeToRecalculate;
		
		while(curr.getRightNode() != null)
			curr = nodeToRecalculate.getRightNode();
		
		
		return curr.getMaxEnd();
	}
	
	/**
	 * Find and return a list of all intervals that overlap with the given interval. 
	 * 
	 * <p>Tip: Define a helper method for the recursive call and call it with root, 
	 * the interval, and an empty list.  Then, return the list that has been built.</p>
	 * 
	 * <pre>   private void findOverlappingHelper(IntervalNode node, IntervalADT interval, List<IntervalADT<T>> result)</pre>
	 * 
	 * <p>Pseudo-code for such a recursive findingOverlappingHelper method.</p>
	 * 
	 * <ol>
	 * <li>if node is null, return</li>
	 * <li>if node interval overlaps with the given input interval, add it to the result.</li>
	 * <li>if left subtree's max is greater than or equal to the interval's start, call findOverlappingHelper in the left subtree.</li>
	 * <li>if right subtree's max is greater than or equal to the interval's start, call call findOverlappingHelper in the rightSubtree.</li>
	 * </ol>
	 *  
	 * @param interval the interval to search for overlapping
	 * 
	 * @return list of intervals that overlap with the input interval.
	 */
	@Override
	public List<IntervalADT<T>> findOverlapping(
					IntervalADT<T> interval) {
		
		List<IntervalADT<T>> list = new ArrayList<IntervalADT<T>>();
		
		findOverlappingHelper(getRoot(), interval, list);
		
		return list;
	}

	private void findOverlappingHelper(IntervalNode<T> node, IntervalADT<T> interval, List<IntervalADT<T>> result) {
		
		if (node == null) 
		{
			return;
		}
		//if the node contains the start or end or interval, add to list.
		if (node.getInterval().overlaps(interval))	
		{
			result.add(node.getInterval());
		}

		//if LMax is larger, call on LSubtree 
		//TODO: Check that the greater than symbol isn't backwards
		if (node.getLeftNode() != null)
		{
			if (node.getLeftNode().getMaxEnd().compareTo(interval.getStart()) >= 0)
			{
				findOverlappingHelper(node.getLeftNode(), interval, result);
			}
		}
		if (node.getRightNode() != null)
		{
			//if RMaxEnd is larger, (independent) call on RSubtree
			if (node.getRightNode().getMaxEnd().compareTo(interval.getStart()) >= 0)
			{
				findOverlappingHelper(node.getRightNode(), interval, result);
			}
		}
		return;
	}
	
	/**
	 * Search and return a list of all intervals containing a given point. 
	 * This method may return an empty list. 
	 * 
	 * <p>For example: if the intervals stored in the tree are:</p>
	 * <pre>
	 * p1 [5, 10]
	 * p2 [2, 18]
	 * p3 [12, 30]</pre>
	 * 
	 * <p>and the input point is 16, it will return a list containing the intervals:</p>
	 * <pre>
	 * p2 [2, 18]
	 * p3 [12, 30]</pre>
	 * 
	 * @throws IllegalArgumentException if point is null
	 * 
	 * @param point
	 *            input point to search for.
	 * @return List of intervals containing the point.
	 */
	@Override
	public List<IntervalADT<T>> searchPoint(T point) {
		
		if (point == null) {
			throw new IllegalArgumentException();
		}
		
		List<IntervalADT<T>> list = new ArrayList<IntervalADT<T>>();
		
		searchPointHelper(point, list, getRoot());
		
		return list;
	}
	
	private void searchPointHelper(T point, List<IntervalADT<T>> list, IntervalNode<T> node)
	{
		if(node == null)
			return;
		
		if(node.getInterval().contains(point))
			list.add(node.getInterval());
		
		if(node.getInterval().getStart().compareTo(point) > 0)
			searchPointHelper(point, list, node.getLeftNode());
	
		else if(node.getInterval().getEnd().compareTo(point) < 0)
			searchPointHelper(point, list, node.getRightNode());
	}

	/**
	 * Get the size of the interval tree. The size is the total number of
	 * nodes present in the tree. 
	 * 
	 * <p>Tip: Define and call a recursive helper function to calculate this.</p>
	 * 
	 * @return int number of nodes in the tree.
	 */
	@Override
	public int getSize() {
		return size;
	}

	/**
	 * Return the height of the interval tree at the root of the tree. 
	 * 
	 * <p>Tip: Define and call a recursive helper function to calculate this for a given node.</p>
	 * 
	 * @return the height of the interval tree
	 */
	@Override
	public int getHeight() {
		
		  if (size == 0) return 0;
		  
		  else if (size == 1) return 1;
		  
		  else return (int) (Math.sqrt(size) + 1);
		 
		
		
		//return getHeightHelper(getRoot());
		// TODO Auto-generated method stub
	}
	
	/*private int getHeightHelper(IntervalNode<T> node) {
		if (node == null) { // Not totally correct but it is a start
			return 0;
		}
		return 1 + getHeightHelper(node.getLeftNode());
	}*/

	/**
	 * Returns true if the tree contains an exact match for the start and end of the given interval.
	 * The label is not considered for this operation.
	 *  
	 * <p>Tip: Define and call a recursive helper function to call with root node 
	 * and the target interval.</p>
	 * 
	 * @param interval
	 * 				target interval for which to search the tree for. 
	 * @return boolean 
	 * 			   	representing if the tree contains the interval.
	 *
	 * @throws IllegalArgumentException
	 *             	if interval is null.
	 * 
	 */
	@Override
	public boolean contains(IntervalADT<T> interval) {
		
		if (interval == null) {
			throw new IllegalArgumentException();
		}
		
		return containsHelper(interval, getRoot());
	}

	private boolean containsHelper(IntervalADT<T> interval, IntervalNode<T> node) {
		
		if (node == null) 
			return false;
		
		if(node.getInterval().compareTo(interval) == 0) 
			return true;
		
		//If interval comes before the nodes interval, go to the left node
		if(node.getInterval().compareTo(interval) > 0)
			return containsHelper(interval, node.getLeftNode());
		
		//If interval comes after the nodes interval, go to the right node
		else
			return containsHelper(interval, node.getRightNode());
}
	
	/**
	 * Print the statistics of the tree in the below format
	 * <pre>
	 *	-----------------------------------------
	 *	Height: 2
	 *	Size: 3
	 *	-----------------------------------------
	 *	</pre> 
	 */
	@Override
	public void printStats() {
		System.out.println("-----------------------------------------");
		System.out.println("Height: " + getHeight());
		System.out.println("Size: " + getSize());
		System.out.println("-----------------------------------------");
	}
}