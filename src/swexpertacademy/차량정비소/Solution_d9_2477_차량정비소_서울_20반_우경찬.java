package swexpertacademy.차량정비소;

import java.util.*;
import java.io.*;

public class Solution_d9_2477_차량정비소_서울_20반_우경찬 {

    final static int CUSTOM_ID = 0, ARRIVE_TIME = 1, RECEIVE_NUM = 2, RECEIVE_TIME = 3, REPAIR_ARRIVE = 4, REPAIR_NUM = 5, REPAIR_TIME = 6;

    /**
     * 고객이 오는 순서는 배열을 오는 시간에 따른 순서로 정렬을 한 후 인덱스를 이용해서 도착한 고객과 도착하지 않은 고객을 나타낸다. 인덱스는 아직 오지 않은 가장 먼저 올 고객을 가리킨다.
     * 접수 창구와 정비 창구는 배열로 관리한다. 배열에 고객 id를 넣는다.
     * 창구과 가득차서 기다리는 고객의 정보를 담는 Q를 각각 구현한다.
     * 고객마다 필요한 정보: 고객 번호, 도착하게 될 시간, 접수한 창구 번호, 정비 창구에 도착한 시간, 정비를 받은 창구 번호
     * <p>
     * 그리고 각 고객마다 접수 창구 번호, 정비 창구 번호가 같은 고객 번호를 담는 리스트가 더 필요하다.
     */
    public static void main(String[] args) throws Exception {
        System.setIn(new FileInputStream("res/input_d9_2477.txt"));
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder sb = new StringBuilder();
        StringTokenizer st;
        int T = Integer.parseInt(br.readLine());
        for (int tc = 1; tc < T + 1; tc++) {
            st = new StringTokenizer(br.readLine(), " ");
            int N = Integer.parseInt(st.nextToken());
            int M = Integer.parseInt(st.nextToken());
            int K = Integer.parseInt(st.nextToken());
            int A = Integer.parseInt(st.nextToken());
            int B = Integer.parseInt(st.nextToken());

            int[] receiveTime = new int[N + 1];
            st = new StringTokenizer(br.readLine(), " ");
            for (int i = 1; i < N + 1; i++) {
                receiveTime[i] = Integer.parseInt(st.nextToken());
            }

            int[] repairTime = new int[M + 1];
            st = new StringTokenizer(br.readLine(), " ");
            for (int i = 1; i < M + 1; i++) {
                repairTime[i] = Integer.parseInt(st.nextToken());
            }

            st = new StringTokenizer(br.readLine(), " ");
            int[][] customInfo = new int[K + 1][7];
            int[][] customInfo2 = new int[K + 1][2];
//            boolean[] customDone = new boolean[K + 1];
            for (int i = 1; i < K + 1; i++) {
                customInfo[i][CUSTOM_ID] = i;
                customInfo[i][ARRIVE_TIME] = Integer.parseInt(st.nextToken());
                customInfo2[i][CUSTOM_ID] = i;
                customInfo2[i][ARRIVE_TIME] = customInfo[i][ARRIVE_TIME];
            }


            Arrays.sort(customInfo2, Comparator.comparingInt(c -> c[1])); // 도착 순서대로 정렬
            int remainCustoms = K;
            int time = 0;
            PriorityQueue<int[]> waitingReceive = new PriorityQueue<>(Comparator.comparing(c -> c[CUSTOM_ID]));
            int[] occupyingReceive = new int[N + 1];
            PriorityQueue<int[]> waitingRepair = new PriorityQueue<>(Comparator.comparing((int[] c) -> c[REPAIR_ARRIVE]).thenComparing(c -> c[RECEIVE_NUM]));
            int[] occupyingRepair = new int[M + 1];
            int commingIdx = 1;
            while (remainCustoms != 0) {
                while (commingIdx < K + 1 && customInfo2[commingIdx][ARRIVE_TIME] == time) {
                    waitingReceive.offer(customInfo2[commingIdx]);
                    commingIdx++;
                }

                for (int rNum = 1; rNum < N + 1; rNum++) {
                    if (occupyingReceive[rNum] != 0) {
                        int cId = occupyingReceive[rNum];
                        if (customInfo[cId][RECEIVE_TIME] == receiveTime[rNum]) {
                            occupyingReceive[rNum] = 0;
                            customInfo[cId][REPAIR_ARRIVE] = time;
                            waitingRepair.offer(customInfo[cId]);
                        } else {
                            customInfo[cId][RECEIVE_TIME]++;
                        }
                    }
                }

                if (!waitingReceive.isEmpty()) {
                    for (int rNum = 1; rNum < N + 1; rNum++) {
                        if (occupyingReceive[rNum] == 0) {
                            int cId = waitingReceive.poll()[CUSTOM_ID];
                            occupyingReceive[rNum] = cId;

                            customInfo[cId][RECEIVE_NUM] = rNum;
                            customInfo[cId][RECEIVE_TIME] = 1;
                            if (waitingReceive.isEmpty()) {
                                break;
                            }
                        }
                    }
                }

                for (int rpNum = 1; rpNum < M + 1; rpNum++) {
                    if (occupyingRepair[rpNum] != 0) {
                        int cId = occupyingRepair[rpNum];
                        if (customInfo[cId][REPAIR_TIME] == repairTime[rpNum]) {
                            occupyingRepair[rpNum] = 0;
                            remainCustoms--;
                        } else {
                            customInfo[cId][REPAIR_TIME]++;
                        }
                    }
                }

                if (!waitingRepair.isEmpty()) {
                    for (int rpNum = 1; rpNum < M + 1; rpNum++) {
                        if (occupyingRepair[rpNum] == 0) {
                            int cId = waitingRepair.poll()[CUSTOM_ID];
                            occupyingRepair[rpNum] = cId;

                            customInfo[cId][REPAIR_NUM] = rpNum;
                            customInfo[cId][REPAIR_TIME] = 1;
                            if (waitingRepair.isEmpty()) {
                                break;
                            }
                        }
                    }
                }

                time++;
            }

            int ans = 0;
            for (int id = 1; id < K + 1; id++) {
                if (customInfo[id][RECEIVE_NUM] == A && customInfo[id][REPAIR_NUM] == B) {
                    ans += id;
                }
            }
            if (ans == 0) ans--;
            sb.append("#").append(tc).append(" ").append(ans).append("\n");
        }
        System.out.println(sb.toString());
        br.close();
    }
}
