package programmers.시험장나누기;

import java.util.*;

public class Solution_pg_시험장나누기2 {

    int n, groupCnt, root;
    int[] parents;

    public int solution(int k, int[] num, int[][] links) {
        n = num.length;
        parents = new int[n];
        Arrays.fill(parents, -1);


        int start = 0;
        int end = 0;
        for (int i = 0; i < n; i++) {
            int cur = num[i];
            if (cur > start) {
                start = cur;
            }
            end += cur;

            if (links[i][0] != -1) {
                parents[links[i][0]] = i;
            }
            if (links[i][1] != -1) {
                parents[links[i][1]] = i;
            }
        }

        for (int i = 0; i < n; i++) {
            if (parents[i] == -1) {
                root = i;
                break;
            }
        }

        while (start <= end) {
            int mid = start + (end - start) / 2;
            if (canDivide(mid, k, num, links)) {
                end = mid - 1;
            } else {
                start = mid + 1;
            }
        }
        return start;
    }


    private boolean canDivide(int mid, int k, int[] num, int[][] links) {
        groupCnt = 1;
        divide(root, mid, num, links);
        return groupCnt <= k;
    }


    private int divide(int idx, int mid, int[] num, int[][] links) {
        int left = 0, right = 0;
        if (links[idx][0] != -1) {
            left = divide(links[idx][0], mid, num, links);
        }
        if (links[idx][1] != -1) {
            right = divide(links[idx][1], mid, num, links);
        }

        // 현재 노드가 mid보다 작으면 그룹을 새로 만들지 않고 바로 재귀호출 종료
        if (num[idx] + left + right <= mid) {
            return num[idx] + left + right;
        }

        // 좌우 자식 노드 중 하나만 더했을 때 mid보다 작다면 더 큰 자식 노드를 다른 그룹으로 분리 후 반환
        // 참고로 여기서 하나의 자식 노드만 있는 경우는 있을 수 없다.
        // 하나의 자식 노드만 있고 그 자식 노드를 더했을 때 mid보다 작거나 같은 경우는 위의 조건문에서 걸러진다.
        if (num[idx] + Math.min(left, right) <= mid) {
            groupCnt++;
            return num[idx] + Math.min(left, right);
        }

        // 이젠 양 자식 노드를 모두 다른 그룹으로 보낸 경우다.
        // 참고로 start를 모든 시험장 중 최대 인원수로 선정하고 시작했으므로 num[idx]가 mid보다 큰 경우는 있을 수 없다.
        groupCnt += 2;
        return num[idx];
    }
}
