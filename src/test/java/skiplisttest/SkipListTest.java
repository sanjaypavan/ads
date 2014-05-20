package skiplisttest;

import static org.junit.Assert.assertEquals;

import java.util.Comparator;

import org.junit.Test;

import skiplist.SkipList;

public class SkipListTest {

    @Test
    public void test() {
        SkipList<Integer> skipList = new SkipList<Integer>(new Comparator<Integer>() {
            public int compare(Integer o1, Integer o2) {
                return o1.compareTo(o2);
            }
        });

        skipList.add(1);
        skipList.add(8);
        skipList.add(7);
        skipList.add(5);
        assertEquals(true, skipList.contains(7));
    }
}
