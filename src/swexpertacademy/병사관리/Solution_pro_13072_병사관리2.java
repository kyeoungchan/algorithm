package swexpertacademy.병사관리;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/**
 * 병사들은 각각 고유번호, 소속팀, 평판 점수를 가지고 있다.
 */
public class Solution_pro_13072_병사관리2 {
    private final static int CMD_INIT = 1;
    private final static int CMD_HIRE = 2;
    private final static int CMD_FIRE = 3;
    private final static int CMD_UPDATE_SOLDIER = 4;
    private final static int CMD_UPDATE_TEAM = 5;
    private final static int CMD_BEST_SOLDIER = 6;

    private final static UserSolution2 usersolution = new UserSolution2();

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

class UserSolution2 {

    static class Soldier {
        int id, version;
        Soldier next;

        public void setStatus(int id, int version, Soldier next) {
            this.id = id;
            this.version = version;
            this.next = next;
        }
    }

    static class Team {
        // 각 팀마다 1~5점 병사들
        Soldier[] head = new Soldier[6];
        Soldier[] tail = new Soldier[6];
    }

    Soldier[] soldiers = new Soldier[200_026];
    int[] versions = new int[100_001];
    int[] teamNumbers = new int[100_001];

    // 1~5번팀
    Team[] teams = new Team[6];

    int idx;

    public void init() {
        idx = 0;
        for (int i = 0; i < 200_026; i++)
            if(soldiers[i] == null) soldiers[i] = new Soldier();

        for (int i = 1; i < 6; i++) {
            teams[i] = new Team();
            for (int j = 1; j < 6; j++) {
                teams[i].head[j] = getNewSoldier(0, null);
                teams[i].tail[j] = teams[i].head[j];
            }
        }

        ///////////////
/*
        for (int i = 0; i < 100_001; i++) {
            versions[i] = 0;
            teamNumbers[i] = 0;
        }
*/
        ////////////////
    }

    private Soldier getNewSoldier(int id, Soldier next) {
        Soldier resultSoldier = soldiers[idx++];
        resultSoldier.setStatus(id, ++versions[id], next);
        return resultSoldier;
    }

    public void hire(int mID, int mTeam, int mScore) {
        Soldier soldier = getNewSoldier(mID, null);
        Team team = teams[mTeam];
        team.tail[mScore].next = soldier;
        team.tail[mScore] = soldier;

        teamNumbers[mID] = mTeam;
    }

    public void fire(int mID) {
        versions[mID] = -1;
    }

    public void updateSoldier(int mID, int mScore) {
        hire(mID, teamNumbers[mID], mScore);
    }

    public void updateTeam(int mTeam, int mChangeScore) {
        Soldier[] head = teams[mTeam].head;
        Soldier[] tail = teams[mTeam].tail;
        if (mChangeScore < 0) {
            for (int originScore = 2; originScore < 6; originScore++) {
                moveScoreInTeam(mChangeScore, head, tail, originScore);
            }
        } else if (mChangeScore > 0) {
            for (int originScore = 4; originScore > 0; originScore--) {
                moveScoreInTeam(mChangeScore, head, tail, originScore);
            }
        }
        // mChangeScore가 0이면 아무런 변화도 일어나지 않는다.
    }

    private void moveScoreInTeam(int mChangeScore, Soldier[] head, Soldier[] tail, int originScore) {
        if (head[originScore].next == null) return;

        int newScore = originScore + mChangeScore;
        newScore = newScore < 1 ? 1 : Math.min(newScore, 5);
        if (newScore == originScore) return;

        tail[newScore].next = head[originScore].next;
        tail[newScore] = tail[originScore];
        head[originScore].next = null;
        tail[originScore] = head[originScore];
    }

    public int bestSoldier(int mTeam) {
        Soldier[] head = teams[mTeam].head;
        for (int score = 5; score > 0; score--) {
            if (head[score].next == null) continue;
            int answer = 0;
            Soldier soldier = head[score].next;
            while (soldier != null) {
                if (soldier.version == versions[soldier.id] && soldier.id > answer) answer = soldier.id;
                soldier = soldier.next;
            }
            ///////////////////
            if (answer != 0)
                // 지워진 병사들만 있는 리스트가 있을 수 있으므로 answer가 0인지 확인을 해야한다.
                //////////////////
                return answer;
        }
        return 0;
    }
}