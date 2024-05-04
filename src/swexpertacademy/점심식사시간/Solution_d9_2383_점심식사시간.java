package swexpertacademy.점심식사시간;

import java.io.*;
import java.util.*;

/**
 * NxN 크기의 정사각형 모양의 방
 * 최대한 빠른 시간 내에 내려가야한다.
 * 방안의 사람들은 P, 계단 입구는 S
 * 이동완료 시간: 모든 사람들이 계단을 내려가 아래 층으로 이동을 완료한 시간
 * 아래층으로 이동하는 시간은 계단 입구까지 이동 시간 + 계단을 내려가는 시간
 * 이동시간: 행의 차이 + 열의 차이
 * 계단을 내려가는 시간
 * - 계단 위에는 최대 3명만 올라가 있을 수 있다.
 * - 나머지는 계단 입구에서 대기해야한다.
 * - 계단에 올라간 후 완전히 내려가는 시간:K
 * 모든 사람들이 계단을 내려가 이동이 완료되는 시간이 최소가 되는 경우
 */
public class Solution_d9_2383_점심식사시간 {

    static class Person {
        int r;
        int c;
        int targetStair;
        int distFromStair;
        boolean arrived;
        boolean usingStair;
        boolean completed;

        public Person(int r, int c) {
            this.r = r;
            this.c = c;
            arrived = false;
            usingStair = false;
            completed = false;
        }

        public void clear() {
            arrived = false;
            usingStair = false;
            completed = false;
            setTarget(targetStair);
        }

        public void setTarget(int t) {
            Stair target = stairs.get(t);
            targetStair = t;
            distFromStair = getDistance(r, c, target.r, target.c);
        }

    }

    static class Stair {
        int r;
        int c;
        int len;
        int[] occupied = new int[3];
        int[] time = new int[3];

        public Stair(int r, int c, int len) {
            this.r = r;
            this.c = c;
            this.len = len;
            Arrays.fill(occupied, -1);
        }

        public void insertPerson(Person person, int pIdx) {
            person.usingStair = true;
            for (int i = 0; i < 3; i++) {
                if (occupied[i] == -1) {
                    occupied[i] = pIdx;
                    time[i] = len;
                    break;
                }
            }
        }

        public void timeWent() {
            for (int i = 0; i < 3; i++) {
                if (occupied[i] == -1) continue;
                time[i]--;
                if (time[i] == 0) {
                    people.get(occupied[i]).completed = true;
                    done++;
                    occupied[i] = -1;
                }
            }
        }

        public boolean canInsert() {
            for (int p : occupied) {
                if (p == -1) return true;
            }
            return false;
        }
    }

    static int minTime, done;
    static List<Person> people;
    static List<Stair> stairs;

    public static void main(String[] args) throws Exception {
        System.setIn(new FileInputStream("res/input_d9_2383.txt"));
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;
        StringBuilder sb = new StringBuilder();
        int T = Integer.parseInt(br.readLine());
        for (int tc = 1; tc < T + 1; tc++) {
            int N = Integer.parseInt(br.readLine());
            people = new ArrayList<>();
            stairs = new ArrayList<>();
            int value;
            for (int i = 0; i < N; i++) {
                st = new StringTokenizer(br.readLine(), " ");
                for (int j = 0; j < N; j++) {
                    value = Integer.parseInt(st.nextToken());
                    if (value == 1) people.add(new Person(i, j));
                    else if (value != 0) stairs.add(new Stair(i, j, value)); // 좌표와 계단을 내려가는 데 걸리는 시간
                }
            }
            minTime = Integer.MAX_VALUE;
            chooseStair(0);
            sb.append("#").append(tc).append(" ").append(minTime).append("\n");
        }
        System.out.print(sb.toString());
        br.close();
    }

    static void chooseStair(int cnt) {
        if (cnt == people.size()) {
            simulate();
            return;
        }

        Person cur = people.get(cnt);
        for (int t = 0; t < stairs.size(); t++) {
            cur.setTarget(t);
            chooseStair(cnt + 1);
        }
    }

    static int getDistance(int pr, int pc, int sr, int sc) {
        return Math.abs(pr - sr) + Math.abs(pc - sc);
    }

    static void simulate() {
        int time = 0;
        done = 0;
        while (true) {
            for (Stair s : stairs) {
                s.timeWent();
                // 계단을 다 내려가면 done이 올라가고, Person은 completed 처리되도록 stair 내부 메서드에서 동작
            }
            for (int pIdx = 0; pIdx < people.size(); pIdx++) {
                Person p = people.get(pIdx);
                if (p.completed) continue;
                if (p.distFromStair > 0) {
                    p.distFromStair--;
                    if (p.distFromStair == 0) p.arrived = true;
                    // 계단 입구에 도착하면 1분 후 아래칸으로 내려갈 수 있다.
                } else if (!p.usingStair) {
                    // 일단 거리가 도달하면 다 arrived 처리가 되므로 이젠 계단을 사용 중인지 여부부터 살펴본다.
                    Stair target = stairs.get(p.targetStair);
                    // 계단 사용이 가능하면 계단을 사용한다.
                    if (target.canInsert())target.insertPerson(p, pIdx);
                }
                // 그 이후부터 계단 객체에서 사람들을 관리한다.
            }
            time++;
            if (done == people.size()) {
                break;
            }
        }
        minTime = Math.min(minTime, time);
        for (Person p : people) {
            p.clear();
        }
    }

}
