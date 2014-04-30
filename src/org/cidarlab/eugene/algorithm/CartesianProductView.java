package org.cidarlab.eugene.algorithm;

import java.util.AbstractList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.ArrayList;

/**
* A random access view of tuples of a cartesian product of ArrayLists
*
* Orders tuples in the natural order of the cartesian product
*
* @param T the type for both the values and the stored tuples, ie. values of the cartesian factors are singletons
* While the type of input sets is List<T> with elements being treated as singletons
*
*/
abstract public class CartesianProductView<T> 
	extends AbstractList<T> {

	private final List<List<T>> factors;
	private final int size;
	
	/**
	 * @param factors the length of the factors (ie. the elements of the factors argument) should not change,
	 *  otherwise get may not return all tuples, or throw exceptions when trying to access the factors outside of range
	 */
	public CartesianProductView(List<List<T>> factors) {
	    this.factors = new ArrayList<List<T>>(factors);
	    Collections.reverse(this.factors);
	    int acc = 1;
	    for (Iterator<List<T>> iter = this.factors.iterator(); iter.hasNext(); ) {
	        acc *= iter.next().size();
	    }
	    this.size = acc;
	}
	
	@Override
	public T get(int index) {
	    if (index < 0 || index >= size()) {
	        throw new IndexOutOfBoundsException(String.format("index %d > size() %d", index, size()));
	    }
	
	    T acc = null;
	    for (Iterator<List<T>> iter = factors.iterator(); iter.hasNext();) {
	        List<T> set = iter.next();
	        acc = makeTupleOrSingleton(set.get(index % set.size()), acc);
	        index /= set.size();
	    }
	    return acc;
	}
	
	@Override
	public int size() {
	    return size;
	}
	
	private T makeTupleOrSingleton(T left, T right) {
	    if (right == null) {
	        return left;
	    }
	    return makeTuple(left, right);
	}
	
	/**
	 *
	 * @param left      a singleton of a value
	 * @param right     a tuple of values taken from the cartesian product factors, with null representing the empty set
	 * @return          the sum of left and right, with the value of left being put in front
	 */
	abstract protected T makeTuple(T left, T right);
}