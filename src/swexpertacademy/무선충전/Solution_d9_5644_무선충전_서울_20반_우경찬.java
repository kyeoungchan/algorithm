package swexpertacademy.무선충전;

import java.util.*;
import java.io.*;

public class Solution_d9_5644_무선충전_서울_20반_우경찬 {
    static final int MAP_SIZE = 10, I_IDX = 0, J_IDX = 1, C_IDX = 2, P_IDX = 3;
    static int[] di = {0, -1, 0, 1, 0};
    static int[] dj = {0, 0, 1, 0, -1};
    static int[] aMove, bMove, aInfo = new int[3], bInfo = new int[3];
    static int[][][] map = new int[MAP_SIZE + 1][MAP_SIZE + 1][]; // 하나의 좌표에서 몇 개의 BC가 들어올지는 모른다.
    static int[][] bcInfo;
    static int M, A, totalCharge;

    /**
     * bcInfo를 이차원 배열을 통해서 각 BC마다의 성능을 바로 조회가 가능하게 한다.
     * map을 삼차원 배열을 통해서 하나의 좌표에 여러 개의 BC 번호를 넣을 수 있게 한다. BC의 번호는 1부터 시작한다.
     * 두 사람이 모두 겹치는 경우, 무조건 둘로 나눈다.
     * 만약 한 사람만 겹치는 경우, 같은 BC를 공유하고 있다면 겹치는 사람이 다른 BC를 사용한다.
     * 한 사람만 겹치지지만 다른 BC를 공유하고 있다면 겹치는 사람은 무조건 큰 거를 사용한다.
     * <p>
     * 사람에 대한 정보를 aInfo, bInfo로 나타내고, 각 시간마다 움직인 좌표와 사용하고 있는 충전량을 표시한다.
     * 총 충전량은 totalCharge를 활용한다.
     */
    public static void main(String[] args) throws Exception {
        System.setIn(new FileInputStream("res/input_d9_5644.txt"));
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder sb = new StringBuilder();
        StringTokenizer st;
        int T = Integer.parseInt(br.readLine());
        for (int tc = 1; tc < T + 1; tc++) {
            map = new int[MAP_SIZE + 1][MAP_SIZE + 1][];
            st = new StringTokenizer(br.readLine(), " ");
            M = Integer.parseInt(st.nextToken()); // 이동시간
            aMove = new int[M + 1];
            bMove = new int[M + 1];

            A = Integer.parseInt(st.nextToken()); // BC의 개수
            st = new StringTokenizer(br.readLine(), " ");
            for (int i = 1; i < M + 1; i++) {
                aMove[i] = Integer.parseInt(st.nextToken());
            }

            st = new StringTokenizer(br.readLine(), " ");
            for (int i = 1; i < M + 1; i++) {
                bMove[i] = Integer.parseInt(st.nextToken());
            }

            bcInfo = new int[A + 1][4];
            for (int i = 1; i < A + 1; i++) {
                st = new StringTokenizer(br.readLine(), " ");
                bcInfo[i][J_IDX] = Integer.parseInt(st.nextToken());
                bcInfo[i][I_IDX] = Integer.parseInt(st.nextToken());
                bcInfo[i][C_IDX] = Integer.parseInt(st.nextToken());
                bcInfo[i][P_IDX] = Integer.parseInt(st.nextToken());
            }

            mapSetting();

            totalCharge = 0;
            aInfo = new int[3];
            bInfo = new int[3];
            aInfo[0] = 1;
            aInfo[1] = 1;
            bInfo[0] = MAP_SIZE;
            bInfo[1] = MAP_SIZE;
            selectCharge();
            for (int time = 1; time < M + 1; time++) {
                aInfo[0] = aInfo[0] + di[aMove[time]];
                aInfo[1] = aInfo[1] + dj[aMove[time]];
                bInfo[0] = bInfo[0] + di[bMove[time]];
                bInfo[1] = bInfo[1] + dj[bMove[time]];
//                System.out.println("time = " + time);
                selectCharge();
            }
            sb.append("#").append(tc).append(" ").append(totalCharge).append("\n");
        }
        System.out.println(sb.toString());
        br.close();
    }

