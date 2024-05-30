package programmers.시험장나누기;

import java.util.*;

public class Solution_pg_시험장나누기_다른사람코드2 {
    public int solution(int k, int[] num, int[][] links) {

        // 현재 노드의 부모 노드 배열, 이를 통해서 루트 노드 구하기
        int[] parents = new int[num.length];
        Arrays.fill(parents,-1);

        // 부모 노드 추가
        for(int i=0;i<links.length;i++){
            // i번 노드의 왼쪽 자식 노드가 -1이 아닐 경우 parents에 추가
            if(links[i][0] != -1) parents[links[i][0]] = i;

            // i번 노드의 오른쪽 자식 노드가 -1이 아닐 경우 parents에 추가
            if(links[i][1] != -1) parents[links[i][1]] = i;
        }

        // 부모 노드가 없을 경우 (-1일 경우) root 노드
        int root = -1;
        for(int i=0;i<parents.length;i++){
            if(parents[i]==-1){
                root = i;
                break;
            }
        }

        // 최대 그룹의 인원을 mid로 설정하여 이진 탐색 진행
        // 최솟값 : 단일 노드의 최댓값
        // 최댓값 : 모든 노드가 최댓값을 가질 때의 전체 그룹의 총합
        // = num의 최대 길이 * num의 최대 크기
        int min = 0;
        int max = 100000000;
        for(int i=0;i<num.length;i++)
            min = Math.max(min,num[i]);

        // 이진 탐색 진행
        while(min <= max){
            int mid = (min + max)/2;

            // 현재 최대 그룹의 인원을 k번 이내에 쪼개서 만들 수 있다면 더 작은 값이 존재하는지 확인하기 위해 최댓값을 mid-1로 설정
            if(canMake(k,num,links,root,mid)) max = mid-1;

                // 그렇지 않다면 현재 mid값보다 키워서 제한 범위를 넓혀야 하기 때문에 최솟값을 mid+1로 설정
            else min = mid+1;
        }

        // 결과값 출력
        return min;
    }

    static int cnt;
    static boolean canMake(int k, int[] num, int[][] links, int root, int mid){
        // dfs 이용하여 루트 노드에서부터 내려오면서 mid값이 나올때까지 0,1,2번 쪼개서 횟수 카운트
        cnt = 0;
        dfs(num,links,root,mid);

        // k개의 그룹이기 때문에 자른 횟수는 최대 k-1
        // 구한 횟수가 k번 이내라면 true 그렇지 않다면 false
        if(cnt < k) return true;
        else return false;
    }

    static int dfs(int[] num, int[][] links, int curr, int mid){
        // 현재 노드 기준 왼쪽과 오른쪽으로 리프 노드까지 내려가면서 탐색
        // 자식 노드가 없다면 각각 0으로 초기화
        int left = 0;
        int right = 0;
        if(links[curr][0] != -1) left = dfs(num,links,links[curr][0],mid);
        if(links[curr][1] != -1) right = dfs(num,links,links[curr][1],mid);

        // 만약 현재 값과 왼쪽 값 오른쪽 값 모두 더해도 mid보다 작거나 같다면 쪼개지 않음
        if(num[curr] + left + right <= mid)
            return num[curr] + left + right;

        // 만약 현재 값과 왼쪽 또는 오른쪽 값 중 하나와 더해도 mid보다 작거나 같다면 1번만 쪼갬
        // 이 때 최소화된 최대 그룹의 인원을 리턴해야 하므로 왼쪽 또는 오른쪽 값 중 최솟값을 더함
        if(num[curr] + Math.min(left,right) <= mid){
            cnt++;
            return num[curr] + Math.min(left,right);
        }

        // 현재값은 무조건 더해져야 하므로 두 번 쪼갠 경우 추가
        cnt+=2;
        return num[curr];
    }
}
