import java.util.*;

class Solution {
    
    
    int n, max, INF = 123456789;
    boolean[][] used;
    
    public int solution(int n, int m, int[][] timetable) {
        this.n = n;
        // 600~1,320 -> 0~720
        int[] time = new int[722];
        for (int[] tt: timetable) {
            int start = tt[0] - 600;
            int end = tt[1] - 600;
            time[start]++;
            time[end + 1]--;
        }
        
        max = time[0];
        for (int i = 1; i < 722; i++) {
            time[i] += time[i - 1];
            max = Math.max(max, time[i]);
        }
        if (max == 1) return 0;
        
        List<Integer> list = new ArrayList<>();
        for (int dist = 2 * (n-1); dist >= 1; dist--) {
            for (int sc = 0; sc < n; sc++) {
                list.clear();
                list.add(sc);
                for (int i = 0; i < n; i++) {
                    for (int j = 0; j < n; j++) {
                        // System.out.println(list);
                        if (i == 0 && j <= sc) continue;
                        
                        boolean flag = false;
                        int pos = i * n + j;
                        for (int pos2: list) {
                            if (getDist(pos, pos2) < dist) {
                                flag = true;
                                break;
                            }
                        }
                        
                        if (!flag) {
                            list.add(pos);
                            if (list.size() >= max) return dist;
                        }
                    }
                }
            }
        }
        return 1;
    }
    
    
    int getDist(int pos1, int pos2) {
        int r1 = pos1/n;
        int c1 = pos1%n;
        int r2 = pos2/n;
        int c2 = pos2%n;
        return Math.abs(r1 - r2) + Math.abs(c1 - c2);
    }
}