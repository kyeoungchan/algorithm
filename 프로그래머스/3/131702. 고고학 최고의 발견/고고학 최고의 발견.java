class Solution {
    
    int n, answer, INF = 123456789;
    int[] dr = new int[] {-1, 0, 1, 0}, dc = new int[] {0, 1, 0, -1};
    
    public int solution(int[][] clockHands) {
        n = clockHands.length;
        
        answer = INF;
        // int[][] tempBoard = copyArray(clockHands);
        dfs(0, 0, clockHands);
        return answer;
    }
    
    void dfs(int pos, int temp, int[][] arr) {
        if (pos == n * n) {
            if (isComplete(arr)) {
                answer = Math.min(answer, temp);
            }
            return;
        }
        int r = pos / n;
        int c = pos % n;
        
        if (r == 0) {
            for (int i = 0; i < 4; i++) {
                int[][] tempBoard = copyArray(arr);
                rotate(r, c, i, tempBoard);
                dfs(pos + 1, temp + i, tempBoard);
            }    
        } else {
            int cnt = calcRotateCount(r, c, arr);
            int[][] tempBoard = arr;
            if (cnt > 0) {
                tempBoard = copyArray(arr);
                rotate(r, c, cnt, tempBoard);
            }
            dfs(pos + 1, temp + cnt, tempBoard);
        }
        
    }
    
    int[][] copyArray(int[][] source) {
        int[][] result = new int[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                result[i][j] = source[i][j];
            }
        }
        return result;
    }
    
    void rotate(int r, int c, int cnt, int[][] arr) {
        if (cnt == 0) return;
        arr[r][c] += cnt;
        arr[r][c] %= 4;
        for (int d = 0; d < 4; d++) {
            int nr = r + dr[d];
            int nc = c + dc[d];
            if (nr < 0 || nr >= n || nc < 0 || nc >= n) continue;
            arr[nr][nc] += cnt;
            arr[nr][nc] %= 4;
        }
    }
    
    boolean isComplete(int[][] arr) {
        for (int j = 0; j < n; j++) {
            if (arr[n-1][j] != 0) return false;
        }
        return true;
    }
    
    int calcRotateCount(int r, int c, int[][] arr) {
        return (4 - arr[r-1][c]) % 4;
    }
}