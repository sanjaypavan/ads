package disjointsettest;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import disjointsets.DisjointSets;

public class DisjointSetTest {

    @Test
    public void test() {
        DisjointSets disjointset = new DisjointSets();
        for (int i = 1; i <= 5; i++) {
            disjointset.createSet(i);
        }

        disjointset.union(1, 5);
        disjointset.union(5, 3);
        System.out.println(disjointset.getNumberOfDisjointSets());
        assertEquals(3, disjointset.getNumberOfDisjointSets());
    }
}
