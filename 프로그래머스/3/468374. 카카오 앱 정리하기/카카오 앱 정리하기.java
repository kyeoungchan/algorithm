import java.util.*;

class Solution {
    
    static class Cell {
        int id, r, c;
        
        Cell(int id, int r, int c) {
            this.id = id;
            this.r = r;
            this.c = c;
        }
    }
    
    int N, M;
    int[] dr = new int[] {0, 0, 1, 0, -1}, dc = new int[] {0, 1, 0, -1, 0};
    int[][] board;
    
    public int[][] solution(int[][] board, int[][] commands) {
        N = board.length;
        M = board[0].length;
        this.board= board;
        
        for (int[] command: commands) {
            move(command[0], command[1]);
        }
        
        return this.board;
    }
    
    void move(int id, int d) {
        Set<Integer> group = getGroup(id, d);
        moveGroup(group, d);
        
        while (true) {
            List<Integer> broken = findBrokenApps(d);
            if (broken.isEmpty()) break;
            
            int brokenId = broken.get(0);
            Set<Integer> newGroup = getGroup(brokenId, d);
            moveGroup(newGroup, d);
        }
    }
    
    Set<Integer> getGroup(int id, int d) {
        Set<Integer> group = new HashSet<>();
        ArrayDeque<Integer> q = new ArrayDeque<>();
        
        group.add(id);
        q.offer(id);
        
        while (!q.isEmpty()) {
            int cur = q.poll();
            
            for (int r = 0; r < N; r++) {
                for (int c = 0; c < M; c++) {
                    if (board[r][c] != cur) continue;
                    
                    int nr = (r + dr[d] + N) % N;
                    int nc = (c + dc[d] + M) % M;
                    
                    int next = board[nr][nc];
                    if (next != 0 && !group.contains(next)) {
                        group.add(next);
                        q.offer(next);
                    }
                }
            }
        }
        return group;
    }
    
    void moveGroup(Set<Integer> group, int d) {
        List<Cell> cells = new ArrayList<>();
        
        for (int r = 0; r < N; r++) {
            for (int c = 0; c < M; c++) {
                if (group.contains(board[r][c])) {
                    cells.add(new Cell(board[r][c], r, c));
                }
            }
        }
        
        // 원래 있던 앱 지우기
        for (Cell cell: cells) {
            board[cell.r][cell.c] = 0;
        }
        
        // d 방향으로 한 칸 이동
        for (Cell cell: cells) {
            int nr = (cell.r + dr[d] + N) % N;
            int nc = (cell.c + dc[d] + M) % M;
            board[nr][nc] = cell.id;
        }
    }
    
    List<Integer> findBrokenApps(int d) {
        List<Integer> broken = new ArrayList<>();
        boolean[] added = new boolean[101];
        
        if (d == 1 || d == 3) {
            // 좌우 이동
            for (int r = 0; r < N; r++) {
                int leftId = board[r][0];
                int rightId = board[r][M-1];
                
                if (leftId == 0 || leftId != rightId) continue;
                
                // board 자체를 가로지르는 어플이 있을 수 있으므로 확인
                boolean brokenInThisRow = false;
                for (int c = 0; c < M; c++) {
                    if (board[r][c] != leftId) {
                        brokenInThisRow = true;
                        break;
                    }
                }
                
                if (brokenInThisRow && !added[leftId]) {
                    broken.add(leftId);
                    added[leftId] = true;
                }
            }
        } else {
            // 상하 이동
            for (int c = 0; c < M; c++) {
                int topId = board[0][c];
                int bottomId = board[N - 1][c];
                
                if (topId == 0 || topId != bottomId) continue;
                
                boolean brokenInThisCol = false;
                for (int r = 0; r < N; r++) {
                    if (board[r][c] != topId) {
                        brokenInThisCol = true;
                        break;
                    }
                }
                
                if (brokenInThisCol && !added[topId]) {
                    broken.add(topId);
                    added[topId] = true;
                }
            }
        }
        
        return broken;
    }
}