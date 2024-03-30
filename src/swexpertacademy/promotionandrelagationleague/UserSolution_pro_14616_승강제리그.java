package swexpertacademy.promotionandrelagationleague;

import java.util.Arrays;
import java.util.Comparator;

public class UserSolution_pro_14616_승강제리그 {

    private int L, mAbility[], memberCnt;
    private Integer[][] leagues;

    void init(int N, int L, int mAbility[]) {
//        this.N = N;
        this.L = L;
        this.mAbility = mAbility;

        memberCnt = N / L;
        leagues = new Integer[L][memberCnt];
        int last = memberCnt - 1;

        for (int i = 0; i < N; i++) {
            int group = i / (memberCnt);
            int pos = i % (memberCnt);
            leagues[group][pos] = i;
            if (pos == last) {
                Arrays.sort(leagues[group], Comparator.comparingInt((Integer m) -> -mAbility[m]).thenComparing(m -> m));
            }
        }
    }

    int move() {
        int last = memberCnt - 1;
        return process(last);

    }

    int trade() {
        int middleIdx = memberCnt / 2;
        return process(middleIdx);
    }

    private int process(int preIdx) {
        int idSum = 0;
        for (int leagueNum = 1; leagueNum < L; leagueNum++) {
            int tempId = leagues[leagueNum][0];
            idSum += tempId;

            int beforeLeague = leagueNum - 1;
            int higherId = leagues[beforeLeague][preIdx];
            idSum += higherId;

            leagues[leagueNum][0] = higherId;
            leagues[beforeLeague][preIdx] = tempId;

            Arrays.sort(leagues[beforeLeague], Comparator.comparingInt((Integer m) -> -mAbility[m]).thenComparing(m -> m));
        }
        Arrays.sort(leagues[L - 1], Comparator.comparingInt((Integer m) -> -mAbility[m]).thenComparing(m -> m));
        return idSum;
    }
}
