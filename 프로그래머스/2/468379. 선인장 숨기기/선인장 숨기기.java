import java.util.*;

class Solution {
    
    int m, n, h, w;
    int[][] grid;
    
    public int[] solution(int m, int n, int h, int w, int[][] drops) {
        this.m = m;
        this.n = n;
        this.h = h;
        this.w = w;
        
        grid = new int[m][n];
        for (int i = 0; i < m; i++) {
            Arrays.fill(grid[i], drops.length);
        }
        
        for (int i = 0; i < drops.length; i++) {
            int r = drops[i][0];
            int c = drops[i][1];
            if (grid[r][c] == drops.length) {
                grid[r][c] = i;
            }
        }
        
        int left = 0;
        int right = drops.length;
        
        int[] answer = new int[] {0, 0};
        
        while (left <= right) {
            int mid = left + (right - left) / 2;
            int[] result = getDriedZone(mid);
            
            if (result != null) {
                answer = result;
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }
        
        return answer;
    }
    
    int[] getDriedZone(int mid) {
        int[][] pSum = new int[m + 1][n + 1];
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                int val = grid[i][j] <= mid ? 1 : 0;
                // if (grid[i][j] <= mid) pSum[i + 1][j + 1]++;
                pSum[i + 1][j + 1] = pSum[i][j + 1] + pSum[i + 1][j] - pSum[i][j] + val;
            }
        }
        
        for (int i = h; i <= m; i++) {
            for (int j = w; j <= n; j++) {
                int val = pSum[i][j] - pSum[i - h][j] - pSum[i][j - w] + pSum[i - h][j - w];
                if (val == 0) return new int[] {i - h, j - w};
            }
        }
        return null;
    }
}