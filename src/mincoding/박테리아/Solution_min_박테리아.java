package mincoding.박테리아;

import java.util.*;
import java.io.*;

/**
 * 박테리아는 N종류
 * 보관을 시작하는 시각부터 halfTime 시간이 경과할 때마다 생명력이 절반으로 준다.
 * 생명력에 소수점은 버린다.
 * 생명력이 9 이하면 폐기처리된다.
 * 박테리아 반출이 요청되면 박테리아 중에서 생명력이 가장 작은 박테리아가 반출된다.
 */
public class Solution_min_박테리아 {

    static class Bacteria implements Comparable<Bacteria> {
        String name;
        int initTime, halfTime, life, cnt, changeTime, idx;
        boolean gone;

        public void setData(String name, int initTime, int halfTime, int life, int cnt, int changeTime, int idx) {
            this.name = name;
            this.initTime = initTime;
            this.halfTime = halfTime;
            this.life = life;
            this.cnt = cnt;
            this.changeTime = changeTime;
            this.idx = idx;
            gone = false;
        }

        public boolean timeMovesAlive(int curTime) {
            if (curTime < changeTime) return true;
            int timeLoop = (curTime - changeTime) / halfTime + 1;
            changeTime += timeLoop * halfTime;
            for (int i = 0; i < timeLoop && life > 9; i++) life /= 2;
            return life > 9;
        }

        @Override
        public int compareTo(Bacteria o) {
            return Integer.compare(changeTime, o.changeTime);
        }
    }

    static class LifeInfo implements Comparable<LifeInfo> {
        int idx, life, initTime;

        public void setData(int idx, int life, int initTime) {
            this.idx = idx;
            this.life = life;
            this.initTime = initTime;
        }

        @Override
        public int compareTo(LifeInfo o) {
            return life == o.life ? Integer.compare(initTime, o.initTime) : Integer.compare(life, o.life);
        }
    }

    private Map<String, Integer> nameToIdx = new HashMap<>();
    private PriorityQueue<Bacteria> bacteriaPQ = new PriorityQueue<>();
    private int[] halfTimeInfo = new int[100], bacteriaCnt = new int[100];
    private Bacteria[] bacteriaArray = new Bacteria[20_001];
    private LifeInfo[] lifeInfoArray = new LifeInfo[20_001];
    private int bacteriaIdx, lifeInfoIdx;
    private PriorityQueue<LifeInfo> lifeInfoPQ = new PriorityQueue<>();

