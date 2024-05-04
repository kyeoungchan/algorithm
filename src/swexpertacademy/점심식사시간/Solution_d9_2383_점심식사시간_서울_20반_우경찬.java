package swexpertacademy.점심식사시간;

import java.util.*;
import java.io.*;

public class Solution_d9_2383_점심식사시간_서울_20반_우경찬 {
    static int N, cnt1, cnt2, ANS;
    static int[][] stairInfo;
    static List<int[]> peopleInfo;

    public static void main(String[] args) throws Exception {
        System.setIn(new FileInputStream("res/input_d9_2383.txt"));
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder sb = new StringBuilder();
        StringTokenizer st;
        int T = Integer.parseInt(br.readLine());
        for (int tc = 1; tc < T + 1; tc++) {
            N = Integer.parseInt(br.readLine());
            stairInfo = new int[2][3];
            peopleInfo = new ArrayList<>();
            peopleInfo.add(null); // size == 1 -> idx = 1
            int stairId = 0;
            for (int i = 0; i < N; i++) {
                st = new StringTokenizer(br.readLine(), " ");
                for (int j = 0; j < N; j++) {
                    int data = Integer.parseInt(st.nextToken());
                    if (data == 1) {
                        peopleInfo.add(new int[]{i, j});
                    } else if (data != 0) {
                        stairInfo[stairId][0] = i;
                        stairInfo[stairId][1] = j;
                        stairInfo[stairId][2] = data;
                        stairId++;
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

        selectStairs(cnt + 1, selectedStairs);
        selectedStairs[cnt] = 1;
        selectStairs(cnt + 1, selectedStairs);
        selectedStairs[cnt] = 0;
    }

    static void calculateTime(int[] selectedStairs, int stairId) {
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
            boolean[] updateNext = new boolean[3];

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
                        for (int j = 0; j < 3; j++) { // 1번 친구 -> 2번친구. 1번 친구 -> 2번 친구
                            if (occupyingP[j] == pId) {
                                isOccupying = true;
                                selfIdx = j;
                                break;
                            } else if (!hasEmptySpace && occupyingP[j] == 0) {
                            	// 가장 앞에 빈 칸 하나를 탐색했으면 그 다음 빈 칸은 탐색을 생략하기 위해 hasEmptySpace도 조건문으로 추가
                            	if (updateNext[j]) continue; // 이번 턴에서 빈 칸이 생긴 경우라면 다음 턴에서부터 사용할 수 있어야하므로 건너뛴다.
                                hasEmptySpace = true;
                                emtpyIdx = j;
                            }
                        }
                        if (isOccupying) {
                            // 이미 차지하고 있다면
                            if (goingDownTime - 1 == 0) { // 1번 친구 -> 0 1 2 자리 중에서 0번 사용 반납 -> 동시간대에 2번친구
                                // 다 내려왔다면
                                occupyingP[selfIdx] = 0;
                                updateNext[selfIdx] = true;
                            } else {
                                q.offer(new int[]{pId, dist, waitTime, goingDownTime - 1});
                            }
                        } else if (hasEmptySpace) {
                            // 본인이 차지하고 있지는 않지만, 빈 자리가 환있다면
                            occupyingP[emtpyIdx] = pId;
                            q.offer(new int[]{pId, dist, waitTime, goingDownTime - 1});
                        } else {
                            // 차지하고 있지도, 빈 자리도 없다면
                            q.offer(cur);
                        }
                    }
                }
            }
            time++;
        }
        if (stairId == 0) {
            cnt1 = time;
        } else {
            cnt2 = time;
        }
    }
}
