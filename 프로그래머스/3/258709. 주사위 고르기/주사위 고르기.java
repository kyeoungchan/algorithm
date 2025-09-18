import java.util.*;

class Solution {
    
    private int n, max;
    private int[] answer, diceA, diceB;
    private int[][] dice;
    //private List<Integer> listA, listB;
    
    public int[] solution(int[][] dice) {
        n = dice.length;
        this.dice = dice;
        answer = new int[n/2];
        max = 0;
        
        diceA = new int[n/2];
        //listA = new ArrayList<>();
        //listB = new ArrayList<>();
        
        choose(0, 1);
        
        return answer;
    }
    
    private void choose(int depth, int number) {
        if (depth == n/2) {
            
            diceB = new int[n/2];
            
            int aIdx = 0, bIdx = 0;
            for (int num = 1; num <= n; num++) {
                if (aIdx < n/2 && diceA[aIdx] == num) {
                    aIdx++;
                } else {
                    diceB[bIdx++] = num;
                }
            }
            //printDices();
            
            List<Integer> listA = new ArrayList<>();
            throwDice(0, 0, listA, diceA);
            //System.out.println("listA: " + listA);
            List<Integer> listB = new ArrayList<>();
            throwDice(0, 0, listB, diceB);
            //System.out.println("listB: " + listB);
            //System.out.println();
            
            Collections.sort(listB);
            int winningCount = 0;
            for (int a: listA) {
                int left = 0, right = listB.size() - 1, idx = 0;
                while (left <= right) {
                    int mid = left + (right - left) / 2;
                    if (a > listB.get(mid)) {
                        idx = mid;
                        left = mid + 1;
                    } else {
                        right = mid - 1;
                    }
                }
                winningCount += idx;
            }
            if (winningCount > max) {
                max = winningCount;
                for (int i = 0; i < n/2; i++) {
                    answer[i] = diceA[i];
                }
            }
            return;
        }
        
        if (number > n) return;
        
        diceA[depth] = number;
        choose(depth + 1, number + 1);
        diceA[depth] = 0;
        choose(depth, number + 1);
    }
    
    private void throwDice(int depth, int sum, List<Integer> list, int[] diceAB) {
        if (depth == n/2) {
            list.add(sum);
            return;
        }
        
        int newSum = sum;
        int targetNum = diceAB[depth];
        for (int i = 0; i < 6; i++) {
            newSum += dice[targetNum - 1][i];
            throwDice(depth + 1, newSum, list, diceAB);
            newSum -= dice[targetNum - 1][i];
        }
    }
    
    private void printDices() {
        System.out.println("diceA");
        System.out.println(Arrays.toString(diceA));
        System.out.println();
        
        System.out.println("diceB");
        System.out.println(Arrays.toString(diceB));
        System.out.println();
        System.out.println();
    }
}