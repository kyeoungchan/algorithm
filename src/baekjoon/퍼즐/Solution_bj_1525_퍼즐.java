package baekjoon.퍼즐;

import java.io.*;
import java.util.*;

public class Solution_bj_1525_퍼즐 {

    final static int GOAL_HASH = 123456780;

/*
    static class Node {
        int hash, count;

        public Node(int hash, int count) {
            this.hash = hash;
            this.count = count;
        }
    }
*/

    static int[] tenPower = {1, 10, 100, 1_000, 10_000, 100_000, 1_000_000, 10_000_000, 100_000_000, 1_000_000_000};
    static boolean[] check = new boolean[876_543_211]; // 해시는 12345678 ~ 876543210까지 가능하다.

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        int hash = 0;
        for (int i = 0; i < 3; i++) {
            st = new StringTokenizer(br.readLine(), " ");
            for (int j = 0; j < 3; j++) {
                hash *= 10;
                hash += Integer.parseInt(st.nextToken());
            }
        }

        check[hash] = true;
//        ArrayDeque<Node> q = new ArrayDeque<>();
        ArrayDeque<int[]> q = new ArrayDeque<>();
        q.offer(new int[] {hash, 0});

        int answer = -1; // 이동이 불가능한 경우 -1 출력
        while (!q.isEmpty()) {
//            Node cur = q.poll();
            int[] cur = q.poll();
            if (GOAL_HASH == cur[0]) {
                answer = cur[1];
                break;
            }

            int zeroPos = getZeroPos(cur[0]);
            int switchPos = zeroPos + 3;
            if (switchPos < 10) {
                // 위 숫자랑 바꾸는 게 말이 될 때
                /*
                 9 8 7
                 6 5 4
                 3 2 1
                 이것이 내가 구한 자릿수의 위치
                 0의 자리수가 1~6까지는 위에 숫자랑 위치를 옮기는 것이 가능하다.
                 */
                swap(zeroPos, switchPos, cur, q);
            }

            switchPos = zeroPos - 3;
            if (switchPos > 0) {
                // 아래 숫자와 바꾸는 게 말이 될 때
                swap(zeroPos, switchPos, cur, q);
            }

            switchPos = zeroPos - 1;
            if (switchPos % 3 != 0) {
                // 오른쪽 숫자와 바꿀 수 있다면, 즉, 1, 4, 7이 아니라면
                swap(zeroPos, switchPos, cur, q);
            }

            if (zeroPos % 3 != 0) {
                // 왼쪽 숫자와 바꿀 수 있다면, 즉, 0의 위치가 3, 6, 9가 아니라면
                switchPos = zeroPos + 1;
                swap(zeroPos, switchPos, cur, q);
            }
        }

        System.out.println(answer);
        br.close();
    }

    static void swap(int zeroPos, int switchPos, int[] cur, ArrayDeque<int[]> q) {
        int switchVal = (cur[0] % tenPower[switchPos]) / tenPower[switchPos - 1];
        int newHash;
        if (zeroPos < switchPos) {
            newHash = generateHash(switchPos, 0, zeroPos, switchVal, cur);
        } else {
            newHash = generateHash(zeroPos, switchVal, switchPos, 0, cur);
        }

//        System.out.println("newHash = " + newHash);
        if (!check[newHash]) {
            check[newHash] = true;
            q.offer(new int[] {newHash, cur[1] + 1});
        }
    }

    static int generateHash(int firstPos, int firstVal, int secondPos, int secondVal, int[] cur) {
/*
        System.out.println("generateHash");
        System.out.println("firstPos = " + firstPos);
        System.out.println("firstVal = " + firstVal);
        System.out.println("secondPos = " + secondPos);
        System.out.println("secondVal = " + secondVal);
        System.out.println("cur.hash = " + cur.hash);
*/
        int res = 0;
        // firstPos보다 앞의 값 생성
        // 예를 들어, 523에서 500으로 만들고 싶다면 523 / 100 * 100을 하면 된다.
        res += cur[0] / tenPower[firstPos] * tenPower[firstPos];
//        System.out.println("res = " + res);

        // firstPos에 firstVal 넣기
        // firstPos가 2고, 둘째자리에 4를 넣고싶다면, 즉, 40을 넣고 싶다면 4 * temPower[1]
        res += firstVal * tenPower[firstPos - 1];
//        System.out.println("res = " + res);

        // 중간 숫자들 집어넣기
        // 예를 들어, firstPos가 5고, secondPos가 2라면 *****00** 이런 형태로 3,4자리에만 숫자가 들어가야한다.
        // 그러면 hash를 10,000(tenPower[firstPos-1])에 %연산을 해주고, 그 값에 다시 / 100(tenPower[secondPos]) 연산을 해주고,
        // 다시 * 100(temPoser[secondPos])를 해줘야한다.
        res += cur[0] % tenPower[firstPos - 1] / tenPower[secondPos] * tenPower[secondPos];
//        System.out.println("res = " + res);

        // secondPos에 secondVal 넣기
        res += secondVal * tenPower[secondPos - 1];
//        System.out.println("res = " + res);

        // secondPos 이후의 자릿수 숫자들 다 집어넣기
        // 예를 들어, secondPos가 2라면, 1자리의 숫자를 집어넣으면 된다.
        res += cur[0] % tenPower[secondPos - 1];
//        System.out.println("res = " + res);

//        System.out.println();
        return res;
    }

    static int getZeroPos(int hash) {
        int res = 0;
        for (int i = 0; i < 9; i++) {
            res++;
            if (hash % 10 == 0) {
                return res;
            }
            hash /= 10;
        }
        System.out.println("Impossible!!!");
        throw new RuntimeException("Impossible!!!");
    }
}
