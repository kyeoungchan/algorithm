package swexpertacademy.병사관리;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

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
    static class Soldier {
        int id;
        int version;
        Soldier next;
    }

    static class Team {
        Soldier[] head = new Soldier[6];
        Soldier[] tail = new Soldier[6];
    }

    // 각 병사들을 담는 배열
    Soldier[] soldiers = new Soldier[200_026];
    // soldiers 배열 포인터
    int idx;
    // 병사들의 버전을 관리하는 배열
    int[] versions = new int[100_001];
    // 병사들의 팀번호를 저장하는 배열
    int[] teamNumbers = new int[100_001];
    // 1~5번팀마다 1~5점의 병사 집단들을 관리하는데, 각 점수마다 병사 집단의 head와 tail만 담고 있다.
    Team[] teams = new Team[6];

    public void init() {
        idx = 0;
        for (int i = 0; i < 200_026; i++) {
            // 각 테케마다 일일이 다 새로 만들 필요는 없으므로 if문 사용
            if (soldiers[i] == null) soldiers[i] = new Soldier();
        }

        for (int i = 1; i < 6; i++) {
            // 1~5번팀
            teams[i] = new Team();
            for (int j = 1; j < 6; j++) {
                // 각 팀의 점수마다 초기는 id가 0인 Soldier 노드가 들어가있다.
                teams[i].tail[j] = teams[i].head[j] = getNewSoldier(0, null);
            }
        }

        for (int i = 0; i <= 100_000; i++) {
            versions[i] = 0;
            teamNumbers[i] = 0;
        }
    }

    private Soldier getNewSoldier(int id, Soldier next) {
        Soldier soldier = soldiers[idx++];
        soldier.id = id;
        soldier.next = next;
        soldier.version = ++versions[id];
        return soldier;
    }

    public void hire(int mID, int mTeam, int mScore) {
        Soldier newSoldier = getNewSoldier(mID, null);
        teams[mTeam].tail[mScore].next = newSoldier;
        teams[mTeam].tail[mScore] = newSoldier;
        teamNumbers[mID] = mTeam;
    }

    public void fire(int mID) {
        versions[mID] = -1;
    }

    public void updateSoldier(int mID, int mScore) {
        hire(mID, teamNumbers[mID], mScore);
    }

    public void updateTeam(int mTeam, int mChangeScore) {
        if (mChangeScore < 0) {
            // 바뀌는 점수가 음수라면 팀의 이동이 5점 -> 1점 방향으로 이동한다.
            // 따라서 2점부터 5점까지 점수 순서대로 병사들을 업데이트해주면 된다.
            // 1점은 이미 더이상 내려갈 곳이 없으므로 관리 안해줘도 된다.
            for (int originScore = 2; originScore < 6; originScore++) {
                int newScore = originScore + mChangeScore;
                newScore = newScore < 1 ? 1 : (newScore > 5 ? 5 : newScore);
                if (originScore == newScore) continue;

                // 비어있는 점수대면 넘어간다. 다시 한 번 강조하지만 head[]는 null이 항상 아니다.
                if (teams[mTeam].head[originScore].next == null) continue;
                teams[mTeam].tail[newScore].next = teams[mTeam].head[originScore].next;
                teams[mTeam].tail[newScore] = teams[mTeam].tail[originScore];

                teams[mTeam].head[originScore].next = null;
                teams[mTeam].tail[originScore] = teams[mTeam].head[originScore];
            }
        } else if (mChangeScore > 0) {
            for (int originScore = 4; originScore > 0; originScore--) {
                int newScore = originScore + mChangeScore;
                newScore = newScore < 1 ? 1 : (newScore > 5 ? 5 : newScore);
                if (originScore == newScore) continue;

                if (teams[mTeam].head[originScore].next == null) continue;
                teams[mTeam].tail[newScore].next = teams[mTeam].head[originScore].next;
                teams[mTeam].tail[newScore] = teams[mTeam].tail[originScore];

                teams[mTeam].head[originScore].next = null;
                teams[mTeam].tail[originScore] = teams[mTeam].head[originScore];
            }
        }
    }

    public int bestSoldier(int mTeam) {
        for (int score = 5; score > 0; score--) {
            Soldier soldier = teams[mTeam].head[score].next;
            if (soldier == null) continue;

            int resultId = 0;
            while (soldier != null) {
                if (soldier.version == versions[soldier.id]) {
                    resultId = Math.max(resultId, soldier.id);
                }
                soldier = soldier.next;
            }
            if (resultId != 0) return resultId;
        }
        return 0;
    }
}