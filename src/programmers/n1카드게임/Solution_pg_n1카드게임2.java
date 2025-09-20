package programmers.n1카드게임;

import java.util.*;

public class Solution_pg_n1카드게임2 {
    public int solution(int coin, int[] cards) {
        Set<Integer> had = new HashSet<>();
        Set<Integer> notHad = new HashSet<>();

        int n = cards.length;

        int idx = n / 3;

        for (int i = 0; i < idx; i++) {
            had.add(cards[i]);
        }

        int answer = 0;

        next: while (true) {
            answer++;

            if (idx == n) {
                break;
            }


            notHad.add(cards[idx]);
            notHad.add(cards[idx + 1]);
            idx += 2;

            for (int c: had) {
                if (had.contains(n + 1 - c)) {
                    had.remove(c);
                    had.remove(n + 1 - c);
                    continue next;
                }
            }

            if (coin > 0) {
                for (int c: had) {
                    if (notHad.contains(n + 1 - c)) {
                        coin--;
                        had.remove(c);
                        notHad.remove(n + 1 - c);
                        continue next;
                    }
                }
            }


            if (coin > 1) {
                for (int c : notHad) {
                    if (notHad.contains(n + 1 - c)) {
                        notHad.remove(c);
                        notHad.remove(n + 1 - c);
                        coin -= 2;
                        continue next;
                    }
                }
            }

            break;
        }

        return answer;
    }
}
