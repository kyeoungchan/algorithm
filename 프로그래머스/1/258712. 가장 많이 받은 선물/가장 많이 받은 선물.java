import java.util.*;

class Solution {
    public int solution(String[] friends, String[] gifts) {
        
        Map<String, Integer> idxMap = new HashMap<>();
        
        for (int i = 0; i < friends.length; i++) {
            idxMap.put(friends[i], i);
        }

        int[][] gaveGifts = new int[friends.length][friends.length];
        int[] giftScores = new int[friends.length];
        StringTokenizer st;
        
        for (String gift: gifts) {
            st = new StringTokenizer(gift, " ");
            int fromIdx = idxMap.get(st.nextToken());
            int toIdx = idxMap.get(st.nextToken());
            gaveGifts[fromIdx][toIdx]++;
            giftScores[fromIdx]++;
            giftScores[toIdx]--;
        }
        
        int[] recGifts = new int[friends.length];
        int answer = 0;
        
        for (int i = 0; i < friends.length; i++) {
            for (int j = 0; j < friends.length; j++) {
                if (i == j) continue;
                if (gaveGifts[i][j] > gaveGifts[j][i]) {
                    recGifts[i]++;
                } else if (gaveGifts[i][j] == gaveGifts[j][i] && giftScores[i] > giftScores[j]) {
                    recGifts[i]++;
                }
            }
            answer = Math.max(recGifts[i], answer);
        }
        
        return answer;
    }
}