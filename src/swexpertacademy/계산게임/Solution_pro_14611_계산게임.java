package swexpertacademy.계산게임;

import java.util.*;

public class Solution_pro_14611_계산게임 {

    private static final int MAX_CARD = 50_000;

    static class Table {
        int joker;
        int begin, end;
        int[] cards = new int[MAX_CARD * 2 + 5];
        List<Integer>[][] idxList = new List[20][20];
        /* idxList[joker][score]: 조커의 점수가 joker일 때, 점수가 score인 인덱스 리스트
         * 예를 들어, 현재 joker = 9점이고, findNumber의 타겟 점수가 19점이라면,
         * 우리는 idxList[9][19]를 통해 조회할 수 있다.*/

        // 초기 5장과 조커로 초기화하기
        public void init(int mJoker, int[] mNumbers) {
            joker = mJoker % 20; // 조커 점수를 20으로 나눈 나머지로 초기화하기
            begin = end = MAX_CARD; // 카드들의 처음, 끝 위치 초기화하기

            for (int i = 0; i < 20; i++)
                for (int j = 0; j < 20; j++)
                    // ArrayList로 하면 시간초과가 뜬다!!!! LinkedList로 해야 통과됨
                    idxList[i][j] = new LinkedList<>();

            for (int i = 0; i < 5; i++)
                cards[end + i] = mNumbers[i];
            end += 5;

            for (int i = 0; i < 2; i++)
                update(MAX_CARD + i, 1);

        }

        /**
         * [idx ~ (idx + 3)]의 네 장의 점수를 mDir 방향에 추가한다.
         */
        public void update(int idx, int mDir) {
            int sum = 0;
            int jokerCnt = 0;
            for (int i = 0; i < 4; i++) {
                if (cards[idx + i] == -1)
                    jokerCnt++;
                else
                    sum += cards[idx + i];
            }

            // 조커 점수가 i라고 가정하자.
            for (int i = 0; i < 20; i++) {
                int num = (sum + jokerCnt * i) % 20;
                if (mDir == 0)
                    // 왼쪽
                    idxList[i][num].add(0, idx);
                else
                    // 오른쪽
                    idxList[i][num].add(idx);
            }
        }

        public void pushFront(int[] mNumbers) {
            // 새로운 5장을 왼쪽에 넣기
            begin -= 5;
            for (int i = 0; i < 5; i++)
                cards[begin + i] = mNumbers[i];
            int target = begin;
            for (int i = 4; i >= 0; i--)
                update(target + i, 0);
        }

        public void pushBack(int[] mNumbers) {
            for (int i = 0; i < 5; i++)
                cards[end + i] = mNumbers[i];
            int target = end - 3;
            end += 5;
            for (int i = 0; i < 5; i++)
                update(target + i, 1);
        }

        public int find(int mNum, int mNth, int[] ret) {
            List<Integer> list = idxList[joker][mNum];
            if (mNth > list.size())
                return 0;
            int idx = list.get(mNth - 1);
            for (int i = 0; i < 4; i++)
                ret[i] = cards[idx + i];
            return 1;
        }

        public void changeJoker(int mJoker) {
            joker = mJoker % 20;
        }
    }

    private Table t = new Table();

    /**
     * 처음 테이블에 나열할 카드 5장의 정보가 mNumbers 배열로 주어진다.
     * 조커값이 주어진다.
     */
    void init(int mJoker, int mNumbers[]) {
        t.init(mJoker, mNumbers);
    }

    /**
     * 테이블에 나열할 5장의 카드 정보
     * 왼쪽에서 오른쪽 방향
     * mDir: 0 => 왼쪽에
     * mDir: 1 => 오른쪽에
     * 호출 횟수: 10,000 이하
     */
    void putCards(int mDir, int mNumbers[]) {
        if (mDir == 0) t.pushFront(mNumbers);
        else t.pushBack(mNumbers);
    }

    /**
     * 호출 횟수: 15,000 이하
     * 카드의 계산 결과가 mNum인 mNth 번째 카드를 찾고
     * 그 카드와 오른쪽 3개의 카드에 적힌 수를 ret에 기록한다.
     * 조건에 맞는 카드를 찾을 수 있는 경우 1 반환
     * 조건에 맞는 카드를 찾을 수 없는 경우 0 반환
     * 0을 반환하는 경우 ret 배열은 무시해도 된다.
     */
    int findNumber(int mNum, int mNth, int ret[]) {
        return t.find(mNum, mNth, ret);
    }

    void changeJoker(int mValue) {
        t.changeJoker(mValue);
    }
}
