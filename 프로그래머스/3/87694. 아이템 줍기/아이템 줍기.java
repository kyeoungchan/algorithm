import java.util.*;

class Solution {
    
    static class Point {
        int x, y, dist;
        
        Point(int x, int y, int dist) {
            this.x = x;
            this.y = y;
            this.dist = dist;
        }
    }
    
    public int solution(int[][] rectangle, int characterX, int characterY, int itemX, int itemY) {
        int[] dr = new int[] {0, 1, 0, -1};
        int[] dc = new int[] {1, 0, -1, 0};
        int[][] board = new int[102][102];
        for (int[] r: rectangle) {
            int x1 = r[0] * 2;
            int y1 = r[1] * 2;
            int x2 = r[2] * 2;
            int y2 = r[3] * 2;
            int horizontal = x2 - x1 + 1;
            int vertical = y2 - y1 + 1;
            for (int i = 0; i < horizontal; i++) {
                board[x1 + i][y1] = 1;
                board[x1 + i][y2] = 1;
            }
            for (int i = 0; i < vertical; i++) {
                board[x1][y1 + i] = 1;
                board[x2][y1 + i] = 1;
            }
        }
        
        for (int[] r : rectangle) {
            int x1 = r[0] * 2;
            int y1 = r[1] * 2;
            int x2 = r[2] * 2;
            int y2 = r[3] * 2;
            for (int x = x1 + 1; x < x2; x++) {
                for (int y = y1 + 1; y < y2; y++) {
                    board[x][y] = 2;
                }
            }
        }
        
        int answer = 401;
        Deque<Point> q = new ArrayDeque<>();
        q.offer(new Point(characterX * 2, characterY * 2, 0));
        board[characterX * 2][characterY * 2] = 0;
        int targetX = itemX * 2;
        int targetY = itemY * 2;
        end: while (!q.isEmpty()) {
            Point cur = q.poll();
            for (int d = 0; d < 4; d++) {
                int nx = cur.x + dr[d];
                int ny = cur.y + dc[d];
                int nDist = cur.dist + 1;
                if (nx == targetX && ny == targetY) {
                    answer = nDist / 2;
                    break end;
                }
                if (board[nx][ny] != 1) continue;
                board[nx][ny] = 0;
                q.offer(new Point(nx, ny, nDist));
            }
        }
        return answer;
    }
}