    /**
     * 현재 시각은 0
     * @param N 박테리아의 종류 1<=N<=100
     * @param bNameList 박테리아의 이름 길이: 3~9. \0으로 끝난다.
     * @param mHalfTime 박테리아의 생명력이 절반이 되는 시간
     */
    public void init(int N, char bNameList[][], int mHalfTime[]) {
        nameToIdx.clear();
        bacteriaCnt = new int[N];
        for (int i = 0; i < N; i++) {
            bacteriaCnt[i] = 0;
            String name = getName(bNameList[i]);
            nameToIdx.put(name, i);
            halfTimeInfo[i] = mHalfTime[i];
        }
        bacteriaPQ.clear();
        bacteriaIdx = 0;
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
     * @param tStamp 박테리아를 연구소에 보관하는 시각 1 <= tStamp <= 1_000_000
     * @param bName 박테리아의 이름
     * @param mLife 박테리아의 생명력 100 <= mLife <= 50_000
     * @param mCnt 박테리아의 개수 1 <= mCnt <= 1_000
     * 최대 20,000번 호출
     */
    void addBacteria(int tStamp, char bName[], int mLife, int mCnt) {
        timeMoves(tStamp);
        String name = getName(bName);
        int addingIdx = nameToIdx.get(name);
        int halfTime = halfTimeInfo[addingIdx];
        Bacteria bacteria = createBacteria(name, halfTime, tStamp, mLife, mCnt, tStamp + halfTime);
        bacteriaPQ.offer(bacteria);
        bacteriaCnt[addingIdx] += mCnt;
    }

    private void timeMoves(int curTime) {
        while (!bacteriaPQ.isEmpty() && bacteriaPQ.peek().changeTime <= curTime) {
            Bacteria bacteria = bacteriaPQ.poll();
            if (bacteria.gone) continue;
            if (!bacteria.timeMovesAlive(curTime)) {
                String name = bacteria.name;
                int idx = nameToIdx.get(name);
                bacteriaCnt[idx] -= bacteria.cnt;
            } else {
                bacteriaPQ.offer(bacteria);
            }
        }
    }

    private Bacteria createBacteria(String name, int halfTime, int initTime, int life, int cnt, int changeTime) {
        if (bacteriaArray[bacteriaIdx] == null) bacteriaArray[bacteriaIdx] = new Bacteria();
        bacteriaArray[bacteriaIdx].setData(name, initTime, halfTime, life, cnt, changeTime, bacteriaIdx);
        return bacteriaArray[bacteriaIdx++];
    }

    /**
     * 생명력이 가장 작은 순서대로 박테리아 mCnt개를 반출한다. 그리고 생명력 합 반환
     * 만약 보관 중인 박테리아의 개수가 mCnt개 미만이면 보관 중인 박테리아 모두 반출
     * 생명력이 같은 박테리아가 너무 많으면 보관 시각이 먼저인 박테리아를 우선 반출한다.
     * tStamp 시각에 생명력 9 이하 박테리아는 이미 폐기처분된 상태다.
     * 연구소에 박테리아가 1개 이상 있음이 보장된다.
     * @param tStamp 박테리아의 반출 시각 1 <= tStamp <= 1_000_000
     * @param mCnt 박테리아의 반출 개수 1 <= mCnt <= 100
     * @return 반출하는 박테리아의 생명력 합
     * 최대 15,000번 호출
     * 여기에서만 timeMoves 메서드를 호출하면서 동시에 PQ에 생명력 기준으로 담아준다.
     * 만약 한 종류의 박테리아를 소진하고 같은 종류의 박테리아가 남아있으면 또 담아준다.
     */
    int takeOut(int tStamp, int mCnt) {
        timeMovesBeforeTakeOut(tStamp);
        int result = 0;
        while (mCnt > 0 && !lifeInfoPQ.isEmpty()) {
            LifeInfo lifeInfo = lifeInfoPQ.poll();
            Bacteria target = bacteriaArray[lifeInfo.idx];
            if (target.gone) continue;
            if (target.cnt > mCnt) {
                target.cnt -= mCnt;
                bacteriaCnt[nameToIdx.get(target.name)] -= mCnt;
                result += mCnt * lifeInfo.life;
                mCnt = 0;
            } else {
                mCnt -= target.cnt;
                bacteriaCnt[nameToIdx.get(target.name)] -= target.cnt;
                target.gone = true;
                result += target.cnt * lifeInfo.life;
            }
        }
        return result;
    }

    private void timeMovesBeforeTakeOut(int curTime) {
        lifeInfoIdx = 0;
        PriorityQueue<Bacteria> newPq = new PriorityQueue<>();
        lifeInfoPQ.clear();
        while (!bacteriaPQ.isEmpty() && bacteriaPQ.peek().changeTime <= curTime) {
            Bacteria bacteria = bacteriaPQ.poll();
            if (bacteria.gone) continue;
            if (!bacteria.timeMovesAlive(curTime)) {
                String name = bacteria.name;
                int idx = nameToIdx.get(name);
                bacteriaCnt[idx] -= bacteria.cnt;
            } else {
//                bacteriaPQ.offer(bacteria);
                newPq.offer(bacteria);
                lifeInfoPQ.offer(createLifeInfo(bacteria));
            }
        }
        while (!bacteriaPQ.isEmpty()) {
            Bacteria bacteria = bacteriaPQ.poll();
            newPq.offer(bacteria);
            lifeInfoPQ.offer(createLifeInfo(bacteria));
        }
        bacteriaPQ = newPq;
    }

    private LifeInfo createLifeInfo(Bacteria bacteria) {
        if (lifeInfoArray[lifeInfoIdx] == null) lifeInfoArray[lifeInfoIdx] = new LifeInfo();
        lifeInfoArray[lifeInfoIdx].setData(bacteria.idx, bacteria.life, bacteria.initTime);
        return lifeInfoArray[lifeInfoIdx++];
    }


    /**
     * 반환값이 0일 수 있다.
     * @param tStamp
     * @param bName
     * @return 연구소에 남아있는 bName 박테리아의 개수
     * 최대 15,000번 호출
     */
    int checkBacteria(int tStamp, char bName[]) {
        timeMoves(tStamp);
        String name = getName(bName);
        return bacteriaCnt[nameToIdx.get(name)];
    }
}
