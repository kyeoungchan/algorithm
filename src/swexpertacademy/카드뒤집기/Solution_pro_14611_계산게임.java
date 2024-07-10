package swexpertacademy.카드뒤집기;

import java.util.*;

public class Solution_pro_14611_계산게임 {

    ArrayDeque<Integer> q;
    int jocker, cnt;
    final int MOD = 20;

    /**
     * 처음 테이블에 나열할 카드 5장의 정보가 mNumbers 배열로 주어진다.
     * 조커값이 주어진다.
     */
    void init(int mJoker, int mNumbers[]) {
        q = new ArrayDeque<>();
        jocker = mJoker;
        for (int i = 0; i < 5; i++)
            q.offer(mNumbers[i]);
        cnt = 1;
    }

    /**
     * 테이블에 나열할 5장의 카드 정보
     * 왼쪽에서 오른쪽 방향
     * mDir: 0 => 왼쪽에
     * mDir: 1 => 오른쪽에
     */
    void putCards(int mDir, int mNumbers[]) {
        if (mDir == 0) {
            for (int i = 4; i > -1; i--)
                q.push(mNumbers[i]);
        } else {
            for (int i = 0; i < 5; i++)
                q.offer(mNumbers[i]);
        }
        cnt++;
    }

    int findNumber(int mNum, int mNth, int ret[]) {
        int slidingCnt = cnt * 5 - 3;
        if (mNth > slidingCnt)
            // 한 바퀴 다 돌려도 mNth에 도달할 수 없다면 0 반환
            return 0;
        int sum = 0, index = -1;
        int[] memo = new int[4];
        for (int i = 0; i < 4; i++) {
            // 일단 4개를 꺼내서 집어넣는다.
            index++;
            memo[index] = q.poll();
            if (memo[index] == -1)
                sum += jocker;
            else
                sum += memo[index];
            q.offer(memo[index]);
        }
        slidingCnt--;

        if (sum % MOD == mNum) {
            mNth--;
            if (mNth == 0) {
                for (int i = 0, j = 1; i < 4; i++) {
                    ret[i] = memo[(index + j) % 4];
                    j++;
                }
            }
        }

        while (slidingCnt > 0) {
            slidingCnt--;

            index = (index + 1) % 4;
            if (memo[index] == -1)
                sum -= jocker;
            else
                sum -= memo[index];

            memo[index] = q.poll();

            if (memo[index] == -1)
                sum += jocker;
            else
                sum += memo[index];

            q.offer(memo[index]);
            if (sum % MOD == mNum) {
                mNth--;
                if (mNth == 0) {
                    for (int i = 0, j = 1; i < 4; i++) {
                        ret[i] = memo[(index + j) % 4];
                        j++;
                    }
                }
            }
        }

        if (mNth <= 0) {
            //  한 바퀴 다 도는 동안 mNth를 찾았다면 1반환
            return 1;
        }
        // 한 바퀴 다 돌아도 mNth를 못 찾았다면
        return 0;
    }

    void changeJoker(int mValue) {
        jocker = mValue;
    }
}
