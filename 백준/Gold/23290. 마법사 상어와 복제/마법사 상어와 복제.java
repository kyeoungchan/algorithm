import java.util.*;
import java.io.*;

public class Main {

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
        q.offer(new int[]{si, sj, 0, 0, 0, -1, -1}); // 좌표, 잡아먹은 물고기 수, 이동했던 방향들, 처음 움직인 좌표
        // 이동했던 방향은 첫,두번째 방향만 찍는다. 마지막 방향은 찍을 필요 없이 끝나므로 찍을 필요 없다.
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
                    int dirIdx = 3 + i;
                    int nCnt = cur[2];
                    if (i == 0 || !(i == 2 && cur[dirIdx - 1] == (d + 2) % 4)) {
                        // 처음 움직인 경우나 이전에 이동했던 방향이 지금과 반대방향이 아닌 경우
                        // 여기에서 간과했던 점! 상어는 처음 본인이 있던 물고기는 먹지 않는다.
                        // 고로 상하우 => 3 지점을 다 먹는다./ 상상하 => 2지점만 먹는다.
                        // 즉 무조건 이전과 방향이 다르다고 안 먹는 게 아니라 i가 2일 때만 이전과 방향이 다르면 안 먹게 된다.
                        nCnt += map[ni][nj].size();
                    }
                    if (i == 0) {
                        q.offer(new int[]{ni, nj, nCnt, d, 0, ni, nj});
                    } else if (i == 1) {
                        q.offer(new int[]{ni, nj, nCnt, cur[3], d, cur[5], cur[6]});
                    } else {
                        if (nCnt > maxEat) {
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