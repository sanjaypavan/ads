package segTree;

import java.util.HashMap;
import java.util.Map;

public class SegmentTree<E> {

    private Object               tree[];
    private int                  maxSize;
    private int                  height;
    private final int            STARTINDEX = 0;
    private final int            ENDINDEX;
    private final int            ROOT       = 0;

    private STreeComputator<E>   compRef;

    private Map<Object, Integer> indexMap   = new HashMap<Object, Integer>();

    public SegmentTree(E[] elem, STreeComputator<E> computatorRef) {
        ENDINDEX = initializeTree(elem, computatorRef) - 1;
        constructSegmentTreeUtil(elem, STARTINDEX, ENDINDEX, ROOT);
    }

    private int initializeTree(E[] elem, STreeComputator<E> computatorRef) {

        int size = elem.length;
        height = (int) (Math.ceil(Math.log(size) / Math.log(2)));
        maxSize = 2 * (int) Math.pow(2, height) - 1;
        tree = new Object[maxSize];
        this.compRef = computatorRef;
        return size;
    }

    @SuppressWarnings("unchecked")
    public SegmentTree(Map<Object, E> elem, STreeComputator<E> computatorRef) {

        E[] values = (E[]) new Object[elem.size()];
        int index = 0;

        for (Object key : elem.keySet()) {
            indexMap.put(key, index);
            values[index] = elem.get(key);
            index++;
        }
        ENDINDEX = initializeTree(values, computatorRef) - 1;
        constructSegmentTreeUtil(values, STARTINDEX, ENDINDEX, ROOT);
    }

    public final E query(int qStart, int qEnd) {
        if (qStart < 0 || qEnd > tree.length) {
            return null;
        }
        return queryUtiil(STARTINDEX, ENDINDEX, qStart, qEnd, ROOT);
    }

    public final E query(Object qStart, Object qEnd) {

        if (indexMap.isEmpty() || !indexMap.containsKey(qStart) || !indexMap.containsKey(qEnd)) {
            return null;
        }

        return queryUtiil(STARTINDEX, ENDINDEX, indexMap.get(qStart), indexMap.get(qEnd), ROOT);
    }

    @SuppressWarnings("unchecked")
    private E constructSegmentTreeUtil(E[] elem, int startIndex, int endIndex, int current) {
        if (startIndex == endIndex) {
            tree[current] = elem[startIndex];
            return (E) tree[current];
        }

        int mid = mid(startIndex, endIndex);
        tree[current] = this.compRef.compute(constructSegmentTreeUtil(elem, startIndex, mid, leftChild(current)),
                constructSegmentTreeUtil(elem, mid + 1, endIndex, rightChild(current)));

        return (E) tree[current];
    }

    @SuppressWarnings("unchecked")
    private E queryUtiil(int startIndex, int endIndex, int qStart, int qEnd, int current) {

        if (qStart <= startIndex && qEnd >= endIndex) {
            return (E) tree[current];
        }
        if (endIndex < qStart || startIndex > qEnd) {
            return null;
        }
        int mid = mid(startIndex, endIndex);

        E lvalue = queryUtiil(startIndex, mid, qStart, qEnd, leftChild(current));
        E rvalue = queryUtiil(mid + 1, endIndex, qStart, qEnd, rightChild(current));
        if (lvalue == null) {
            return rvalue;
        } else if (rvalue == null) {
            return lvalue;
        } else {
            return this.compRef.compute(lvalue, rvalue);

        }
    }

    private int leftChild(int pos) {
        return 2 * pos + 1;
    }

    private int rightChild(int pos) {
        return 2 * pos + 2;
    }

    private int mid(int start, int end) {
        return (start + (end - start) / 2);
    }
}
