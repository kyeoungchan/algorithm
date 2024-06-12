package swexpertacademy.병사관리;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.StringTokenizer;
import java.util.*;

/**
 * 병사들은 각각 고유번호, 소속팀, 평판 점수를 가지고 있다.
 */
public class Solution_pro_13072_병사관리 {
    private final static int CMD_INIT = 1;
    private final static int CMD_HIRE = 2;
    private final static int CMD_FIRE = 3;
    private final static int CMD_UPDATE_SOLDIER = 4;
    private final static int CMD_UPDATE_TEAM = 5;
    private final static int CMD_BEST_SOLDIER = 6;

    private final static UserSolution usersolution = new UserSolution();

    private static boolean run(BufferedReader br) throws Exception {
        StringTokenizer st;

        int numQuery;

        int mID, mTeam, mScore, mChangeScore;

        int userAns, ans;

        boolean isCorrect = false;

        numQuery = Integer.parseInt(br.readLine());

        for (int q = 0; q < numQuery; ++q) {
            st = new StringTokenizer(br.readLine(), " ");

            int cmd;
            cmd = Integer.parseInt(st.nextToken());

            switch (cmd) {
                case CMD_INIT:
                    usersolution.init();
                    isCorrect = true;
                    break;
                case CMD_HIRE:
                    mID = Integer.parseInt(st.nextToken());
                    mTeam = Integer.parseInt(st.nextToken());
                    mScore = Integer.parseInt(st.nextToken());
                    usersolution.hire(mID, mTeam, mScore);
                    break;
                case CMD_FIRE:
                    mID = Integer.parseInt(st.nextToken());
                    usersolution.fire(mID);
                    break;
                case CMD_UPDATE_SOLDIER:
                    mID = Integer.parseInt(st.nextToken());
                    mScore = Integer.parseInt(st.nextToken());
                    usersolution.updateSoldier(mID, mScore);
                    break;
                case CMD_UPDATE_TEAM:
                    mTeam = Integer.parseInt(st.nextToken());
                    mChangeScore = Integer.parseInt(st.nextToken());
                    usersolution.updateTeam(mTeam, mChangeScore);
                    break;
                case CMD_BEST_SOLDIER:
                    mTeam = Integer.parseInt(st.nextToken());
                    userAns = usersolution.bestSoldier(mTeam);
                    ans = Integer.parseInt(st.nextToken());
                    if (userAns != ans) {
                        isCorrect = false;
                    }
                    break;
                default:
                    isCorrect = false;
                    break;
            }
        }

        return isCorrect;
    }

    public static void main(String[] args) throws Exception {
        int TC, MARK;

        //System.setIn(new java.io.FileInputStream("res/sample_input.txt"));
        System.setIn(new FileInputStream("res/input_pro_13072.txt"));

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine(), " ");

        TC = Integer.parseInt(st.nextToken());
        MARK = Integer.parseInt(st.nextToken());

        for (int testcase = 1; testcase <= TC; ++testcase) {
            int score = run(br) ? MARK : 0;
            System.out.println("#" + testcase + " " + score);
        }

        br.close();
    }
}

class UserSolution {

    static class Soldier implements Comparable<Soldier> {
        int mId, mTeam, mScore;

        public Soldier(int mId, int mTeam, int mScore) {
            this.mId = mId;
            this.mTeam = mTeam;
            this.mScore = mScore;
        }

        public void setScore(int mScore) {
            this.mScore = mScore;
        }

        public int getScore() {
            return mScore;
        }

        public int getId() {
            return mId;
        }

        @Override
        public int compareTo(Soldier o) {
            return mScore == o.getScore() ? Integer.compare(o.mId, mId) : Integer.compare(o.mScore, mScore);
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof Soldier)) return false;
            Soldier soldier = (Soldier) o;
            return mId == soldier.mId && mTeam == soldier.mTeam && mScore == soldier.mScore;
        }
    }

    ArrayList<Soldier>[] teams;
    // mID로 Soldier 조회를 위한 Map
    Map<Integer, Soldier> idSoldierMap;
    // mID로 mTeam 조회 위한 Map
    Map<Integer, Integer> idTeamMap;

    // 테스트 케이스의 시작 시 고용된 병사는 없다.
    public void init() {
        teams = new ArrayList[6];
        for (int i = 1; i < 6; i++) {
            teams[i] = new ArrayList<>();
        }
        idTeamMap = new HashMap<>();
        idSoldierMap = new HashMap<>();
    }

    /**
     * 병사 고용
     */
    public void hire(int mID, int mTeam, int mScore) {
        Soldier soldier = new Soldier(mID, mTeam, mScore);

        teams[mTeam].add(soldier);
        idTeamMap.put(mID, mTeam);
        idSoldierMap.put(mID, soldier);
    }

    /**
     * 병사 해고
     */
    public void fire(int mID) {
        int mTeam = idTeamMap.get(mID);
        Soldier soldier = idSoldierMap.get(mID);

        teams[mTeam].remove(soldier);
        idTeamMap.remove(mID);
        idSoldierMap.remove(mID);
    }

    /**
     * 병사의 평판 점수 변경
     */
    public void updateSoldier(int mID, int mScore) {
        Soldier soldier = idSoldierMap.get(mID);
        soldier.setScore(mScore);
    }

    /**
     * 특정 팀에 속한 병사들의 평판 점수를 일괄 변경
     */
    public void updateTeam(int mTeam, int mChangeScore) {
        for (Soldier soldier : teams[mTeam]) {
            int sum = soldier.getScore() + mChangeScore;
            if (sum > 5) soldier.setScore(5);
            else if (sum < 1) soldier.setScore(1);
            else soldier.setScore(sum);
        }
    }

    /**
     * 특정 팀 안에서 가장 평판 점수가 높은 병사를 검색
     */
    public int bestSoldier(int mTeam) {
        ArrayList<Soldier> team = teams[mTeam];
        Collections.sort(team);
        return team.get(0).getId();
    }

    private void debug() {
        for (int i = 1; i < 6; i++) {
            System.out.println("teams" + i);
            System.out.println(teams[i]);
        }
        System.out.println();
    }
}