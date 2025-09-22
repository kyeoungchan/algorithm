package programmers.택배배달과수거하기;

public class Solution_pg_택배배달과수거하기 {
    public long solution(int cap, int n, int[] deliveries, int[] pickups) {
        long answer = 0;
        int totalD = 0;
        int totalP = 0;
        int addD = 0;
        int addP = 0;

        for (int i = 0; i < n; i++) {
            if (deliveries[i] != 0) {
                addD = i + 1;
                totalD += deliveries[i];
            }

            if (pickups[i] != 0) {
                addP = i + 1;
                totalP += pickups[i];
            }
        }

        while (totalD != 0 || totalP != 0) {
            // 거리는 일단 제일 먼 쪽 * 2
            answer += Math.max(addD, addP) * 2;

            // 시작은 최대한 cap만큼 담되, totalD가 더 적다면 totalD만큼 담아간다.
            int startBox = Math.min(cap, totalD);

            for (int i = addD - 1; i >= 0; i--) {
                if (deliveries[i] > 0) {
                    if (startBox == 0) {
                        addD = i + 1;
                        break;
                    }
                    int sub = Math.min(deliveries[i], startBox);
                    deliveries[i] -= sub;
                    startBox -= sub;
                    totalD -= sub;
                    if (totalD == 0) {
                        // 더이상 남은 배달이 없는 경우
                        addD = 0;
                        break;
                    }
                    if (deliveries[i] > 0) {
                        // startBox == 0이란 뜻
                        addD = i + 1;
                        break;
                    }
                }
            }

            //System.out.println("totalD: " + totalD + ", addD: " + addD + ", deliveries: " + Arrays.toString(deliveries));

            int pickBox = 0;

            for (int i = addP - 1; i >= 0; i--) {
                if (pickups[i] > 0) {
                    if (pickBox == cap) {
                        // 더이상 못 담는 경우
                        addP = i + 1;
                        break;
                    }
                    int add = Math.min(cap - pickBox, pickups[i]);
                    pickBox += add;
                    pickups[i] -= add;
                    totalP -= add;
                    if (totalP == 0) {
                        // 더이상 담은 재활용 박스가 없는 경우
                        addP = 0;
                        break;
                    }
                    if (pickups[i] > 0) {
                        addP = i + 1;
                        // pickBox에 더 담을 수 없다는 뜻
                        break;
                    }
                }
            }
            //System.out.println("totalP: " + totalP + ", addP: " + addP + ", pickups: " + Arrays.toString(pickups));
            //System.out.println("anser: " + answer);
            //System.out.println();

        }
        return answer;
    }
}
