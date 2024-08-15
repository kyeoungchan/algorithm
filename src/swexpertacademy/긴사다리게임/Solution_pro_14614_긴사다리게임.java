package swexpertacademy.긴사다리게임;

import java.util.*;

/**
 * 긴 사다리 게임은 100명이 참가하는 사다리 게임이다.
 * 각 참가자는 1~100까지 고유 번호를 가진다.
 * 초기 맵에는 모든 세로줄이 그어져있다.
 * 초기 맵에는 가로줄이 없다.
 * mID번 참가자의 이동 방법
 * 1. (mID, 0)에서 출발한다.
 * 2. 가로줄과 세로줄이 만나는 지점이 나올 때까지, 세로줄을 따라 Y좌표가 증가하는 방향으로 이동한다. Y좌표는 최대 1_000_000_000
 * 3. 가로줄과 세로줄이 만나는 지점으로 도착하면, 해당 가로줄의 반대편 끝까지 이동한다.
 * 4. 2번과 3번 과정을 Y좌표의 최대에 도달할 때까지 반복한다.
 */
class Solution_pro_14614_긴사다리게임 {

    private Map<>

    public void init() {
    }

    /**
     * mX, mY와 mX+1, mY를 잇는 가로줄을 추가한다.
     * 1 ≤ mX ≤ 99
     * 1 ≤ mY ≤ 999,999,999
     * 200_000번 호출
     */
    public void add(int mX, int mY) {
    }

    /**
     * mX, mY와 mX+1, mY를 잇는 가로줄을 삭제한다.(가로줄이 존재함이 보장된다.)
     * 범위는 add와 동일
     * 5_000번 호출
     */
    public void remove(int mX, int mY) {
    }

    /**
     * 현재맵에서 사다리게임을 진행했을 때, mID번 참가자가 지나게 되는 가로줄의 개수를 반환한다.
     * mID번 참가자는 (mID, 0)에서 출발한다.
     * @return mID번 참가자가 지나게 되는 가로줄의 개수
     * 500번 호출
     */
    public int numberOfCross(int mID) {
        return 0;
    }

    /**
     * mX, mY를 지나게 되는 참가자의 번호를 반환한다.
     * mX, mY는 가로줄과 세로줄이 만나는 지점이 아님을 보장한다.
     * mX, mY를 지나는 참가자는 항상 존재하며, 유일하다.
     * 500번 호출
     */
    public int participant(int mX, int mY) {
        return 0;
    }
}
