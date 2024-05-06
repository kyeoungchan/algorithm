package swexpertacademy.무선충전;

import java.io.*;
import java.util.*;

/**
 * BC의 정보와 사용자의 이동궤적은 주어진다. 이때 모든 사용자가 충전한 양의 최댓값을 구하는 프로그램을 구해보자.
 * 같은 위치에 2개 이상의 BC가 설치된 경우는 없다.
 * 사용자가 같은 위치로 이동할 수는 있다.
 */
public class Solution_d9_5644_무선충전 {

    static class BatteryCharger {
        // BatteryCharger는 위치와 충전 범위, 그리고 성능 정보가 있다.
        int r;
        int c;
        int range;
        int performance;
        List<User> usingUsers;

        public BatteryCharger(int r, int c, int range, int performance) {
            this.r = r;
            this.c = c;
            this.range = range;
            this.performance = performance;
            usingUsers = new ArrayList<>();
        }

        public boolean canCharge(User user) {
            // 충전 범위가 C일 때 거리가 C 이하면 접속 가능하다. 거리: |x1-x2| + |y1-y2|
            return Math.abs(user.r - r) + Math.abs(user.c - c) <= range;
        }

        public void charge(User user) {
            if (usingUsers.isEmpty()) {
                usingUsers.add(user);
                user.chargeVol = performance;
            } else {
                // 한 BC에 두 명의 사용자가 접속한 경우, 접속한 사용자의 수만큼 충전양을 균등하게 분배한다.
                usingUsers.add(user);
                for (User u : usingUsers)
                    // BC의 성능은 항상 짝수다. (10 ~ 500)
                    u.chargeVol = performance / 2;
            }
        }

        public void freeUser(User user) {
            for (int i = 0; i < usingUsers.size(); i++) {
                if (user.equals(usingUsers.get(i))) {
                    user.chargeVol = 0;
                    usingUsers.remove(i);
                    break;
                }
            }
            if (!usingUsers.isEmpty()) usingUsers.get(0).chargeVol = performance;
        }

    }

    static class User {
        int r;
        int c;
        int chargeVol;

        public User(int r, int c) {
            this.r = r;
            this.c = c;
            chargeVol = 0;
        }

        public void move(int d) {
            // 사용자가 지도 밖으로 이동하는 경우는 없다. 이 조건 덕분에 그냥 이동만 시키면 된다.
            this.r += di[d];
            this.c += dj[d];
        }
    }

    static int M, userAVol, userBVol, maxThisTime, userAVolThisTime, userBVolThisTime;
    static int[] di = {0, -1, 0, 1, 0}, dj = {0, 0, 1, 0, -1}, userATrace, userBTrace;
//    static int[][] map;
    static List<BatteryCharger> bcs;
    static User userA, userB;

    public static void main(String[] args) throws Exception {
        System.setIn(new FileInputStream("res/input_d9_5644.txt"));
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;
        StringBuilder sb = new StringBuilder();
        int T = Integer.parseInt(br.readLine());
        for (int tc = 1; tc < T + 1; tc++) {
            // 지도의 크기: 10 x 10
            // 1,1 ~ 10,10으로 범위가 주어진다.

            // map을 사용하지 않았다.
//            map = new int[11][11];

            st = new StringTokenizer(br.readLine(), " ");
            M = Integer.parseInt(st.nextToken());
            // 인덱스 초일 때 취해야할 액션!
            // 0초일 때는 처음 위치다.
            userATrace = new int[M];
            userBTrace = new int[M];

            int A = Integer.parseInt(st.nextToken());

            /* 사용자는 2명이다.
             * 사용자A는 1,1 지점에서, 사용자B는 10,10에서 출발한다.*/
            userA = new User(1, 1);
            st = new StringTokenizer(br.readLine(), " ");
            for (int i = 0; i < M; i++) {
                userATrace[i] = Integer.parseInt(st.nextToken());
            }

            userB = new User(10, 10);
            st = new StringTokenizer(br.readLine(), " ");
            for (int i = 0; i < M; i++) {
                userBTrace[i] = Integer.parseInt(st.nextToken());
            }

            bcs = new ArrayList<>();
            for (int i = 0; i < A; i++) {
                st = new StringTokenizer(br.readLine(), " ");
                int c = Integer.parseInt(st.nextToken());
                int r = Integer.parseInt(st.nextToken());
                int range = Integer.parseInt(st.nextToken());
                int performance = Integer.parseInt(st.nextToken());
                bcs.add(new BatteryCharger(r, c, range, performance));
            }
            userAVol = 0;
            userBVol = 0;
            simulate();
            sb.append("#").append(tc).append(" ").append(userAVol + userBVol).append("\n");
        }
        System.out.print(sb.toString());
        br.close();
    }

    static void simulate() {
        maxThisTime = 0;
        userAVolThisTime = 0;
        userBVolThisTime = 0;
        // 움직이기 전에 충전받기
        selectBatteryCharger(0);
        userAVol += userAVolThisTime;
        userBVol += userBVolThisTime;
//        debug(0);
        for (int time = 0; time < M; time++) {
            // 사용자는 1초에 한 칸씩 움직인다.
            userA.move(userATrace[time]);
            userB.move(userBTrace[time]);

            maxThisTime = 0;
            userAVolThisTime = 0;
            userBVolThisTime = 0;
            selectBatteryCharger(0);
            userAVol += userAVolThisTime;
            userBVol += userBVolThisTime;
//            debug(time + 1);
        }
    }

    // 여러 충전 범위에 속할 때는 하나를 선택해서 접속할 수 있다.
    static void selectBatteryCharger(int cnt) {
        if (cnt == 2) {
            if (maxThisTime < userA.chargeVol + userB.chargeVol) {
                // 디버깅을 위해서 userA와 userB를 따로 기록해두자.
                userAVolThisTime = userA.chargeVol;
                userBVolThisTime = userB.chargeVol;
                maxThisTime = userAVolThisTime + userBVolThisTime;
            }
            return;
        }

        boolean charged = false;
        User u;
        if (cnt == 0) u = userA;
        else u = userB;
        for (BatteryCharger bc : bcs) {
            if (!bc.canCharge(u)) continue;

            charged = true;
            bc.charge(u);
            selectBatteryCharger(cnt + 1);
            bc.freeUser(u);
        }
        // userA가 충전하지 못했더라도 userB는 충전할 수 있으므로 따로 재귀호출을 해주어야 한다.
        if (!charged) selectBatteryCharger(cnt + 1);
    }
}