    private static void selectCharge() {
        int[] aCanChargeArr = map[aInfo[0]][aInfo[1]];
        int[] bCanChargeArr = map[bInfo[0]][bInfo[1]];

        if (aCanChargeArr != null && bCanChargeArr != null) {
            // 이미 내림차순으로 정렬이 돼있는 상태이기 때문에 0, 1 인덱스만 살펴보면 된다.
            int aZeroId = aCanChargeArr[0];
            int bZeroId = bCanChargeArr[0];

            if (aCanChargeArr.length == 1 && bCanChargeArr.length == 1) {
                if (aZeroId == bZeroId) {
                    aInfo[2] = bcInfo[aZeroId][P_IDX] / 2;
                    bInfo[2] = bcInfo[aZeroId][P_IDX] / 2;
                } else {
                    aInfo[2] = bcInfo[aZeroId][P_IDX];
                    bInfo[2] = bcInfo[bZeroId][P_IDX];
                }
            } else if (aCanChargeArr.length == 1) {
                int bOneId = bCanChargeArr[1];
                aInfo[2] = bcInfo[aZeroId][P_IDX];
                if (bZeroId == aZeroId) {
                    bInfo[2] = bcInfo[bOneId][P_IDX];
                } else {
                    bInfo[2] = bcInfo[bZeroId][P_IDX];
                }
            } else if (bCanChargeArr.length == 1) {
                int aOneId = aCanChargeArr[1];

                bInfo[2] = bcInfo[bZeroId][P_IDX];
                if (aZeroId == bZeroId) {
                    aInfo[2] = bcInfo[aOneId][P_IDX];
                } else {
                    aInfo[2] = bcInfo[aZeroId][P_IDX];
                }
            } else {
                int aOneId = aCanChargeArr[1];
                int bOneId = bCanChargeArr[1];
                if (aZeroId != bZeroId) {
                    aInfo[2] = bcInfo[aZeroId][P_IDX];
                    bInfo[2] = bcInfo[bZeroId][P_IDX];
                } else {
                    if (bcInfo[aOneId][P_IDX] > bcInfo[bOneId][P_IDX]) {
                        aInfo[2] = bcInfo[aOneId][P_IDX];
                        bInfo[2] = bcInfo[bZeroId][P_IDX];
                    } else {
                        aInfo[2] = bcInfo[aZeroId][P_IDX];
                        bInfo[2] = bcInfo[bOneId][P_IDX];
                    }
                }
            }

        } else if (aCanChargeArr != null) {
            int aZeroId = aCanChargeArr[0];
            aInfo[2] = bcInfo[aZeroId][P_IDX];
            bInfo[2] = 0;
        } else if (bCanChargeArr != null) {
            int bZeroId = bCanChargeArr[0];
            bInfo[2] = bcInfo[bZeroId][P_IDX];
            aInfo[2] = 0;
        } else {
            aInfo[2] = 0;
            bInfo[2] = 0;
        }
        totalCharge += aInfo[2];
        totalCharge += bInfo[2];
//        System.out.println("Arrays.toString(aInfo) = " + Arrays.toString(aInfo));
//        System.out.println("Arrays.toString(bInfo) = " + Arrays.toString(bInfo));
//        System.out.println("totalCharge = " + totalCharge);
//        System.out.println();
    }

    static void printMap() {
        System.out.println();
        for (int i = 1; i < MAP_SIZE + 1; i++) {
            for (int j = 1; j < MAP_SIZE + 1; j++) {
                System.out.printf("%-10s", Arrays.toString(map[i][j]));
            }
            System.out.println();
        }
    }

    static void mapSetting() {
        for (int id = 1; id < A + 1; id++) {
            int iPos = bcInfo[id][I_IDX];
            int jPos = bcInfo[id][J_IDX];
            int range = bcInfo[id][C_IDX];
//            int performance = bcInfo[i][P_IDX];

            setBCInMap(id, iPos, jPos);

            ArrayDeque<int[]> q = new ArrayDeque<>();
            boolean[][] v = new boolean[MAP_SIZE + 1][MAP_SIZE + 1];
            q.offer(new int[]{iPos, jPos, 0}); // 좌표와 움직인 거리를 담는다.
            v[iPos][jPos] = true;
            while (!q.isEmpty()) {
                int[] polled = q.poll();
                for (int d = 1; d < 5; d++) {
                    int ni = polled[0] + di[d];
                    int nj = polled[1] + dj[d];
                    int newD = polled[2] + 1;
                    if (0 < ni && ni < MAP_SIZE + 1 && 0 < nj && nj < MAP_SIZE + 1 && !v[ni][nj] && newD <= range) {
                        v[ni][nj] = true;
                        q.offer(new int[]{ni, nj, newD});
                        setBCInMap(id, ni, nj);
                    }
                }
            }
        }
//        printMap();
    }

    private static void setBCInMap(int id, int iPos, int jPos) {
        int[] posArr = map[iPos][jPos];
//        System.out.println();
//        System.out.println("id = " + id);
//        System.out.println("iPos = " + iPos);
//        System.out.println("jPos = " + jPos);
//        System.out.println("bcInfo[id][P_IDX] = " + bcInfo[id][P_IDX]);
//        System.out.println(Arrays.toString(posArr));
        if (posArr == null) {
            posArr = new int[1];
            posArr[0] = id; // id를 넣는다.
        } else {
            int[] tempArr = new int[posArr.length + 1];
            int tempIdx = 0;
            boolean isInserted = false;
            for (int j = 0; j < posArr.length; j++) {
                int tempId = posArr[j];
                if (bcInfo[tempId][P_IDX] >= bcInfo[id][P_IDX]) {
                    tempArr[tempIdx++] = posArr[j];
                } else if (!isInserted){
//                    System.out.println("bcInfo[tempId][P_IDX] = " + bcInfo[tempId][P_IDX]);
                    tempArr[tempIdx++] = id; // null이 아니었다면 추가해서 넣는다.
                    tempArr[tempIdx++] = posArr[j];
                    isInserted = true;
                }
            }
            if (!isInserted) {
                // 아직 isInserted가 false라는 것은 제일 마지막에 추가해줘야한다는 뜻이다.
                tempArr[posArr.length] = id;
            }
            posArr = tempArr;
        }
//        System.out.println(Arrays.toString(posArr));
        map[iPos][jPos] = posArr;
    }
}
