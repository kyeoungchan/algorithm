package baekjoon.마법사상어와복제;

import java.util.*;
import java.io.*;

/**
 * 상어가 쓸 수 있는 마법: 파이어볼, 토네이도, 파이어스톰, 물복사버그, 비바라기, 블리자드
 * 오늘은 물복사버그를 4x4 격자에서 하려고 한다.
 * 칸은 1,1 ~ 4,4으로 돼있다.
 * 격자에는 M마리의 물고기가 있다.
 *  - 각 물고기는 격자의 칸 하나에 들어가 있다.
 *  - 이동방향은 8방향
 *  - 둘 이상의 물고기가 같은 칸에 있을 수 있다.
 * 마법사 상어도 격자에 들어가있다.
 *  - 물고기와 같은 칸에 있을 수 있다.
 * 작업 순서
 * 1. 모든 물고기에게 복제 마법 시전
 * 2. 모든 물고기가 한 칸 이동
 *  - 상어가 있는 칸, 물고기 냄새가 있는 칸, 격자 범위 밖으로는 이동할 수 없다.
 *  - 각 물고기는 자신이 가지고 있는 이동방향이 이동할 수 있는 칸을 향할 때까지 방향을 45도 반시계 회전시킨다.
 * 3. 상어는 연속해서 3칸 이동한다.
 *  - 상어는 4방향으로 인접한 칸으로 이동한다.
 *  - 연속해서 이동하는 칸 중에 격자의 범위를 벗어나는 칸이 있으면 그 방법은 아예 갈 수 없는 방법이다.
 *  - 이동하는 중에 물고기가 있는 칸으로 이동하게 된다면, 그 칸에 있는 물고기는 격자에서 제외된다.
 *      - 그리고 물고기 냄새를 남긴다.
 *  - 가능한 이동 방법 중에서 제외되는 물고기의 수가 가장 많은 방법으로 이동한다.
 *  - 제외되는 물고기 수가 같으면 사전 순
 *      - 사전 순이란, 상좌하우 순으로 숫자매기듯이 하면 된다.
 * 4. 두 번 전 연습에서 생긴 물고기의 냄새가 격자에서 사라진다.
 * 5. 1에서 복제된 물고기는 1에서의 위치와 방향을 그대로 갖게 된다.
 * 연습횟수: S
 * 격자에 있는 물고기의 수를 구하라.
 *
 * 해결 프로세스: 리스트를 사용하기에는 물고기가 너무 많을 수 있으니 격자의 범위가 적은 이점을 살려서 이차원배열을 사용하도록 한다.
 * 물고기 객체를 사용하자.
 *  - 물고기객체에는 식별자 id를 부여한다.
 *      - 잡아먹힌 물고기는 멤버 변수 boolean dead를 통해서 죽었음을 표시한다.
 *  - 배열에는 리스트를 넣어서, 한 칸에 존재하는 물고기 객체의 id를 담는다.
 *  - 상어의 좌표는 지역 변수를 사용한다.
 *  - 상어가 잡아먹으면 남겨질 냄새는 냄새관련 이차원 배열로 표시해두자.
 *      - -2로 표시하고, 매 턴이 지날 때마다 ++을 해주자.
 *      - 0이 되면 냄새는 사라진다.
 *      - 그리고 물고기가 이동을 마치고나서 냄새를 처리해야한다.
 *  - 상어의 최상 루트는...
 *      - 상어의 위치에서 BFS로 돌려가며 최고로 물고기를 많이 잡아먹은 경로를 설정하자.
 *      - BFS를 사용하기 위한 큐에 담을 정보는 상어의 좌표, 현재 잡아먹은 물고기 수, d1, d2, d3
 *      - visited 처리해야한다. 그래야 한 번 잡아먹은 물고기를 또 잡아먹는 일이 발생하지 않는다.
 *      - visited 처리는 어차피 최대 3번 움직이므로 그전에 이동한 것과 반대방향인 경우만 생각한다.
 */
public class Solution_bj_23290_마법사상어와복제 {

    static class Fish {
        int id, i, j, d;
        boolean dead;

        Fish(int id, int i, int j, int d) {
            this.id = id;
            this.i = i;
            this.j = j;
            this.d = d;
            dead = false;
        }

        void move() {
            if (dead) return; // 이미 잡아먹힌 물고기면 이동하지 않는다.
            boolean canMove = false;

            int ni = i;
            int nj = j;
            for (int k = 0; k < 8; k++) {
                int nd = d - k;
                if (nd < 1) nd += 8;
                ni = i + dfi[nd];
                nj = j + dfj[nd];
                if (ni < 1 || ni > 4 || nj < 1 || nj > 4 || (ni == si && nj == sj) || smellMap[ni][nj] != 0) continue;
                d = nd;
                canMove = true;
                break;
            }

            if (canMove) {
                // 움직일 수 있어야 원래 좌표에서 본인을 지운다.
                for (int idx = 0; idx < map[i][j].size(); idx++) { // 원래 좌표에 id 삭제
                    if (map[i][j].get(idx) == id) {
                        map[i][j].remove(idx);
                        break;
                    }
                }
                // 그리고 새로운 좌표에 본인을 찍는다.
                setPos(ni, nj);
            }
        }

