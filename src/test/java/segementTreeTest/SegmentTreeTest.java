package segementTreeTest;

import static org.junit.Assert.assertEquals;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;
import org.junit.Test;
import segTree.STreeComputator;
import segTree.SegmentTree;

public class SegmentTreeTest {

	@Test
	public void test() {
		constructorTest1();
		constructorTest2();
	}
	
	private void constructorTest1() {
		Integer elem[] = {1, 2, 3, 4, 5, 6};
		SegmentTree<Integer> segTreeSum = new SegmentTree<Integer>(elem, new STreeComputator<Integer>() {

			public Integer compute(Integer o1, Integer o2) {
				return o1 + o2;
			}
		});
		
		assertEquals(new Integer(9), segTreeSum.query(1, 3));
		
		SegmentTree<Integer> segTreeMax = new SegmentTree<Integer>(elem, new STreeComputator<Integer>() {

			public Integer compute(Integer o1, Integer o2) {
				return Math.max(o1, o2);
			}
		});
		
		assertEquals(new Integer(4), segTreeMax.query(1, 3));
	}
	
	private void constructorTest2() {
		Map <Object, Integer> elem = new LinkedHashMap<Object, Integer>();
		elem.put(getDate("01/01/2014"), 1);
		elem.put(getDate("02/01/2014"), 2);
		elem.put(getDate("03/01/2014"), 3);
		elem.put(getDate("04/01/2014"), 4);
		elem.put(getDate("05/01/2014"), 5);
		elem.put(getDate("06/01/2014"), 6);
		
		SegmentTree<Integer> segTree = new SegmentTree<Integer>(elem, new STreeComputator<Integer>() {
			public Integer compute(Integer o1, Integer o2) {
				return o1+o2;
			}
		});
		
		assertEquals(new Integer(9), segTree.query(getDate("02/01/2014"), getDate("04/01/2014")));
	}
	
	private Date getDate(String dateStr){
		DateFormat df = new SimpleDateFormat("dd/MM/yyyy"); 
	    Date startDate;
	    try {
	        startDate = df.parse(dateStr);
	        return startDate;
	    } catch (ParseException e) {
	        e.printStackTrace();
	    }
		return null;
	}
}
