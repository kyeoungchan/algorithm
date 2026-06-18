/* A(N명) vs B(N명)
   각 팀 1명씩 숫자 대결 -> 큰 수가 +1
   A 순서를 알고 있는 상황에서 B가 얻을 수 있는 최대 승점?
   
   => A를 오름 차순 정렬 후 B도 오름차순 정렬
      A[i]와 B[j] 비교 -> j가 끝날때까지 진행
      * 길이가 1이거나 마지막 원소 비교 위해 마지막에 한번 더 진행
*/

import java.util.*;

class Solution {
    public int solution(int[] A, int[] B) {
        int answer = 0;
        
        int N = A.length;
        Arrays.sort(A);
        Arrays.sort(B);
        
        Deque<Integer> queueA = new ArrayDeque<>();
        Deque<Integer> queueB = new ArrayDeque<>();
        
        for(int i=0;i<N;i++){
            queueA.offer(A[i]);
            queueB.offer(B[i]);
        }
        
        int curA = queueA.poll();
        int curB = queueB.poll();
       
        while(!queueB.isEmpty()){
            if(curA >= curB){
                curB = queueB.poll();
                continue;
            }
            else{
                answer++;
                curA = queueA.poll();
                curB = queueB.poll();
            }
        }
        
        answer += curA < curB? 1:0;
        return answer;
    }
}