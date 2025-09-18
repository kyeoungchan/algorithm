import java.util.*;

/*
1~n 사이의 수가 적힌 카드가 하나씩 있는 카드 뭉치(n은 6의 배수)
coin개의 동전
카드를 뽑는 순서 정해져 있음.
1. 카드 n/3장을 뽑는다. 카드와 coin개의 동전을 나는 갖는다.
2. 1라운드부터 시작, 각 라운드마다 카드 2장을 뽑는다.
카드 뭉치에 남은 카드가 없다면 게임 종료
뽑은 카드는 카드 한 장당 동전 하나를 소모해 가지거나, 동전을 소모하지 않고 버릴 수 있다.
3. 카드에 적힌 수의 합이 n+1이 되도록 카드 두 장을 내고 다음 라운드로 진행할 수 있다.
만약 카드 두 장을 낼 수 없다면 게임을 종료한다.
*/
class Solution {
    
    private Set<Integer> original, additional;
    
    public int solution(int coin, int[] cards) {
        int answer = 0;
        int len = cards.length;
        original = new HashSet<>();
        additional = new HashSet<>();
    
        int idx = len/3;
        for (int i = 0; i < idx; i++) {
            original.add(cards[i]);
        }
        
        int target = len + 1;
        while (true) {
            answer++;
            if (idx >= len) {
                break;
            }
            additional.add(cards[idx]);
            additional.add(cards[idx+1]);
            idx+=2;
            boolean flag = false;
            
            for (int o: original) {
                if (original.contains(target - o)) {
                    original.remove(o);
                    original.remove(target - o);
                    flag = true;
                    break;
                }
            }
            
            if (!flag) {
                if (coin > 0) {
                    for (int o : original) {
                        if (additional.contains(target - o)) {
                            original.remove(o);
                            additional.remove(target-o);
                            coin--;
                            flag = true;
                            break;
                        }
                    }
                }
            }
            
            if(!flag) {
                if (coin > 1) {
                    for (int a: additional) {
                        if (additional.contains(target - a)) {
                            additional.remove(a);
                            additional.remove(target - a);
                            coin -= 2;
                            flag = true;
                            break;
                        }
                    }
                }
            }
            
            if (!flag) break;
        }
        
        return answer;
    }
}