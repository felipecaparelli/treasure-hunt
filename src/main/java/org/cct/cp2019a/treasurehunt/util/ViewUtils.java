package org.cct.cp2019a.treasurehunt.util;

import org.cct.cp2019a.treasurehunt.model.BoardSquare;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Class the should be used to create the view elements
 */
public class ViewUtils {

    /**
     * Build the view grid headers
     * @param gameBoardGrid
     * @return
     */
    public static Set<String> buildViewGridHeaders(Map<String, List<BoardSquare>> gameBoardGrid) {
        return gameBoardGrid.keySet().stream().sorted().collect(Collectors.toCollection(LinkedHashSet::new));
    }

    /**
     * Build the view rows
     * @param gameBoardGrid
     * @return
     */
    public static List<List<Integer>> buildViewGridRows(Map<String, List<BoardSquare>> gameBoardGrid) {
        List<List<Integer>> rows = new ArrayList<>(gameBoardGrid.size());
        for (int cols = 0; cols < gameBoardGrid.size(); cols++) {
            rows.add(Arrays.asList(1,2,3,4,5,6,7,8,9,10));
        }
        return rows;
    }
}
