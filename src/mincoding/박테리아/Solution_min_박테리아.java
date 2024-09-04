package mincoding.박테리아;

import java.util.*;

/**
 * 박테리아는 N종류
 * 보관을 시작하는 시각부터 halfTime 시간이 경과할 때마다 생명력이 절반으로 준다.
 * 생명력에 소수점은 버린다.
 * 생명력이 9 이하면 폐기처리된다.
 * 박테리아 반출이 요청되면 박테리아 중에서 생명력이 가장 작은 박테리아가 반출된다.
 */
public class Solution_min_박테리아 {

    static class Bacteria {
        String name;
        int halfTime;

        public Bacteria(String name, int halfTime) {
            this.name = name;
            this.halfTime = halfTime;
        }
    }

    private Map<String, Integer> nameToIdx = new HashMap<>();
    private Bacteria[] bacteriaDict;

    /**
     * 현재 시각은 0
     * @param N 박테리아의 종류 1<=N<=100
     * @param bNameList 박테리아의 이름 길이: 3~9. \0으로 끝난다.
     * @param mHalfTime 박테리아의 생명력이 절반이 되는 시간
     */
    public void init(int N, char bNameList[][], int mHalfTime[]) {
        nameToIdx.clear();
        bacteriaDict = new Bacteria[N];
        for (int i = 0; i < N; i++) {
            String name = getName(bNameList[i]);
            nameToIdx.put(name, i);
            bacteriaDict[i] = new Bacteria(name, mHalfTime[i]);
        }
    }

    private String getName(char[] nameArr) {
        StringBuilder sb = new StringBuilder();
        for (char c : nameArr) {
            if (c == '\0') break;
            sb.append(c);
        }
        return sb.toString();
    }

    /**
     *
     * @param tStamp 박테리아를 연구소에 보관하는 시각 1 <= tStamp <= 1_000_000
     * @param bName 박테리아의 이름
     * @param mLife 박테리아의 생명력 100 <= mLife <= 50_000
     * @param mCnt 박테리아의 개수 1 <= mCnt <= 1_000
     */
    void addBacteria(int tStamp, char bName[], int mLife, int mCnt) {
        return;
    }

    /**
     * 생명력이 가장 작은 순서대로 박테리아 mCnt개를 반출한다. 그리고 생명력 합 반환
     * 만약 보관 중인 박테리아의 개수가 mCnt개 미만이면 보관 중인 박테리아 모두 반출
     * 생명력이 같은 박테리아가 너무 많으면 보관 시각이 먼저인 박테리아를 우선 반출한다.
     * tStamp 시각에 생명력 9 이하 박테리아는 이미 폐기처분된 상태다.
     * 연구소에 박테리아가1개 이상 있음이 보장된다.
     * @param tStamp 박테리아의 반출 시각 1 <= tStamp <= 1_000_000
     * @param mCnt 박테리아의 반출 개수 1 <= mCnt <= 100
     * @return 반출하는 박테리아의 생명력 합
     * 최대 15,000번 호출
     */
    int takeOut(int tStamp, int mCnt) {
        return -1;
    }

    /**
     * 반환값이 0일 수 있다.
     * @param tStamp
     * @param bName
     * @return 연구소에 남아있는 bName 박테리아의 개수
     * 최대 15,000번 호출
     */
    int checkBacteria(int tStamp, char bName[]) {
        return -1;
    }
}
