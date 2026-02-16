import java.util.*;

class Solution {
    public long solution(int cap, int n, int[] deliveries, int[] pickups) {
        long answer = 0L;
        
        int delTarI = -1;
        int pickTarI = -1;
        
        
        for (int i = n - 1; i >= 0; i--) {
            if (delTarI == -1 && deliveries[i] > 0) {
                delTarI = i;
            }
            if (pickTarI == -1 && pickups[i] > 0) {
                pickTarI = i;
            }
            if (delTarI != -1 && pickTarI != -1) {
                break;
            }
        }
        
        while (delTarI != -1 || pickTarI != -1) {
            // System.out.println("delTarI: " + delTarI);
            // System.out.println("pickTarI: " + pickTarI);
            
            answer += 2 * Math.max(delTarI + 1, pickTarI + 1);
            // System.out.println("answer: " + answer);
            int temp = cap;
            for (int i = delTarI; i >= 0; i--) {
                if (i == 0 && deliveries[i] == 0) {
                    // 배달 완료
                    delTarI = -1;
                    break;
                }
                
                if (deliveries[i] > temp) {
                    deliveries[i] -= temp;
                    delTarI = i;
                    break;
                } else {
                    temp -= deliveries[i];
                    deliveries[i] = 0;
                    if (i == 0) {
                        delTarI = -1;
                    }
                }
            }
            
            temp = cap;
            for (int i = pickTarI; i >= 0; i--) {
                if (i == 0 && pickups[i] == 0) {
                    // 수거 완료
                    pickTarI = -1;
                    break;
                }
                
                if (pickups[i] > temp) {
                    pickups[i] -= temp;
                    pickTarI = i;
                    break;
                } else {
                    temp -= pickups[i];
                    pickups[i] = 0;
                    if (i == 0) {
                        pickTarI = -1;
                    }
                }
            }

            // System.out.println(Arrays.toString(deliveries));
            // System.out.println(Arrays.toString(pickups));
            // System.out.println();
        }
        
        
        return answer;
    }
}