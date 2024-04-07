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
 *  - 배열에는 리스트를 넣어서, 한 칸에 존재하는 물고기 객체의 방향정보(d)를 담는다.
 *      - 복제리스트는 매번 배열을 탐색하며 물고기 객체로 담아준다.
 *      - 그리고 복제할 때마다 배열을 초기화시켜준다.
 *      - 이동은 복제해놓은 리스트에서 꺼내서 쓴다.
 *          - 여기에서 주의할 점은 객체는 건들지 말고 지도에만 표시해야한다는 점이다!
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
        int i, j, d;

        Fish(int i, int j, int d) {
            this.i = i;
            this.j = j;
            this.d = d;
        }

        void move() {
            boolean canMove = false;
            for (int k = 0; k < 8; k++) {
                int nd = d - k;
                if (nd < 1) nd += 8;

                int ni = i + dfi[nd];
                int nj = j + dfj[nd];

                if (ni < 1 || ni > 4 || nj < 1 || nj > 4 || (ni == si && nj == sj) || smellMap[ni][nj] != 0) continue;
                canMove = true;
                setMap(ni, nj, nd);
                break;
            }

            if (!canMove) {
                // 움직이는 데 실패했다면 제자리에 찍는다.
                setMap();
            }
        }

        void setMap() {
            map[i][j].add(d);
        }

        void setMap(int ni, int nj, int nd) {
            map[ni][nj].add(nd);
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
        for (int i = 1; i < 5; i++) {
            for (int j = 1; j < 5; j++) {
                map[i][j] = new ArrayList<>();
            }
        }
        smellMap = new int[5][5];
        List<Fish> copyList = new ArrayList<>();
        int answer = M;
        for (int i = 0; i < M; i++) {
            st = new StringTokenizer(br.readLine(), " ");
            int fi = Integer.parseInt(st.nextToken());
            int fj = Integer.parseInt(st.nextToken());
            int fd = Integer.parseInt(st.nextToken());
            map[fi][fj].add(fd);
        }
        st = new StringTokenizer(br.readLine(), " ");
        si = Integer.parseInt(st.nextToken());
        sj = Integer.parseInt(st.nextToken());


        for (int time = 0; time < S; time++) {
            copyFish(copyList);
            moveFish(copyList);
            updateSmell();
            int[][] movingTrace = moveShark();
            answer = afterSharkMove(answer, movingTrace);
            answer = pasteCopiedFish(copyList, answer);
        }
        System.out.println(answer);
        br.close();
    }

    static void copyFish(List<Fish> copyList) {
        for (int i = 1; i < 5; i++) {
            for (int j = 1; j < 5; j++) {
                if (map[i][j].isEmpty()) continue;
                for (int d : map[i][j]) {
                    copyList.add(new Fish(i, j, d));
                }
                map[i][j].clear(); // 복제했으니 지도에서는 물고기들을 모두 삭제한다.
            }
        }
    }

    static void moveFish(List<Fish> copyList) {
        for (Fish f : copyList) {
            f.move();
        }
    }

    static void updateSmell() {
        for (int i = 1; i < 5; i++) {
            for (int j = 1; j < 5; j++) {
                if (smellMap[i][j] != 0)
                    smellMap[i][j]++;
            }
        }
    }

    static int[][] moveShark() {
        ArrayDeque<int[]> q = new ArrayDeque<>();
        int[][] movingTrace = new int[3][2]; // 각 순서마다 이동한 좌표들
        q.offer(new int[]{si, sj, 0, 0, -1, -1}); // 좌표, 잡아먹은 물고기 수, 두 번째로 이동했던 방향, 처음 움직인 좌표
        // 이동했던 방향은 두번째 방향만 찍는다. 두 번째로 움직였을 때는 첫 번째로 다시 돌아가더라도 물고기를 먹을 수 있기 때문에 세 번째로 움직였을 때 두 번째로 움직인 장소로 다시 가는 경우만 체크하면 된다.
        // 마지막 -1 2개는 첫 번째로 움직였을 때 찍은 좌표다. 두 번째는 세 번째 움직일 때 조회하므로 첫 번째만 기억하면 된다.
        int maxEat = -1;
        for (int i = 0; i < 3; i++) {
            int size = q.size();
            for (int j = 0; j < size; j++) {
                int[] cur = q.poll();
                for (int d = 0; d < 4; d++) {
                    int ni = cur[0] + dsi[d];
                    int nj = cur[1] + dsj[d];
                    if (ni < 1 || ni > 4 || nj < 1 || nj > 4) continue;
                    int nCnt = cur[2];
                    if (i == 0 || !(i == 2 && cur[3] == (d + 2) % 4)) {
                        // 처음 움직인 경우나 이전에 이동했던 방향이 지금과 반대방향이 아닌 경우
                        // 여기에서 간과했던 점! 상어는 처음 본인이 있던 물고기는 먹지 않는다.
                        // 고로 상하우 => 3 지점을 다 먹는다./ 상상하 => 2지점만 먹는다.
                        // 즉 무조건 이전과 방향이 다르다고 안 먹는 게 아니라 i가 2일 때만 이전과 방향이 다르면 안 먹게 된다.
                        nCnt += map[ni][nj].size();
                    }
                    if (i == 0) {
                        q.offer(new int[]{ni, nj, nCnt, 0, ni, nj});
                    } else if (i == 1) {
                        q.offer(new int[]{ni, nj, nCnt, d, cur[4], cur[5]});
                    } else {
                        if (nCnt > maxEat) {
                            maxEat = nCnt;
                            movingTrace[0][0] = cur[4];
                            movingTrace[0][1] = cur[5];
                            movingTrace[1][0] = cur[0];
                            movingTrace[1][1] = cur[1];
                            movingTrace[2][0] = ni;
                            movingTrace[2][1] = nj;
                        }
                    }
                }
            }
        }
        return movingTrace;
    }

    static int afterSharkMove(int answer, int[][] movingTrace) {
        si = movingTrace[2][0];
        sj = movingTrace[2][1];
        for (int i = 0; i < 3; i++) {
            // 상어가 지나간 곳에 흔적을 남기는 곳들
            int mi = movingTrace[i][0];
            int mj = movingTrace[i][1];
            if (map[mi][mj] == null || map[mi][mj].isEmpty()) continue;
            answer -= map[mi][mj].size();
            map[mi][mj].clear();
            smellMap[mi][mj] = -2;
        }
        return answer;
    }

    static int pasteCopiedFish(List<Fish> copyList, int answer) {
        for (Fish copied : copyList) {
            // 복제해둔 물고기들을 다 풀어준다.
            copied.setMap();
            answer++;
        }
        copyList.clear();
        return answer;
    }
}
