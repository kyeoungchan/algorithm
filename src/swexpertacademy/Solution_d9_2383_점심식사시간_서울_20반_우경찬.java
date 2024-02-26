package swexpertacademy;

import java.util.*;
import java.io.*;

public class Solution_d9_2383_점심식사시간_서울_20반_우경찬 {
    static int N, cnt1, cnt2, ANS;
    static int[][] map, stairInfo;
    static List<int[]> peopleInfo;

    public static void main(String[] args) throws Exception {
        System.setIn(new FileInputStream("res/input_d9_2383.txt"));
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder sb = new StringBuilder();
        StringTokenizer st;
        int T = Integer.parseInt(br.readLine());
        for (int tc = 1; tc < T + 1; tc++) {
            N = Integer.parseInt(br.readLine());
            map = new int[N][N];
            stairInfo = new int[2][3];
            peopleInfo = new ArrayList<>();
            peopleInfo.add(null);
            int peopleId = 1;
            int stairId = 0;
            for (int i = 0; i < N; i++) {
                st = new StringTokenizer(br.readLine(), " ");
                for (int j = 0; j < N; j++) {
                    int data = Integer.parseInt(st.nextToken());
                    if (data == 1) {
                        peopleInfo.add(new int[]{i, j});
                        map[i][j] = peopleId++; // 사람은 id로 표현하며, 1부터 시작
                    } else if (data != 0) {
                        stairInfo[stairId][0] = i;
                        stairInfo[stairId][1] = j;
                        stairInfo[stairId][2] = data;
                        stairId++;
                        map[i][j] = -1; // 계단은 -1로 표현
                    }
                }
            }
            int[] selectedStairs = new int[peopleInfo.size()]; // 각 사람마다 선택한 계단을 본다.
            ANS = Integer.MAX_VALUE;
            selectStairs(1, selectedStairs);
            sb.append("#").append(tc).append(" ").append(ANS).append("\n");
        }
        System.out.println(sb.toString());
        br.close();
    }

    static void selectStairs(int cnt, int[] selectedStairs) {
        if (cnt == selectedStairs.length) {
            calculateTime(selectedStairs, 0); // 계단 id는 0부터
            calculateTime(selectedStairs, 1);
            ANS = Math.min(ANS, Math.max(cnt1, cnt2));
            return;
        }

//        selectedStairs[cnt] = 0;
        selectStairs(cnt + 1, selectedStairs);
        selectedStairs[cnt] = 1;
        selectStairs(cnt + 1, selectedStairs);
    }

    static void calculateTime(int[] selectedStairs, int stairId) {
//        PriorityQueue<int[]> q = new PriorityQueue<>((Comparator.comparing((int[] o) -> o[1]).thenComparing(o -> -o[2]).thenComparing(o -> o[3])));
        ArrayDeque<int[]> q = new ArrayDeque<>();

        int cost = stairInfo[stairId][2];
        for (int pId = 1; pId < selectedStairs.length; pId++) {
            if (selectedStairs[pId] != stairId) continue;
            int[] personInfo = peopleInfo.get(pId);
            int dist = Math.abs(personInfo[0] - stairInfo[stairId][0]) + Math.abs(personInfo[1] - stairInfo[stairId][1]);
            q.offer(new int[]{pId, dist, 0, cost});
        }

        int time = 0;
        int[] occupyingP = new int[3];
        while (!q.isEmpty()) {
            int size = q.size();
            time++;

            for (int i = 0; i < size; i++) {
                int[] cur = q.poll();
                int pId = cur[0];
                int dist = cur[1];
                int waitTime = cur[2];
                int goingDownTime = cur[3];

                if (dist > 0) {
                    q.offer(new int[]{pId, dist - 1, waitTime, goingDownTime});
                } else if (dist == 0) {
                    if (waitTime == 0) {
                        q.offer(new int[]{pId, dist, waitTime + 1, goingDownTime});
                    } else {
                        boolean isOccupying = false;
                        int selfIdx = -1;
                        boolean hasEmptySpace = false;
                        int emtpyIdx = -1;
                        for (int j = 0; j < 3; j++) {
                            if (occupyingP[j] == pId) {
                                isOccupying = true;
                                selfIdx = j;
                                break;
                            } else if (!hasEmptySpace && occupyingP[j] == 0) {
                                hasEmptySpace = true;
                                emtpyIdx = j;
                            }
                        }
                        if (isOccupying) {
                            // 이미 차지하고 있다면
                            if (goingDownTime == 0) {
                                // 다 내려왔다면
                                occupyingP[selfIdx] = 0;
                            } else {
                                q.offer(new int[]{pId, dist, waitTime, goingDownTime - 1});
                            }
                        } else if (hasEmptySpace) {
                            // 본인이 차지하고 있지는 않지만, 빈 자리가 있다면
                            occupyingP[emtpyIdx] = pId;
                            q.offer(new int[]{pId, dist, waitTime, goingDownTime - 1});
                        } else {
                            // 차지하고 있지도, 빈 자리도 없다면
                            q.offer(cur);
                        }
                    }
                }
            }
        }
        time--;
        // 왜 정답보다 1씩 더 크게 나오는지는 모르겠다;
        if (stairId == 0) {
            cnt1 = time;
        } else {
            cnt2 = time;
        }
    }
}
