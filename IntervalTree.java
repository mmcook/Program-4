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
		
		else if (contains(interval) == true) {
			throw new IllegalArgumentException();
		}
		
		else {
			insertHelper(interval, getRoot());
		}
		
		size++;
	}

	private void insertHelper(IntervalADT<T> interval, IntervalNode<T> root) {
		
		if (interval.compareTo(root.getInterval()) < 1 && root.getLeftNode() != null) {
			insertHelper(interval, root.getLeftNode());
		} else {
			root.setLeftNode(new IntervalNode<T>(interval));
		}
		
		if (interval.compareTo(root.getInterval()) > 1 && root.getRightNode() != null) {
			insertHelper(interval, root.getLeftNode());
		} else {
			root.setRightNode(new IntervalNode<T>(interval));
		}
	}
	@Override
	public void delete(IntervalADT<T> interval)
					throws IntervalNotFoundException, IllegalArgumentException {
		

		
		
	}

	@Override
	public IntervalNode<T> deleteHelper(IntervalNode<T> node,
					IntervalADT<T> interval)
					throws IntervalNotFoundException, IllegalArgumentException {
						
		if (node == null) {
			throw new IntervalNotFoundException("Something");
		}
		size--;
		
		if (node.getInterval().compareTo(interval) == 0) {
			tree.setInterval(node.getSuccessor().getInterval());
			
			if (node.getSuccessor().getRightNode() != null) {
				return deleteHelper(node.getRightNode(), node.getSuccessor().getInterval());
			}
			else {
				return node.getLeftNode();
			}
		}
		else if (node.getInterval().compareTo(interval) < 0) {
			node.setRightNode(deleteHelper(node.getRightNode(), node.getRightNode().getInterval()));
			return node;
		}
		else {
			node.setLeftNode(deleteHelper(node.getLeftNode(), node.getLeftNode().getInterval()));
			return node;
		}				
	}

	@Override
	public List<IntervalADT<T>> findOverlapping(
					IntervalADT<T> interval) {
		
		List<IntervalADT<T>> list = null;
		
		findOverlappingHelper(getRoot(),interval, list);
		
		return list;
	}

	private void findOverlappingHelper(IntervalNode<T> node, IntervalADT<T> interval, List<IntervalADT<T>> result) {
		if (node == null) 
		{
			return;
		}
		
		if(interval.overlaps(node.getInterval()))
			result.add(interval);
		
		if(interval.getStart().compareTo(node.getLeftNode().getMaxEnd()) >= 0)
			findOverlappingHelper(node.getLeftNode(), interval, result);
		
		else if(interval.getStart().compareTo(node.getRightNode().getMaxEnd()) >= 0)
			findOverlappingHelper(node.getRightNode(), interval, result);
		}
	
	@Override
	public List<IntervalADT<T>> searchPoint(T point) {
		
		if (point == null) {
			throw new IllegalArgumentException();
		}
		
		return null;
		// TODO Auto-generated method stub
	}

	@Override
	public int getSize() {
		return size;
	}

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

	@Override
	public boolean contains(IntervalADT<T> interval) {
		
		if (interval == null) {
			throw new IllegalArgumentException();
		}
		
		return containsHelper(interval, getRoot());
	}

	private boolean containsHelper(IntervalADT<T> interval, IntervalNode<T> node) {
		
		if (node == null) return false;
		
		if(node.getInterval().compareTo(interval) == 0) return true;
		
		//If interval comes before the nodes interval, go to the left node
		if(node.getInterval().compareTo(interval) > 0)
			return containsHelper(interval, node.getLeftNode());
		
		//If interval comes after the nodes interval, go to the right node
		else
			return containsHelper(interval, node.getRightNode());
}
	
	@Override
	public void printStats() {
		System.out.println("-----------------------------------------");
		System.out.println("Height: " + getHeight());
		System.out.println("Size: " + getSize());
		System.out.println("-----------------------------------------");
	}

}
