package disjointsets;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class DisjointSets {
    private List<Map<Integer, Set<Integer>>> disjointSet;

    public DisjointSets() {
        disjointSet = new ArrayList<Map<Integer, Set<Integer>>>();
    }

    public final void createSet(int elem) {
        Map<Integer, Set<Integer>> map = new HashMap<Integer, Set<Integer>>();
        Set<Integer> set = new HashSet<Integer>();
        set.add(elem);
        map.put(elem, set);
        disjointSet.add(map);
    }

    public final int findSet(int elem) {
        for (Map<Integer, Set<Integer>> map : disjointSet) {
            Set<Integer> keys = map.keySet();
            for (Integer key : keys) {
                Set<Integer> set = map.get(key);
                if (set.contains(elem)) {
                    return key;
                }
            }
        }
        return -1;
    }

    public void union(int first, int second) {
        int firstRep = findSet(first);
        int secondRep = findSet(second);

        Set<Integer> firstSet = null;
        Set<Integer> secondSet = null;

        for (Map<Integer, Set<Integer>> map : disjointSet) {
            if (map.containsKey(firstRep)) {
                firstSet = map.get(firstRep);
            } else if (map.containsKey(secondRep)) {
                secondSet = map.get(secondRep);
            }
        }

        if (firstSet != null && secondSet != null) {
            firstSet.addAll(secondSet);
        }

        for (int index = 0; index < disjointSet.size(); index++) {
            Map<Integer, Set<Integer>> map = disjointSet.get(index);
            if (map.containsKey(firstRep)) {
                map.put(firstRep, firstSet);
            } else if (map.containsKey(secondRep)) {
                map.remove(secondRep);
                disjointSet.remove(index);
            }
        }
    }

    public int getNumberOfDisjointSets() {
        return disjointSet.size();
    }
}
