package swexpertacademy.계산게임;

import java.util.*;

/**
 * 1~30까지의 카드
 * 조커카드: -1로 표현
 * 주어지는 카드 수: 5장
 * 계산
 * - mNum과 mNth가 주어질 때, 선택한 카드와 오른쪽에 인접한 3개의 카드(즉, 총 4장의 카드들)의 합을 20으로 나눴을 때의 나머지를 구해서 mNum과 같은지 확인한다.
 * - 찾으면 인접한 4장의 카드들을 알린다.
 */
public class Solution_pro_14611_계산게임2 {

    final int MAX_PUT_CARD_CNT = 50_000;
    final int INIT_PUT_CARD_CNT = 5;
    final int MOD = 20;

    int joker, startIdx, endIdx;
    int[] cards;
    List<Integer>[][] cardIdx = new List[MOD][MOD];
    // row 정보: 조커의 값. col 정보: 찾고자하는 mNum

    /**
     * 나열할 5장의 카드 정보가 주어진다.
     * @param mJoker   조커 값
     * @param mNumbers 5장의 카드 정보
     */
    void init(int mJoker, int mNumbers[]) {
        joker = mJoker % 20;
        startIdx = endIdx = MAX_PUT_CARD_CNT;
        // 좌에 50,000개의 카드를 놓을 수 있도록 첫 인덱스를 세팅한다.
        cards = new int[MAX_PUT_CARD_CNT * 2 + INIT_PUT_CARD_CNT];

        for (int i = 0; i < MOD; i++)
            for (int j = 0; j < MOD; j++)
                cardIdx[i][j] = new LinkedList<>();

        putCards(1, mNumbers);
    }

    /**
     * 테이블에 나열할 5장의 카드 정보가 주어진다.
     * @param mDir 0이면 왼쪽에, 1이면 오른쪽에 나열한다.
     * @param mNumbers 5장의 카드 정보
     * 호출 횟수: 10_000회 이하
     */
    void putCards(int mDir, int mNumbers[]) {
        // 0이면 왼쪽에, 1이면 오른쪽에 나열한다.
        if (mDir == 1) {
            for (int i = 0; i < 5; i++)
                cards[i + endIdx] = mNumbers[i];
            putCardIdxRight();
            endIdx += 5;
        } else {
            startIdx -= 5;
            for (int i = 0; i < 5; i++)
                cards[i + startIdx] = mNumbers[i];
            putCardIdxLeft();
        }
    }

    private void putCardIdxRight() {
        // 셈을 하는 카드는 4장이므로 endIdx-3 ~ endIdx+1까지를 탐색한다.
        for (int i = endIdx - 3; i <= endIdx + 1; i++) {
            if (i < startIdx) continue;
            int jokerCnt = 0, sum = 0;
            for (int j = 0; j < 4; j++) {
                if (cards[i + j] == -1) jokerCnt++;
                else sum += cards[i + j];
            }
            for (int tempJoker = 0; tempJoker < MOD; tempJoker++) {
                int value = (tempJoker * jokerCnt + sum) % MOD;
                cardIdx[tempJoker][value].add(i);
            }
        }
    }

    private void putCardIdxLeft() {
        for (int i = startIdx + 4; i >= startIdx; i--) {
            int jokerCnt = 0, sum = 0;
            for (int j = 0; j < 4; j++) {
                if (cards[i + j] == -1) jokerCnt++;
                else sum += cards[i + j];
            }
            for (int tempJoker = 0; tempJoker < MOD; tempJoker++) {
                int value = (tempJoker * jokerCnt + sum) % MOD;
                cardIdx[tempJoker][value].add(0, i);
            }
        }
    }

    /**
     * 계산을 한다.
     * @param mNum 20으로 나눈 나머지 결과
     * @param mNth 번째 카드
     * @param ret 4장의 카드
     * @return 찾으면 1을, 조건에 맞는 카드들을 찾지 못했으면 0을 반환한다.
     * 호출 횟수: 15_000회 이하
     */
    int findNumber(int mNum, int mNth, int ret[]) {
        if (cardIdx[joker][mNum].size() < mNth) return 0;
        int idx = cardIdx[joker][mNum].get(mNth - 1);
        for (int i = 0; i < 4; i++)
            ret[i] = cards[idx + i];
        return 1;
    }

    /**
     * 조커 값을 변경한다.
     */
    void changeJoker(int mValue) {
        joker = mValue % 20;
    }
}
