import java.util.*;
import java.io.*;

public class Solution {

    static class BatteryCharger {
        int r, c, range, performance;
        List<User> usingUsers;

        public BatteryCharger(int r, int c, int range, int performance) {
            this.r = r;
            this.c = c;
            this.range = range;
            this.performance = performance;
            usingUsers = new ArrayList<>();
        }

        public boolean canCharge(User user) {
            int userR = user.r;
            int userC = user.c;
            return Math.abs(userR - r) + Math.abs(userC - c) <= range;
        }

        public void charge(User user) {
            // recheck
            if (usingUsers.contains(user)) {
                System.out.println("Internal Error! why user still in users?");
                return;
            }
            if (usingUsers.isEmpty()) {
                usingUsers.add(user);
                user.charge = performance;
            } else {
                usingUsers.add(user);
                for (User u : usingUsers) u.charge = performance / 2;
            }
        }

        public void freeUser(User user) {
            if (!usingUsers.contains(user)) {
                System.out.println("Internal Error! why user does not in users?");
                return;
            }
            user.charge = 0;
            usingUsers.remove(user);
            // recheck
            if (!usingUsers.isEmpty()) usingUsers.get(0).charge = performance;
        }
    }

    static class User {
        int r, c, charge;

        public User(int r, int c) {
            this.r = r;
            this.c = c;
            charge = 0;
        }

        public void move(int d) {
            r += dr[d];
            c += dc[d];
        }
    }

    static User userA, userB;
    static int maxChargeSingleStage, totalCharge, A;
    static int[] aTrace, bTrace, dr = {0, -1, 0, 1, 0}, dc = {0, 0, 1, 0, -1};
    static BatteryCharger[] bcs;

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;
        StringBuilder sb = new StringBuilder();
        int T = Integer.parseInt(br.readLine());
        for (int tc = 1; tc < T + 1; tc++) {
            st = new StringTokenizer(br.readLine());
            int M = Integer.parseInt(st.nextToken());
            A = Integer.parseInt(st.nextToken());
            aTrace = new int[M];
            st = new StringTokenizer(br.readLine());
            for (int i = 0; i < M; i++) {
                aTrace[i] = Integer.parseInt(st.nextToken());
            }
            bTrace = new int[M];
            st = new StringTokenizer(br.readLine());
            for (int i = 0; i < M; i++) {
                bTrace[i] = Integer.parseInt(st.nextToken());
            }

            bcs = new BatteryCharger[A];
            for (int i = 0; i < A; i++) {
                st = new StringTokenizer(br.readLine());
                int c = Integer.parseInt(st.nextToken());
                int r = Integer.parseInt(st.nextToken());
                int range = Integer.parseInt(st.nextToken());
                int performance = Integer.parseInt(st.nextToken());
                bcs[i] = new BatteryCharger(r, c, range, performance);
            }

            userA = new User(1, 1);
            userB = new User(10, 10);
            totalCharge = 0;
            selectBatteryCharger();
            for (int i = 0; i < M; i++) {
                userA.move(aTrace[i]);
                userB.move(bTrace[i]);
                selectBatteryCharger();
            }
            sb.append("#").append(tc).append(" ").append(totalCharge).append("\n");
        }
        System.out.print(sb.toString());
        br.close();
    }

    static void selectBatteryCharger() {
        maxChargeSingleStage = 0;
        boolean aCharged = false;
        boolean bCharged = false;
        for (int i = 0; i < A; i++) {
            BatteryCharger bcI = bcs[i];
            if(!bcI.canCharge(userA)) continue;
            bcI.charge(userA);
            aCharged = true;
            for (int j = 0; j < A; j++) {
                BatteryCharger bcJ = bcs[j];
                if(!bcJ.canCharge(userB)) continue;
                bCharged = true;
                bcJ.charge(userB);
                maxChargeSingleStage = Math.max(maxChargeSingleStage, userA.charge + userB.charge);
                bcJ.freeUser(userB);
            }
            if (!bCharged) {
                // B가 한 번도 충전을 못했더라도 A는 충전할 수 있다.
                maxChargeSingleStage = Math.max(maxChargeSingleStage, userA.charge);
            }
            bcI.freeUser(userA);
        }
        if (!aCharged) {
            // A가 충전을 한 번도 못했더라도 B는 충전할 수 있을 수 있다.
            for (int j = 0; j < A; j++) {
                BatteryCharger bcJ = bcs[j];
                if(!bcJ.canCharge(userB)) continue;
                bcJ.charge(userB);
                maxChargeSingleStage = Math.max(maxChargeSingleStage, userB.charge);
                bcJ.freeUser(userB);
            }
        }
        totalCharge += maxChargeSingleStage;
    }
}