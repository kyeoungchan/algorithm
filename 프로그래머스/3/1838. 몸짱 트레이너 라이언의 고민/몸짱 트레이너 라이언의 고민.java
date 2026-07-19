import java.util.*;

class Solution {
    
    int n, INF = 123456789;
    
    public int solution(int n, int m, int[][] timetable) {
        this.n = n;
        // 600~1320 -> 0~720
        int startTime = INF;
        int endTime = 0;
        int[] timeSum = new int[722];
        for (int i = 0; i < m; i++) {
            int t1 = timetable[i][0]-600;
            int t2 = timetable[i][1]-600;
            timeSum[t1]++;
            timeSum[t2+1]--;
            startTime = Math.min(startTime, t1);
            endTime = Math.max(endTime, t2);
        }
        int max = timeSum[startTime];
        for (int i = startTime+1; i <= endTime; i++) {
            timeSum[i] += timeSum[i-1];
            max = Math.max(max, timeSum[i]);
        }
        if (max == 1) return 0;
        // System.out.println(max);
        
        List<Integer> poses = new ArrayList<>();
        end: for (int dist = 2 * (n-1); dist > 0; dist--) {
            for (int sc = 0; sc < n; sc++) {
                poses.clear();
                poses.add(sc);
                for (int next = sc + 1; next < n * n; next++) {
                    boolean flag = false;
                    for (int prev: poses) {
                        int d = getDist(prev, next);
                        if (d < dist) {
                            flag = true;
                            break;
                        }
                    }
                    if (!flag) {
                        poses.add(next);
                        if (poses.size() == max) {
                            return dist;
                        }
                    }
                }
            }
        }
        return 1;
    }
    
    int getDist(int pose1, int pose2) {
        return Math.abs(pose1/n - pose2/n) + Math.abs(pose1%n - pose2 % n);
    }
}