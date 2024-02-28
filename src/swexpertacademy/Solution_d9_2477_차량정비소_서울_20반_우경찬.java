package swexpertacademy;

import java.util.*;
import java.io.*;

public class Solution_d9_2477_차량정비소_서울_20반_우경찬 {

    final static int CUSTOM_ID = 0, ARRIVE_TIME = 1, RECEIVE_NUM = 2, RECEIVE_TIME = 3, REPAIR_NUM = 4, REPAIR_TIME = 5;

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

            int[] receive = new int[N + 1];
            st = new StringTokenizer(br.readLine(), " ");
            for (int i = 1; i < N + 1; i++)
                receive[i] = Integer.parseInt(st.nextToken());
            st = new StringTokenizer(br.readLine(), " ");
            int[] repair = new int[M + 1];
            for (int i = 1; i < M + 1; i++)
                repair[i] = Integer.parseInt(st.nextToken());

            int[][] customInfo = new int[K + 1][6]; // 고객 번호, 도착 시간, 접수 창구 번호, 정비 창구 번호
            int maxTime = 0;
            st = new StringTokenizer(br.readLine(), " ");
            PriorityQueue<int[]> commingPq = new PriorityQueue<>(Comparator.comparingInt(c -> c[ARRIVE_TIME]));
            for (int i = 1; i < K + 1; i++) {
                int time = Integer.parseInt(st.nextToken());
                customInfo[i][CUSTOM_ID] = i; // 고객 번호도 같이 저장
                customInfo[i][ARRIVE_TIME] = time;
                commingPq.offer(customInfo[i]);
                maxTime = Math.max(maxTime, time);
            }

            int ans = 0;
            int[] receiveOccupied = new int[N + 1];
            int[] repairOccupied = new int[M + 1];

            PriorityQueue<int[]> waitingReceive = new PriorityQueue<>(Comparator.comparingInt(c -> c[CUSTOM_ID]));
            int time = 1;
            PriorityQueue<int[]> waitingRepair = new PriorityQueue<>(Comparator.comparingInt(c -> c[RECEIVE_NUM]));

            while (!commingPq.isEmpty() && !allEmpty(receiveOccupied) && !waitingReceive.isEmpty() && !allEmpty(repairOccupied) && !waitingRepair.isEmpty()) {
                int commingPqSize = commingPq.size();
                for (int i = 0; i < commingPqSize; i++) {
                    if (commingPq.peek()[ARRIVE_TIME] == time) {
                        waitingReceive.offer(commingPq.poll());
                    } else {
                        break;
                    }
                }

                for (int i = 1; i < N + 1; i++) {
                    if (waitingReceive.isEmpty()) break;
                    if (receiveOccupied[i] == 0) {
                        // 접수 창구가 빈 경우
                        int[] cur = waitingReceive.poll();
                        cur[RECEIVE_NUM] = i;
                        cur[RECEIVE_TIME] = receive[i];
                        receiveOccupied[i] = cur[CUSTOM_ID];
                    } else {
                        int cId = receiveOccupied[i];
                        customInfo[cId][RECEIVE_TIME]--;
                        if (customInfo[cId][RECEIVE_TIME] == 0) {
                            receiveOccupied[i] = 0;
                            waitingRepair.offer(customInfo[cId]);
                        }
                    }
                }

                for (int i = 1; i < M + 1; i++) {
                    if (waitingRepair.isEmpty()) break;
                    if (repairOccupied[i] == 0) {
                        // 접수 창구가 빈 경우
                        int[] cur = waitingRepair.poll();
                        cur[REPAIR_NUM] = i;
                        cur[REPAIR_TIME] = receive[i];
                        repairOccupied[i] = cur[CUSTOM_ID];
                    } else {
                        int cId = repairOccupied[i];
                        customInfo[cId][REPAIR_TIME]--;
                        if (customInfo[cId][REPAIR_TIME] == 0) {
                            repairOccupied[i] = 0;
//                            waitingRepair.offer(customInfo[cId]);
                        }
                    }
                }
                time++;
            }

            for (int[] info : customInfo) {
                System.out.println(Arrays.toString(info));
                if (info[RECEIVE_NUM] == A && info[REPAIR_NUM] == B) {
                    ans += info[CUSTOM_ID];
                }
            }
            System.out.println();
            sb.append("#").append(tc).append(" ").append(ans).append("\n");
        }
        System.out.println(sb.toString());
        br.close();
    }

    static boolean allEmpty(int[] desks) {
        for (int i = 0; i < desks.length; i++) {
            if (desks[i] != 0) {
                return false;
            }
        }
        return true;
    }

}
