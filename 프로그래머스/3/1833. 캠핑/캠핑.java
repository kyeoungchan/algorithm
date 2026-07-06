import java.util.*;

class Solution {
    public int solution(int n, int[][] data) {
        Arrays.sort(data, (d1, d2) -> d1[0] == d2[0] ? Integer.compare(d1[1], d2[1]) : Integer.compare(d1[0], d2[0]));
        
        int answer = 0;
        
        for (int i = 0; i < n - 1; i++) {
            go: for (int j = i + 1; j < n; j++) {
                if (isParallel(data[i], data[j])) continue;
                int up = Math.max(data[i][1], data[j][1]);
                int down = Math.min(data[i][1], data[j][1]);
                for (int k = i + 1; k < j; k++) {
                    // 다른 쐐기가 경계면에는 존재 가능
                    if (data[k][0] == data[i][0] || data[k][0] == data[j][0]) continue;
                    
                    if (down < data[k][1] && data[k][1] < up) {
                        continue go;
                    }
                }
                answer++;
            }
        }
        
        return answer;
    }
    
    boolean isParallel(int[] d1, int[] d2) {
        return d1[0] == d2[0] || d1[1] == d2[1];
    }
}