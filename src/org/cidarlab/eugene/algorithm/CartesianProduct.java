package org.cidarlab.eugene.algorithm;

import java.util.Arrays;
import java.util.Iterator;
import java.util.concurrent.TimeUnit;

public class CartesianProduct 
	implements Iterable<long[]>, Iterator<long[]> {
	
	private final long[] _lengths;
	private final long[] _indices;
	private boolean _hasNext = true;
	
	public CartesianProduct(long[] lengths) {
	    _lengths = lengths;
	    _indices = new long[lengths.length];
	}
	
	public boolean hasNext() {
	    return _hasNext;
	}
	
	public long[] next() {
		long[] result = Arrays.copyOf(_indices, _indices.length);
	    for (int i = _indices.length - 1; i >= 0; i--) {
	        if (_indices[i] == _lengths[i] - 1) {
	            _indices[i] = 0;
	            if (i == 0) {
	                _hasNext = false;
	            }
	        } else {
	            _indices[i]++;
	            break;
	        }
	    }
	    return result;
	}
	
	public Iterator<long[]> iterator() {
	    return this;
	}
	
	public void remove() {
	    throw new UnsupportedOperationException();
	}
	
	/**
	 * Usage example. Prints out
	 * 
	 * <pre>
	 * [0, 0, 0] a, NANOSECONDS, 1
	 * [0, 0, 1] a, NANOSECONDS, 2
	 * [0, 0, 2] a, NANOSECONDS, 3
	 * [0, 0, 3] a, NANOSECONDS, 4
	 * [0, 1, 0] a, MICROSECONDS, 1
	 * [0, 1, 1] a, MICROSECONDS, 2
	 * [0, 1, 2] a, MICROSECONDS, 3
	 * [0, 1, 3] a, MICROSECONDS, 4
	 * [0, 2, 0] a, MILLISECONDS, 1
	 * [0, 2, 1] a, MILLISECONDS, 2
	 * [0, 2, 2] a, MILLISECONDS, 3
	 * [0, 2, 3] a, MILLISECONDS, 4
	 * [0, 3, 0] a, SECONDS, 1
	 * [0, 3, 1] a, SECONDS, 2
	 * [0, 3, 2] a, SECONDS, 3
	 * [0, 3, 3] a, SECONDS, 4
	 * [0, 4, 0] a, MINUTES, 1
	 * [0, 4, 1] a, MINUTES, 2
	 * ...
	 * </pre>
	 */
	public static void main(String[] args) {
		long[] list0 = new long[] { 1 };
		long[] list1 = new long[] { 2, 3, 4 };
		long[] list2 = new long[] { 2, 3, 4 };
		long[] list3 = new long[] { 2, 3, 4 };
		long[] list4 = new long[] { 5 };
	
		long[] lengths = new long[] { list0.length, list1.length, list2.length, list3.length, list4.length };
	    for (long[] indices : new CartesianProduct(lengths)) {
	        System.out.println(
	                " " + list0[(int)indices[0]] //
	    	        + ", " + list1[(int)indices[1]] //
	                + ", " + list2[(int)indices[2]] //
	                + ", " + list3[(int)indices[3]]
	        		+ " " + list4[(int)indices[4]]); //
	    }
	}
}