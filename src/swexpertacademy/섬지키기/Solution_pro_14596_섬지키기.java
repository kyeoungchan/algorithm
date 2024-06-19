package swexpertacademy.섬지키기;

import java.util.*;

/**
 * 격자 모양의 섬이 있다.
 * 섬의 크기는 NxN, 바다로 둘러쌓여있다.
 * 섬의 숫자는 각 지역의 고도를 나타낸다.
 * 바닷물은 mSeaLevel-1 이하인 지역을 침투할 수 있다.
 * 하지만 대각선으로는 침투할 수 없다.
 */
public class Solution_pro_14596_섬지키기 {

    int mapSize, hashSize = 9999;
    int[] dr = {-1, 0, 1, 0}, dc = {0, 1, 0, -1};
    int[][] map;
    List<Structure>[] structures;

    static class Structure {
        int r, c, d; // m개의 숫자 조합 중에서 가장 작은 애를 기준으로 위치와 방향.

        public Structure(int r, int c, int d) {
            this.r = r;
            this.c = c;
            this.d = d;
        }

        @Override
        public String toString() {
            return "Structure{" +
                    "r=" + r +
                    ", c=" + c +
                    ", d=" + d +
                    '}';
        }
    }

    public void init(int N, int mMap[][]) {
        mapSize = N;
        map = mMap;
        structures = new List[hashSize + 1];
        for (int i = 0; i < hashSize + 1; i++)
            structures[i] = new ArrayList<>();

        for (int len = 2; len < 6; len++) {
            // 가로로 놓고자 할 때
            for (int r = 0; r < N; r++) {
                for (int c = 0; c < N - len + 1; c++) {
                    int hash = 0;
                    // 가야하는 위치: c~c+len-1
                    for (int k = 0; k < len - 1; k++)
                        hash = hash * 10 + (mMap[r][c + k + 1] - mMap[r][c + k] + 5);
                    // 오른쪽으로 가는 방향으로 구조물 세우기 가능
                    structures[hash].add(new Structure(r, c, 1));

                    int reverseHash = 0;
                    for (int k = 0; k < len - 1; k++)
                        reverseHash = reverseHash * 10 + (mMap[r][c + len - 2 - k] - mMap[r][c + len - 1 - k] + 5);
                    if (hash == reverseHash) continue;
                    // 왼쪽으로 가는 방향으로 구조물 세우기 가능
                    structures[reverseHash].add(new Structure(r, c + len - 1, 3));
                }
            }

            // 세로로 놓고자 할 때
            for (int r = 0; r < N - len + 1; r++) {
                for (int c = 0; c < N; c++) {
                    int hash = 0;
                    for (int k = 0; k < len - 1; k++)
                        hash = hash * 10 + (mMap[r + k + 1][c] - mMap[r + k][c] + 5);
                    // 아래쪽으로 가는 방향으로 구조물 세우기 가능
                    structures[hash].add(new Structure(r, c, 2));

                    int reverseHash = 0;
                    for (int k = 0; k < len - 1; k++)
                        reverseHash = reverseHash * 10 + (mMap[r + len - 2 - k][c] - mMap[r + len - 1 - k][c] + 5);
                    if (hash == reverseHash) continue;

                    // 위쪽으로 가는 방향으로 구조물 세우기 가능
                    structures[reverseHash].add(new Structure(r + len - 1, c, 0));
                }
            }
        }
    }

    public int numberOfCandidate(int M, int mStructure[]) {
        if (M == 1) return mapSize * mapSize;
        int hash = 0;
        for (int i = 0; i < M- 1; i++)
            hash = 10 * hash + (mStructure[i] - mStructure[i + 1] + 5);
        return structures[hash].size();
    }

    public int maxArea(int M, int mStructure[], int mSeaLevel) {
        int maxLandArea = -1;
        if (M == 1) {
            for (int i = 0; i < mapSize; i++) {
                for (int j = 0; j < mapSize; j++) {
                    map[i][j] += mStructure[0];
                    maxLandArea = Math.max(maxLandArea, simulateFlood(mSeaLevel));
                    map[i][j] -= mStructure[0];
                }
            }
        } else {
            int hash = 0;
            for (int i = 0; i < M - 1; i++)
                hash = 10 * hash + (mStructure[i] - mStructure[i + 1] + 5);
            for (Structure structure : structures[hash]) {
                int startR = structure.r;
                int startC = structure.c;
                int d = structure.d;
                for (int i = 0; i < M; i++)
                    map[startR + dr[d] * i][startC + dc[d] * i] += mStructure[i];
                maxLandArea = Math.max(maxLandArea, simulateFlood(mSeaLevel));
                for (int i = 0; i < M; i++)
                    map[startR + dr[d] * i][startC + dc[d] * i] -= mStructure[i];
            }
        }
        return maxLandArea;
    }

    private int simulateFlood(int seaLevel) {
        int restArea = mapSize * mapSize;
        boolean[][] v = new boolean[mapSize][mapSize];
        ArrayDeque<int[]> q = new ArrayDeque<>();
        int r = mapSize - 1, c = 0;
        for (int d = 0; d < 4; d++) {
            while (true) {
                r += dr[d];
                c += dc[d];
                if (r < 0 || r > mapSize - 1 || c < 0 || c > mapSize - 1) {
                    r -= dr[d];
                    c -= dc[d];
                    break;
                }
                v[r][c] = true;
                if (map[r][c] < seaLevel) {
                    restArea--;
                    q.offer(new int[]{r, c});
                }
            }
        }

        while (!q.isEmpty()) {
            int[] cur = q.poll();
            for (int d = 0; d < 4; d++) {
                int nr = cur[0] + dr[d];
                int nc = cur[1] + dc[d];
                if (nr < 0 || nr > mapSize - 1 || nc < 0 || nc > mapSize - 1 || v[nr][nc]) continue;
                v[nr][nc] = true;
                if (map[nr][nc] < seaLevel) {
                    restArea--;
                    q.offer(new int[]{nr, nc});
                }
            }
        }
        return restArea;
    }

    private void printMap() {
        for (int i = 0; i < mapSize; i++) {
            for (int j = 0; j < mapSize; j++) {
                System.out.print(map[i][j] + " ");
            }
            System.out.println();
        }
        System.out.println();
    }
}
