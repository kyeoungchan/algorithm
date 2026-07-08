import java.util.*;

class Solution {
    public int solution(int n, int[][] data) {
        
        Arrays.sort(data, (d1, d2) -> d1[0] == d2[0] ? Integer.compare(d1[1], d2[1]) : Integer.compare(d1[0], d2[0]));
        
        List<Integer> xList = new ArrayList<>();
        TreeMap<Integer, List<Integer>> xToYMap = new TreeMap<>();
        
        for (int[] d: data) {
            int x = d[0];
            int y = d[1];
            if (!xList.contains(x)) xList.add(x);
            if (!xToYMap.containsKey(x)) xToYMap.put(x, new ArrayList<>());
            xToYMap.get(x).add(y);
        }
        
        int answer = 0;
        
        for (int[] d : data) {
            int leftX = d[0];
            int leftY = d[1];
            int bottomLine = -1;
            int topLine = Integer.MAX_VALUE;
            for (int rightX: xList) {
                if (rightX <= leftX) continue;
                int tempTopLine = topLine;
                for (int rightY: xToYMap.get(rightX)) {
                    if (rightY == leftY) continue;
                    
                    if (rightY < leftY) {
                        if (bottomLine <= rightY) answer++;
                        bottomLine = Math.max(bottomLine, rightY);
                    } else if (leftY < rightY) {
                        if (rightY <= topLine) answer++;
                        tempTopLine = Math.min(tempTopLine, rightY);
                    }
                }
                topLine = tempTopLine;
            }
        }
        
        return answer;
    }
}