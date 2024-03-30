package programmers.deliverypickup;

import java.util.*;
import java.io.*;

/*
일단 가장 멀리 있는 집에 택배를 보내는 과정이 선행되어야 한다.
그리고 트럭에는 상자가 없는 상태로 가장 멀리 있는 집의 상자를 수거해와야한다.
PQ를 사용해서, 인덱스와 처리해야하는 박스 정보를 관리하자.
PQ를 2개를 사용하고, 한 집에서 박스 처리가 덜되면 다시 PQ에 넣어주자. PQ를 사용하는 이유고, 다시 맨 앞으로 오게 된다.
2개의 PQ에서 꺼내온 인데스를 비교해서 가장 거리가 먼 집으로 한 번 갔다오면 꺼내온 값들은 모두 계산이 된다.
*/
public class Solution_pg_택배배달과수거하기 {

    public long solution(int cap, int n, int[] deliveries, int[] pickups) throws Exception {
        long answer = 0;
        PriorityQueue<int[]> dpq = new PriorityQueue<>(Comparator.comparingInt(o -> -o[0]));
        PriorityQueue<int[]> ppq = new PriorityQueue<>(Comparator.comparingInt(o -> -o[0]));
        for (int i = 0; i < n; i++) {
            int d = deliveries[i];
            int p = pickups[i];
            if (d != 0) dpq.offer(new int[]{i + 1, d});
            if (p != 0) ppq.offer(new int[]{i + 1, p});
        }
        while (!(dpq.isEmpty() && ppq.isEmpty())) {
            int dIdx = 0;
            int dCap = 0;
            int pIdx = 0;
            int pCap = 0;
            int[] dCur = dpq.poll();
            if (dCur != null) {
                dIdx = dCur[0];
                if (cap == dCur[1]) {
                    dCap = dCur[1];
                } else if (cap < dCur[1]) {
                    dCap = cap;
                    dpq.offer(new int[]{dIdx, dCur[1] - cap});
                } else {
                    dCap = dCur[1];
                    while (!dpq.isEmpty() && dCap < cap) {
                        int[] dNext = dpq.poll();
                        if (dCap + dNext[1] > cap) {
                            dpq.offer(new int[]{dNext[0], dNext[1] - cap + dCap});
                            dCap = cap;
                        } else {
                            dCap += dNext[1];
                        }
                    }
                }
            }
            int[] pCur = ppq.poll();
            if (pCur != null) {
                pIdx = pCur[0];
                if (cap == pCur[1]) {
                    pCap = pCur[1];
                } else if (cap < pCur[1]) {
                    pCap = cap;
                    ppq.offer(new int[]{pIdx, pCur[1] - cap});
                } else {
                    pCap = pCur[1];
                    while (!ppq.isEmpty() && pCap < cap) {
                        int[] pNext = ppq.poll();
                        if (pCap + pNext[1] > cap) {
                            ppq.offer(new int[]{pNext[0], pNext[1] - cap + pCap});
                            pCap = cap;
                        } else {
                            pCap += pNext[1];
                        }
                    }
                }
            }
            int far = Math.max(dIdx, pIdx);
            answer += far * 2;
        }
        return answer;
    }

    public static void main(String[] args) throws Exception {
        Solution_pg_택배배달과수거하기 instance = new Solution_pg_택배배달과수거하기();
        System.out.println(instance.solution(4, 5, new int[]{1, 0, 3, 1, 2}, new int[]{0, 3, 0, 4, 0})); // 결과 16
        System.out.println(instance.solution(2, 7, new int[]{1, 0, 2, 0, 1, 0, 2}, new int[]{0, 2, 0, 1, 0, 2, 0})); // 결과 30
        System.out.println(instance.solution(1, 3, new int[]{0, 0, 1}, new int[]{0, 1, 0})); // 결과 6
        System.out.println(instance.solution(2, 3, new int[]{3, 1, 2}, new int[]{0, 0, 0})); // 결과 12
        System.out.println(instance.solution(2, 3, new int[]{1, 1, 1}, new int[]{8, 7, 6})); // 결과 42

    }

}
