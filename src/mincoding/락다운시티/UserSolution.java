package mincoding.락다운시티;

import java.util.*;

class UserSolution {

    static class Node {

        int r, c, dist;

        Node(int r, int c, int dist) {
            this.r = r;
            this.c = c;
            this.dist = dist;
        }
    }

    int N, M, visitedTime;
    int[] dr = new int[] {-1, 0, 1, 0} , dc = new int[]{0, 1, 0, -1};
    List<String> certifications;
    Set<String> set;
    String[][] grid;
    int[][] visited;
    ArrayDeque<Node> q;

    void init(int N, int M, String mGrade[][]) {
        this.N = N;
        this.M = M;
        this.grid = mGrade;
        visitedTime = 0;
        set = new HashSet<>();
        visited = new int[N][N];
        q = new ArrayDeque<>();

        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                set.add(mGrade[i][j]);
            }
        }
        certifications = new ArrayList<>(set);
        Collections.sort(certifications);
    }

    void change(int mRow, int mCol, int mDir, int mLength, String mGrade) {
        int curR = mRow;
        int curC = mCol;
        int d = mDir == 0 ? 2 : 1;
        for (int i = 0; i < mLength; i++) {
            grid[curR][curC] = mGrade;
            curR += dr[d];
            curC += dc[d];
        }
        if (!set.contains(mGrade)){
            set.add(mGrade);
//            certifications.add(mGrade);
            // 정렬된 위치에 바로 삽입 → O(log K) 탐색 + O(K) 삽입
            int pos = Collections.binarySearch(certifications, mGrade);
            if (pos < 0) certifications.add(-pos - 1, mGrade);
        }
    }

    String calculate(int L, int sRow, int sCol, int eRow, int eCol) {
        int left = 0;
        int right = certifications.size() - 1;
        String result = null;
        while (left <= right) {
            int mid = left + (right - left) / 2;
            String certification = certifications.get(mid);

            boolean canGo;
            if (grid[eRow][eCol].compareTo(certification) < 0) {
                canGo = false;
            } else {
                canGo = travel(certification, L, sRow, sCol, eRow, eCol);
            }

            if (canGo) {
                left = mid + 1;
                result = certifications.get(mid);
            } else {
                right = mid - 1;
            }
        }
        return result;
    }

    boolean travel(String certification, int L, int sr, int sc, int er, int ec) {
        visitedTime++;
        q.clear();

        q.offer(new Node(sr, sc, 0));
        visited[sr][sc] = visitedTime;

        while (!q.isEmpty()) {
            Node cur = q.poll();
            for (int d = 0; d < 4; d++) {
                int nr = cur.r + dr[d];
                int nc = cur.c + dc[d];
                if (nr < 0 || nr >= N || nc < 0 || nc >= N) continue;
                if (grid[nr][nc].compareTo(certification) < 0) continue;
                int nDist = cur.dist + 1;
                if (visited[nr][nc] == visitedTime) continue;
                if (nDist > L) continue;
                if (nr == er && nc == ec) return true;
                visited[nr][nc] = visitedTime;
                q.offer(new Node(nr, nc, nDist));
            }
        }
        return false;
    }
}