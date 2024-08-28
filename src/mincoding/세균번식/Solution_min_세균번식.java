package mincoding.세균번식;

import java.io.*;
import java.util.*;

/**
 * 번식 진행 후 비활성화
 * NxN의 크기의 접시
 * 각 셀마다 에너지 소비량이라는 정보가 있다.
 * 타겟 세균의 종류는 파란 세균과 빨간 세균으로 2개가 있다.
 * 번식 에너지: 약품이 지니고 있는 특수 에너지로 세균 생성에 사용된다.
 * 1. 생성약품 떨어뜨리기
 * 만약 셀이 비어있으면 타겟 세균과 동일한 종류의 세균을 생성하면서 바로 활성화시킨다.
 * - 이때 해당 셀의 에너지 소비량만큼 번식 에너지를 소비한다.
 * - 번식 에너지보다 소비량이 더 크면 번식 에너지의 남은 양은 0이 된다.
 * 만약 타겟 세균과 같은 종류의 세균에 떨어졌다면 해당 세균은 활성화된다.
 * 만약 다른 세균에 떨어졌다면 아무 일도 일어나지 않는다.
 * => 활성화된 세균들은 상하좌우에 연결된 같은 종류의 세균들을 모두 활성화시킨다. => 연쇄적으로 활성화된다.
 * 2. 번식 에너지를 이용한 번식
 * - 번식 에너지가 존재하는 경우, 활성화된 세균들의 번식 가능한 위치들을 모두 찾는다.
 * - 번식 가능한 위치들 중 우선순위가 가장 높은 위치에 타겟 세균과 동일한 종류의 세균이 생성되면서 바로 활성화된다.
 *  - 이때 해당 셀의 에너지 소비량만큼 번식 에너지를 소비한다.
 *  - 번식 에너지보다 소비량이 더 크면 번식 에너지의 남은 양은 0이 된다.
 * - 세균 생헝 후 활성화된 세균들은 상하좌우로 연결된 같은 종류의 세균들을 모두 활성화시킨다.
 * - 더이상 활성화시킬 세균들이 없을 때까지 이 과정은 계속된다.
 */
public class Solution_min_세균번식 {

    static class Position implements Comparable<Position> {
        int r, c, energyUseVolume;

        public Position(int r, int c, int energyUseVolume) {
            this.r = r;
            this.c = c;
            this.energyUseVolume = energyUseVolume;
        }

        @Override
        public int compareTo(Position o) {
            return energyUseVolume == o.energyUseVolume ? r == o.r ? Integer.compare(c, o.c) : Integer.compare(r, o.r) : Integer.compare(o.energyUseVolume, energyUseVolume);
        }

        @Override
        public String toString() {
            return "Position{" +
                    "r=" + r +
                    ", c=" + c +
                    ", energyUseVolume=" + energyUseVolume +
                    '}';
        }
    }

    private int N, v;
    private int[] cnt = new int[3], dr = {-1, 0, 1, 0}, dc = {0, 1, 0, -1};
    private int[][] dish, status, visited;
    private PriorityQueue<Position> pq = new PriorityQueue<>();
    private ArrayDeque<int[]> q = new ArrayDeque<>();

    /**
     * @param N 5 <= N <= 100
     * @param Dish 각 에너지 소비량: 1 <= 소비량 <= 5
     */
    public void init(int N, int[][] Dish) {
        this.N = N;
        visited = new int[N][N];
        status = new int[N][N];
        dish = new int[N][N];
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++)
                dish[i][j] = Dish[i][j];
        }
        v = cnt[1] = cnt[2] = 0;
    }

    /**
     * @param target 1 <= target <= 2 세균의 종류
     * @param energy 3 <= energy <= 200 번식 에너지의 양
     * @return 접시 내의 종류가 target인 세균의 총 개수
     */
    public int dropMedicine(int target, int R, int C, int energy) {
        R--;
        C--;

        if (status[R][C] == 0) {
            energy -= dish[R][C];
            cnt[target]++;
            status[R][C] = target;
            if (energy <= 0) return cnt[target];
        } else if (status[R][C] != target)
            return cnt[target];


        pq.clear();
        q.clear();
        v++;
        putQueue(R, C, target);

        while (!pq.isEmpty()) {
            Position cur = pq.poll();
            cnt[target]++;
            status[cur.r][cur.c] = target;
            energy -= cur.energyUseVolume;
            if (energy <= 0) break;
            putQueue(cur.r, cur.c, target);
        }
        return cnt[target];
    }

    private void putQueue(int r, int c, int target) {
        visited[r][c] = v;
        q.offer(new int[]{r, c});
        while (!q.isEmpty()) {
            int[] cur = q.poll();
            for (int d = 0; d < 4; d++) {
                int nr = cur[0] + dr[d];
                int nc = cur[1] + dc[d];
                if (nr < 0 || nr > N - 1 || nc < 0 || nc > N - 1 || visited[nr][nc] == v) continue;
                if (status[nr][nc] == 0) {
                    visited[nr][nc] = v;
                    pq.offer(new Position(nr, nc, dish[nr][nc]));
                } else if (status[nr][nc] == target) {
                    visited[nr][nc] = v;
                    q.offer(new int[]{nr, nc});
                }
            }
        }
    }

    public int cleanBacteria(int R, int C) {
        R--;
        C--;
        if (status[R][C] == 0) return -1;
        q.clear();
        int target = status[R][C];
        status[R][C] = 0;
        q.offer(new int[]{R, C});
        while (!q.isEmpty()) {
            int[] cur = q.poll();
            cnt[target]--;
            for (int d = 0; d < 4; d++) {
                int nr = cur[0] + dr[d];
                int nc = cur[1] + dc[d];
                if (nr < 0 || nr > N - 1 || nc < 0 || nc > N - 1 || status[nr][nc] != target) continue;
                status[nr][nc] = 0;
                q.offer(new int[] {nr, nc});
            }
        }
        return cnt[target];
    }
}
