import java.util.*;

public class Solution {

    int n, root, cutCnt;
    int[] parents;

    // 시험장이 n개 있다면, 시험장의 고유 번호는 0부터 n-1까지 부여됩니다.
    public int solution(int k, int[] num, int[][] links) {
        int answer = 0;
        // 각 노드들의 부모를 가리키는 배열
        n = num.length;
        parents = new int[n];
        Arrays.fill(parents, -1);

        int start = 0;
        int end = 0;
        for (int i = 0; i < n; i++) {
            int left = links[i][0];
            if (left != -1) parents[left] = i;
            int right = links[i][1];
            if (right != -1) parents[right] = i;
            start = Math.max(start, num[i]);
            end += num[i];
        }

        for (int i = 0; i < n; i++) {
            if (parents[i] == -1) {
                root = i;
                break;
            }
        }

        while (start <= end) {
            int mid = start + (end - start) / 2;
            // mid 인원수를 최대로 하는 그룹으로 나눴을 때 k개 이하의 그룹이 생성이 가능하지 못하다면
            if (!canDivide(mid, k, num, links)) {
                start = mid + 1;
            } else {
                answer = mid;
                end = mid - 1;
            }
        }
        return answer;
    }

    private boolean canDivide(int mid, int k, int[] num, int[][] links) {
        cutCnt = 0;
        divide(mid, root, num, links);
        return cutCnt <= k - 1;
    }

    // 현재노드에서 자식 노드를 자른다고 생각하자.
    private int divide(int mid, int idx, int[] num, int[][] links) {
        int left = 0, right = 0;
        int leftIdx = links[idx][0];
        int rightIdx = links[idx][1];
        if (leftIdx != -1) left = divide(mid, leftIdx, num, links);
        if (rightIdx != -1) right = divide(mid, rightIdx, num, links);

        // 어느쪽도 안 잘라도 되는 경우
        if (num[idx] + left + right <= mid) {
            return num[idx] + left + right;
        }

        // 한 쪽만 자르면 해결되는 경우
        if (num[idx] + Math.min(left, right) <= mid) {
            cutCnt++;
            return num[idx] + Math.min(left, right);
        }

        // 양 쪽 다 잘라야하는 경우
        // 물론 mid가 하나의 노드의 값보다 더 작은 경우는 여기서 걸러지지 않는다. 그러나 결국 cutCnt가 많아질수밖에 없으므로 정답에 영향을 주지 않게 된다.
        // 사실 start를 0이 아니라 조건에 맞게 주고 시작해야 옳다.
        cutCnt += 2;
        return num[idx];
    }
}

