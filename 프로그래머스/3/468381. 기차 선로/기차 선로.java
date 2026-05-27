import java.util.*;

class Solution {
    
    int n, m, answer;
    int[] dr = new int[] {-1, 0, 1, 0}, dc = new int[] {0, 1, 0, -1};
    int[][] grid, visited;
    
    public int solution(int[][] grid) {
        n = grid.length;
        m = grid[0].length;
        this.grid = grid;
        visited = new int[n][m];
        
        answer = 0;
        
        visited[0][0]++;
        backtracking(0, 0, 1);
        
        return answer;
    }
    
    void backtracking(int r, int c, int d) {
        // printGrid();
        if (r == n - 1 && c == m - 1) {
            // System.out.println("도착!");
            if (allVisited()) {
                answer++;
                // System.out.println("answer: " + answer);
            }
            return;
        }
        
        int nr = r + dr[d];
        int nc = c + dc[d];
        if (nr < 0 || nr >= n || nc < 0 || nc >= m) return;
        int newType = grid[nr][nc];
        if (newType == -1) return;
        
        if (newType == 0) {
            for (int i = 1; i <= 7; i++) {
                if (!canOut(i, (d + 2) % 4)) continue;
                int nd = getNextDir(i, d);
                visited[nr][nc]++;
                grid[nr][nc] = i;
                backtracking(nr, nc, nd);
                grid[nr][nc] = 0;
                visited[nr][nc]--;
            }
        } else {
            if (!canOut(newType, (d + 2) % 4)) return;
            if (newType == 3 && visited[nr][nc] == 2) return;
            if (newType != 3 && visited[nr][nc] == 1) return;
            int nd = getNextDir(newType, d);
            visited[nr][nc]++;
            backtracking(nr, nc, nd);
            visited[nr][nc]--;
        }
    }
    
    boolean allVisited() {
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                if (grid[i][j] == -1 || grid[i][j] == 0) continue;
                if (grid[i][j] == 3 && visited[i][j] != 2) return false;
                if (grid[i][j] != 3 && visited[i][j] != 1) return false;
            }
        }
        return true;
    }
    
    boolean canOut(int type, int d) {
        if (type == 1) {
            return d % 2 == 1;
        } else if (type == 2) {
            return d % 2 == 0;
        } else if (type == 3) {
            return true;
        } else if (type == 4) {
            return d == 0 || d == 3;
        } else if (type == 5) {
            return d == 0 || d == 1;
        } else if (type == 6) {
            return d == 1 || d == 2;
        } else if (type == 7) {
            return d == 2 || d == 3;
        }

        // type이 0이거나 -1인 경우로, 호출되면 안되는 시점
        throw new RuntimeException("잘못된 호출1");
    }
    
    int getNextDir(int type, int inDir) {
        if (type <= 0) {
            // type이 0이거나 -1인 경우로, 호출되면 안되는 시점
            throw new RuntimeException("잘못된 호출2");
        } else if (type <= 3) {
            return inDir;
        } else if (type == 4) {
            return inDir == 2 ? 3 : 0;
        } else if (type == 5) {
            return inDir == 2 ? 1 : 0;
        } else if (type == 6) {
            return inDir == 0 ? 1 : 2;
        } else {
            return inDir == 0 ? 3 : 2;
        }
    }
    
    void printGrid() {
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                System.out.printf("%2d", grid[i][j]);
            }
            System.out.println();
        }
        System.out.println();
    }
}