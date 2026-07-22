import java.util.*;

class Solution {
    int n, m, answer, INF = 123456789, redTarget, blueTarget;
    int[] dr = new int[] {-1, 0, 1, 0}, dc = new int[] {0, 1, 0, -1};
    int[][] maze;
    
    public int solution(int[][] maze) {
        this.maze = maze;
        answer = INF;
        n = maze.length;
        m = maze[0].length;
        
        int redStart = -1;
        int blueStart = -1;
        redTarget = -1;
        blueTarget = -1;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                int pos = i * m + j;
                if (maze[i][j] == 1) redStart = pos;
                else if (maze[i][j] == 2) blueStart = pos;
                else if (maze[i][j] == 3) redTarget = pos;
                else if (maze[i][j] == 4) blueTarget = pos;
            }
        }
        
        boolean[][][] visited = new boolean[n][m][2];
        visited[redStart/m][redStart%m][0] = true;
        visited[blueStart/m][blueStart%m][1] = true;
        dfs(redStart, blueStart, visited, 0);
        
        if (answer == INF) answer = 0;
        return answer;
    }
    
    void dfs(int curRed, int curBlue, boolean[][][] visited, int turn) {
        // System.out.println("curRed: " + curRed + ", curBlue: " + curBlue + ", turn: " + turn);
        if (curRed == redTarget && curBlue == blueTarget) {
            answer = Math.min(answer, turn);
            return;
        }
        
        int rr = curRed / m;
        int rc = curRed % m;
        int br = curBlue / m;
        int bc = curBlue % m;
        if (curRed == redTarget) {
            for (int d2 = 0; d2 < 4; d2++) {
                int nr2 = br + dr[d2];
                int nc2 = bc + dc[d2];
                int nextBlue = nr2 * m + nc2;
                if (nr2 < 0 || nr2 >= n || nc2 < 0 || nc2 >= m || maze[nr2][nc2] == 5 || visited[nr2][nc2][1] || nextBlue == curRed) continue;
                visited[nr2][nc2][1] = true;
                dfs(curRed, nextBlue, visited, turn + 1);
                visited[nr2][nc2][1] = false;
            }
            return;
        }
        for (int d = 0; d < 4; d++) {
            int nr = rr + dr[d];
            int nc = rc + dc[d];
            int nextRed = nr * m + nc;
            if (nr < 0 || nr >= n || nc < 0 || nc >= m || maze[nr][nc] == 5 || visited[nr][nc][0]) continue;
            visited[nr][nc][0] = true;
            
            if (curBlue == blueTarget && nextRed != curBlue) {
                dfs(nextRed, curBlue, visited, turn + 1);
            }
            for (int d2 = 0; d2 < 4 && curBlue != blueTarget; d2++) {
                int nr2 = br + dr[d2];
                int nc2 = bc + dc[d2];
                int nextBlue = nr2 * m + nc2;
                if (nr2 < 0 || nr2 >= n || nc2 < 0 || nc2 >= m || maze[nr2][nc2] == 5 || visited[nr2][nc2][1] || nextBlue == nextRed) continue;
                if (nextBlue == curRed && nextRed == curBlue) continue;
                visited[nr2][nc2][1] = true;
                dfs(nextRed, nextBlue, visited, turn + 1);
                visited[nr2][nc2][1] = false;
            }
            
            visited[nr][nc][0] = false;
        }
    }
}