        void setPos(int i, int j) {
            this.i = i;
            this.j = j;
            setMap();
        }

        void setMap() {
            if (map[i][j] == null) map[i][j] = new ArrayList<>();
            map[i][j].add(id);
        }

        void eaten() {
            dead = true;
        }

    }

    static List<Integer>[][] map;
    static int[][] smellMap;
    static int[] dsi = {-1, 0, 1, 0}, dsj = {0, -1, 0, 1}, dfi = {0, 0, -1, -1, -1, 0, 1, 1, 1}, dfj = {0, -1, -1, 0, 1, 1, 1, 0, -1};
    static int si, sj;

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        int M = Integer.parseInt(st.nextToken()); // 물고기 수
        int S = Integer.parseInt(st.nextToken()); // 상어가 마법을 연습한 횟수
        map = new List[5][5];
        smellMap = new int[5][5];
        List<Fish> fishList = new ArrayList<>();
        List<Fish> copyList = new ArrayList<>();
        int answer = M;
        int totalId = 0;
        for (int i = 0; i < M; i++) {
            st = new StringTokenizer(br.readLine(), " ");
            int fi = Integer.parseInt(st.nextToken());
            int fj = Integer.parseInt(st.nextToken());
            int fd = Integer.parseInt(st.nextToken());
            Fish newFish = new Fish(totalId++, fi, fj, fd);
            fishList.add(newFish);
            newFish.setMap();
        }
        st = new StringTokenizer(br.readLine(), " ");
        si = Integer.parseInt(st.nextToken());
        sj = Integer.parseInt(st.nextToken());

        ArrayDeque<int[]> q = new ArrayDeque<>();

        for (int time = 0; time < S; time++) {

            for (Fish curF : fishList) {
                // 현재 살아있는 물고기를 모두 복제해둔다.
                if (curF.dead) continue;
                copyList.add(new Fish(totalId++, curF.i, curF.j, curF.d));
            }
            for (Fish f : fishList) {
                f.move();
            }

            for (int i = 1; i < 5; i++) {
                for (int j = 1; j < 5; j++) {
                    if (smellMap[i][j] != 0)
                        smellMap[i][j]++;
                }
            }


            int[][] movingTrace = new int[3][2]; // 각 순서마다 이동한 좌표들
            q.offer(new int[]{si, sj, 0, 0, 0, -1, -1}); // 좌표, 잡아먹은 물고기 수, 이동했던 방향들, 처음 좌표
            // 이동했던 방향은 첫,두번째 방향만 찍는다. 마지막 방향은 찍을 필요 없이 끝나므로 찍을 필요 없다.
            // 마지막 -1 2개는 첫 번째로 움직였을 때 찍은 좌표다. 두 번째는 세 번째 움직일 때 조회하므로 첫 번째만 기억하면 된다.
            int maxEat = 0;
            for (int i = 0; i < 3; i++) {
                int size = q.size();
                for (int j = 0; j < size; j++) {
                    int[] cur = q.poll();
                    for (int d = 0; d < 4; d++) {
                        int ni = cur[0] + dsi[d];
                        int nj = cur[1] + dsj[d];
                        if (ni < 1 || ni > 4 || nj < 1 || nj > 4) continue;
                        int dirIdx = 3 + i;
                        int nCnt = cur[2];
                        if (i == 0 || cur[dirIdx - 1] != (d + 2) % 4) {
                            // 처음 움직인 경우나 이전에 이동했던 방향이 지금과 반대방향이 아닌 경우
                            if (map[ni][nj] != null) {
                                nCnt += map[ni][nj].size();
                            }
                        }
                        if (i == 0) {
                            q.offer(new int[]{ni, nj, nCnt, d, 0, ni, nj});
                        } else if (i == 1) {
                            q.offer(new int[]{ni, nj, nCnt, cur[3], d, cur[5], cur[6]});
                        } else {
                            if (nCnt > maxEat || movingTrace[0][0] == 0) {
                                // 있을 수 없는 좌표, 즉 상어가 먹을 물고기가 없다고 생각하고 한 번도 이동을 안 했을 때
                                maxEat = nCnt;
                                movingTrace[0][0] = cur[5];
                                movingTrace[0][1] = cur[6];
                                movingTrace[1][0] = cur[0];
                                movingTrace[1][1] = cur[1];
                                movingTrace[2][0] = ni;
                                movingTrace[2][1] = nj;
                            }
                        }
                    }
                }
            }

            si = movingTrace[2][0];
            sj = movingTrace[2][1];
            for (int i = 0; i < 3; i++) {
                // 상어가 지나간 곳에 흔적을 남기는 곳들
                int mi = movingTrace[i][0];
                int mj = movingTrace[i][1];
                if (map[mi][mj] == null || map[mi][mj].isEmpty()) continue;
                answer -= map[mi][mj].size();
                for (int fId : map[mi][mj]) {
                    Fish f = fishList.get(fId);
                    f.eaten();
                }
                map[mi][mj].clear();
                smellMap[mi][mj] = -2;
            }


            for (Fish copied : copyList) {
                // 복제해둔 물고기들을 다 풀어준다.
                copied.setMap();
                fishList.add(copied);
                answer++;
            }
            copyList.clear();
        }
        System.out.println(answer);
        br.close();
    }
}
