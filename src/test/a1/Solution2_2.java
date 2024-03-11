package test.a1;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;


public class Solution2_2 {

    static int N, ANS, max, di[] = {-1, 0, 1, 0}, dj[] = {0, 1, 0, -1};
    static int[][] houseInfo, distMap;
    static List<int[]> maxInfoPos;
    static List<Integer> idsMap[][];

    /**
     * 좌표는 -15~15로 주어진다.
     * 각 집마다 위치 좌표와 전기차 충전기 거리 정보가 주어진다.
     * 충전기를 최대 2개 세웠을 때 각 충전기로부터 집까지의 거리들의 합 중 최솟값을 구하라.
     * 1개만 세울 수 있다면 1개를 세웠을 때의 거리 값 합을 구한다.
     * 2개를 다 세워도 모든 집들의 충전거리 범위를 충족시킬 수 없다면 -1을 출력하라.
     * <p>
     * 이차원 배열을 통해 각 집마다의 범위 내의 거리들을 모두 더해준다.
     * 그리고 이차원 배열 안의 리스트를 통해 각 위치마다 어떤 집들이 도달되는지도 제공한다.
     * 그리고 최대 겹치는 집들의 개수와 그 위치들을 리스트로 받는다.
     * 만약 최대 겹치는 집들의 개수가 N개와 같다면, 그 위치의 거리 합들의 정보가 정답이다.
     * 만약 같지 않다면, 그 위치에 세워두고, 다른 곳들에 충전기를 세워둔다.
     * 그때 이차원 boolean 배열을 통해 거리합 이차원 배열 정보를 사용하되, 겹친다면 그때 두 충전기 중에서 거리가 더 긴 것을 빼서 계산한다.
     */
    public static void main(String[] args) throws Exception {

        System.setIn(new FileInputStream("res/input2.txt"));
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;
        StringBuilder sb = new StringBuilder();

        int T = Integer.parseInt(br.readLine());
        for (int tc = 1; tc < T + 1; tc++) {
            N = Integer.parseInt(br.readLine());
            houseInfo = new int[N + 1][3];
            max = 0;
            maxInfoPos = new ArrayList<>();
            distMap = new int[31][31];
            idsMap = new List[31][31];
            boolean[][] v;
            for (int id = 1; id < N + 1; id++) {
                st = new StringTokenizer(br.readLine(), " ");
                houseInfo[id][0] = Integer.parseInt(st.nextToken()) + 15;
                houseInfo[id][1] = Integer.parseInt(st.nextToken()) + 15;
                houseInfo[id][2] = Integer.parseInt(st.nextToken());
                int hi = houseInfo[id][0];
                int hj = houseInfo[id][1];
                int rangeLimit = houseInfo[id][2];

                distMap[hi][hj] = -1; // -1을 충전소를 세울 수 없는 곳이다. 집에다가 충전소를 세운다면 아무리 코딩테스트여도 집주인이 소송할 것이다.
                ArrayDeque<int[]> q = new ArrayDeque<>();
                v = new boolean[31][31];
                v[hi][hj] = true;
                q.offer(new int[]{hi, hj, 0});
                while (!q.isEmpty()) {
                    int[] cur = q.poll();
                    int ci = cur[0];
                    int cj = cur[1];
                    int dist = cur[2];
                    if (distMap[ci][cj] != -1) {
                        distMap[ci][cj] += dist;
                        if (idsMap[ci][cj] == null) {
                            idsMap[ci][cj] = new ArrayList<>();
                        }
                        idsMap[ci][cj].add(id);
                        if (max < idsMap[ci][cj].size()) {
                            max = idsMap[ci][cj].size();
                            maxInfoPos.clear();
                            maxInfoPos.add(new int[]{ci, cj});
                        } else if (max == idsMap[ci][cj].size()) {
                            maxInfoPos.add(new int[]{ci, cj});
                        }
                    }
                    if (dist < rangeLimit) {
                        for (int d = 0; d < 4; d++) {
                            int ni = ci + di[d];
                            int nj = cj + dj[d];
                            if (ni < 0 || ni > 30 || nj < 0 || nj > 30 || v[ni][nj]) continue;
                            v[ni][nj] = true;
                            q.offer(new int[]{ni, nj, dist + 1});
                        }
                    }
                } // q while
            } // 집 정보 입력 for문
            ANS = Integer.MAX_VALUE;
            if (max == N) {
                for (int[] chargePos : maxInfoPos) {
                    ANS = Math.min(distMap[chargePos[0]][chargePos[1]], ANS);
                }
            } else {
                boolean[][] charged = new boolean[N + 1][2];
                for (int[] firstChargerPos : maxInfoPos) {

                    int firstChargerI = firstChargerPos[0];
                    int firstChargerJ = firstChargerPos[1];

                    for (int id : idsMap[firstChargerI][firstChargerJ]) {
                        charged[id][0] = true;
                    }
                    int searchId = 0;
                    for (int id = 1; id < N + 1; id++) {
                        if (charged[id][0]) continue;
                        searchId = id;
                        break;
                    }
                    int firstHi = houseInfo[searchId][0];
                    int firstHj = houseInfo[searchId][1];
                    int rangeLimit = houseInfo[searchId][2];

                    ArrayDeque<int[]> q = new ArrayDeque<>();
                    v = new boolean[31][31];
                    v[firstHi][firstHj] = true;
                    q.offer(new int[]{firstHi, firstHj, 0});
                    while (!q.isEmpty()) {
                        int firstDist = distMap[firstChargerI][firstChargerJ];
                        int chargedHouseCnt = max;

                        int[] cur = q.poll();
                        int curI = cur[0];
                        int curJ = cur[1];
                        int dist = cur[2];
                        if (distMap[curI][curJ] != -1 && idsMap[curI][curJ] == null) continue;
                        if (idsMap[curI][curJ] != null) {
                            if (idsMap[curI][curJ].size() < (N - max))
                                continue; // 만약 탐색하려는 위치가 이미 충전 가능한 집의 개수가 필요 충전 집의 개수보다 작다면 탐색할 필요가 없다.(가지치기)
                            for (int id : idsMap[curI][curJ]) {
                                if (!charged[id][0]) { // 이미 충전이 안 된 집이라면 충전된 집의 개수를 더해준다.
                                    chargedHouseCnt++;
                                    charged[id][1] = true;
                                } else {
                                    // 이미 충전이 된 집이라면 충전된 집의 개수를 더해주지는 않고, 미리 거리의 합에서 더 거리가 먼 것을 빼준다.
                                    int hi = houseInfo[id][0];
                                    int hj = houseInfo[id][1];
                                    firstDist -= Math.max(getDistance(curI, curJ, hi, hj), getDistance(firstChargerI, firstChargerJ, hi, hj));
                                }
                            }
                            if (chargedHouseCnt == N)
                                ANS = Math.min(ANS, firstDist + distMap[curI][curJ]);
                        }

                        if (dist < rangeLimit) {
                            for (int d = 0; d < 4; d++) {
                                int ni = curI + di[d];
                                int nj = curJ + dj[d];
                                if (ni < 0 || ni > 30 || nj < 0 || nj > 30 || v[ni][nj]) continue;
                                v[ni][nj] = true;
                                q.offer(new int[]{ni, nj, dist + 1});
                            }
                        } // 새로운 좌표 탐색 큐에 넣기
                    } // while(!q.isEmtpy())
                } // for (int[] firstChargerPos : maxInfoPos)
            }

            if (ANS == Integer.MAX_VALUE) ANS = -1;
            sb.append("#").append(tc).append(" ").append(ANS).append("\n");
        }
        System.out.println(sb.toString());
        br.close();
    }

    static int getDistance(int fromI, int fromJ, int toI, int toJ) {
        return Math.abs(fromI - toI) + Math.abs(fromJ - toJ);
    }
}
