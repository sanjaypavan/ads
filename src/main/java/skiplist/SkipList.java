package skiplist;

import java.util.Comparator;
import java.util.LinkedList;
import java.util.Random;

public class SkipList<T> {
    private final Comparator<T> comparator;
    private static final Object INFINITY = new Object();
    private SkipListNode        bottomList;
    private SkipListNode        topList;
    private final Random        random   = new Random();

    @SuppressWarnings("unchecked")
    public SkipList(final Comparator<T> comparator) {
        this.comparator = comparator;
        bottomList = topList = new SkipListNode((T) INFINITY);
    }

    public final boolean add(final T value) {
        SkipListNode newNode = new SkipListNode(value);
        LinkedList<SkipListNode> path = pathToSmallerNode(newNode);
        insertAndPromote(newNode.value, path, null);
        if (flipCoin()) {
            insertAndPromote(value, path, newNode);
        }
        return true;
    }

    private void insertAndPromote(T value, LinkedList<SkipListNode> path, final SkipListNode lastCreatedNode) {
        SkipListNode newNode = new SkipListNode(value);
        if (path.isEmpty()) {
            SkipListNode newTop = createList(newNode, lastCreatedNode);
            addAtTheTop(newTop);

        } else {
            SkipListNode nodeToInsertAfter = path.pop();
            linkToPreviousNode(newNode, nodeToInsertAfter);

            linkToNodeBelow(newNode, lastCreatedNode);
        }
    }

    private SkipListNode createList(SkipListNode newNode, SkipListNode below) {
        @SuppressWarnings("unchecked")
        SkipListNode newTop = new SkipListNode((T) INFINITY);
        newTop.next = newNode;
        newNode.next = newTop;
        linkToNodeBelow(newNode, below);
        return newTop;
    }

    private void addAtTheTop(SkipListNode newTop) {
        newTop.down = topList;
        topList.up = newTop;
        topList = newTop;
    }

    private void linkToNodeBelow(SkipListNode newNode, SkipListNode below) {
        if (below != null) {
            below.up = newNode;
            newNode.down = below;
        }
    }

    private void linkToPreviousNode(SkipListNode newNode, SkipListNode nodeToInsertAfter) {
        SkipListNode next = nodeToInsertAfter.next;
        nodeToInsertAfter.next = newNode;
        newNode.prev = nodeToInsertAfter;
        newNode.next = next;
        if (next != null) {
            next.prev = newNode;
        }
    }

    private LinkedList<SkipListNode> pathToSmallerNode(SkipListNode node) {
        SkipListNode list = topList;
        LinkedList<SkipListNode> path = new LinkedList<SkipListNode>();
        while (list != null) {
            while (list.next != null && compare(list.next.value, node.value) <= 0) {
                list = list.next;
            }
            path.add(list);
            list = list.down;
        }
        return path;
    }

    public final T find(final T t) {
        SkipListNode foundNode = findNodeFromHighLevel(t);
        return foundNode == null ? null : foundNode.value;
    }

    @SuppressWarnings("unchecked")
    public final boolean contains(final Object t) {
        SkipListNode foundElem = findNodeFromHighLevel((T) t);
        return foundElem != null;
    }

    public final boolean isEmpty() {
        return bottomList.next == null;
    }

    private SkipListNode findNodeFromHighLevel(final T t) {
        SkipListNode list = topList;
        while (list != null) {
            while (list.next != null && compare((T) list.next.value, t) <= 0) {
                list = list.next;
            }
            if (compare((T) list.value, t) == 0) {
                return list;
            }
            list = list.down;
        }
        return null;
    }

    @SuppressWarnings("unchecked")
    public final void clear() {
        topList = bottomList = new SkipListNode((T) INFINITY);
    }

    private boolean flipCoin() {
        return random.nextBoolean() && random.nextBoolean();
    }

    private int compare(final T o1, final T o2) {
        if (o1 == INFINITY) {
            return -1;
        } else if (o2 == INFINITY) {
            return 1;
        } else {
            return comparator.compare(o1, o2);
        }
    }

    private class SkipListNode {
        public T value;
        public SkipListNode up, down, next, prev;

        public SkipListNode(T value) {
            this.value = value;
        }

        public String toString() {
            return value.toString();
        }
    }
}
