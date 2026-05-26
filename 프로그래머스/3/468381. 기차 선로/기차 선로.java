class Solution {
    
    int n, m, answer;
    int[] dr = new int[] {-1, 0, 1, 0}, dc = new int[] {0, 1, 0, -1};
    int[][] grid, visited;
    
    public int solution(int[][] grid) {
        this.grid = grid;
        n = grid.length;
        m = grid[0].length;
        visited = new int[n][m];
        
        answer = 0;
        
        visited[0][0]++;
        backtracking(0, 0, 1);
        
        return answer;
    }
    
    void backtracking(int r, int c, int d) {
        // System.out.println("d:" + d);
        // printGrid();
        if (r == n - 1 && c == m - 1) {
            if (allVisited()) {
                answer++;
            }
            // printGrid();
            // System.out.println("answer: " + answer);
            return;
        }
        
        int nr = r + dr[d];
        int nc = c + dc[d];
        // 낭떠러지면 리턴
        if (nr < 0 || nr >= n || nc < 0 || nc >= m) return;
        // System.out.println("nr: " + nr + ", nc: " + nc);
        int nextType = grid[nr][nc];
        
        // 벽이면 리턴
        if (nextType == -1) return;
        
        if (nextType == 0) {
            for (int i = 1; i <= 7; i++) {
                // 저쪽에서 이쪽으로 연결될 수 없는 경우라면 스킵
                if (!canOut(i, (d + 2) % 4)) continue;
                int nd = getNextDir(i, d);
                // System.out.println("nd: " + nd);
                grid[nr][nc] = i;
                visited[nr][nc]++;
                backtracking(nr, nc, nd);
                visited[nr][nc]--;
                grid[nr][nc] = 0;
            }
        } else {
            // 3이 아니고 한 번 이상 방문한적이 있다면 리턴
            if (nextType != 3 && visited[nr][nc] > 0) return;
            
            // 3일 때 2번 방문한적이 있다면 리턴
            if (nextType == 3 && visited[nr][nc] >= 2) return;

            // 저쪽에서 이쪽으로 연결될 수 없는 경우라면 리턴
            if (!canOut(nextType, (d + 2) % 4)) return;
            
            int nd = getNextDir(nextType, d); 
            visited[nr][nc]++;
            backtracking(nr, nc, nd);
            visited[nr][nc]--;
        }
        
        
    }
    
    int getNextDir(int type, int curD) {
        // 0과 -1의 경우는 생각지 않는다.
        if (type <= 3) {
            return curD;
        } else if (type == 4) {
            return curD == 1 ? 0 : 3;
        } else if (type == 5) {
            return curD == 2 ? 1 : 0;
        } else if (type == 6) {
            return curD == 3 ? 2 : 1;
        } else {
            return curD == 1 ? 2 : 3;
        }
    }
    
    boolean canOut(int curType, int d) {
        if (curType < 3) return (curType + d) % 2 == 0;
        else if (curType == 3) return true;
        else return (curType - d) % 4 == 0 || (curType - d) % 4 == 1;
    }
    
    boolean allVisited() {
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                if (grid[i][j] == 0 || grid[i][j] == -1) continue;
                if (grid[i][j] == 3 && visited[i][j] != 2) return false;
                if (visited[i][j] == 0) return false;
            }
        }
        return true;
    }
    
    private void printGrid() {
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                System.out.printf("%2d", grid[i][j]);
            }
            System.out.println();
        }
        System.out.println();
    }
}