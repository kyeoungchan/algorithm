import java.util.*;

class Solution {
    
    static class Triangle {
        int r, c, space;
        boolean left;
        
        Triangle(int r, int c, int space, boolean left) {
            this.r = r;
            this.c = c;
            this.space = space;
            this.left = left;
        }
    }
    
    int n, m, groupId;
    int[] dr = new int[] {-1, 0, 1, 0}, dc = new int[] {0, 1, 0, -1};
    int[][] grid;
    int[][][] visited;
    Deque<Triangle> q;
    public int solution(int[][] grid) {
        q = new ArrayDeque<>();
        this.grid = grid;
        n = grid.length;
        m = grid[0].length;
        // left/right
        visited = new int[n][m][2];
        int answer = 0;
        groupId = 0;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                int space = 0;
                if (visited[i][j][0] == 0) {
                    space = bfs(i, j, true);
                    answer = Math.max(answer, space);
                }
                if (visited[i][j][1] == 0) {
                    space = bfs(i, j, false);
                    answer = Math.max(answer, space);
                }
                // printGrid();
            }
        }
        
        return answer;
    }
    
    int bfs(int r, int c, boolean left) {
        groupId++;
        int thirdIdx = left ? 0 : 1;
        int result = 0;
        q.clear();
        visited[r][c][thirdIdx] = groupId;
        
        q.offer(new Triangle(r, c, 1, left));
        while (!q.isEmpty()) {
         
            Triangle cur = q.poll();
            result++;
            int d = 0;
            if (grid[cur.r][cur.c] == 1) {
                // /
                d = cur.left ? 2 : 0;
            } else {
                // \
                d = cur.left ? 1 : 3;
            }
            for (int i = 0; i < 2; i++) {
                d++;
                d %= 4;
                int nr = cur.r + dr[d];
                int nc = cur.c + dc[d];
                if (nr < 0 || nr >= n || nc < 0 || nc >= m || visited[nr][nc][0] == groupId || visited[nr][nc][1] == groupId) continue;
                if (grid[nr][nc] == 1) {
                    thirdIdx = d == 1 || d == 2 ? 0 : 1;
                } else {
                    thirdIdx = d == 0 || d == 1 ? 0 : 1;
                }
                visited[nr][nc][thirdIdx] = groupId;
                q.offer(new Triangle(nr, nc, cur.space + 1, thirdIdx == 0));
            }
        }
        return result;
    }
    void printGrid() {
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                char ch = grid[i][j] == 1 ? '/' : '\\';
                System.out.printf("[%2d %c %2d] ", visited[i][j][0], ch, visited[i][j][1]);
            }
            System.out.println();
        }
        System.out.println();
    }
}