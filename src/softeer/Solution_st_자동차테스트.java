package softeer;

import java.io.*;
import java.util.*;

public class Solution_st_자동차테스트 {

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine(), " ");
        StringBuilder sb = new StringBuilder();
        int n = Integer.parseInt(st.nextToken());
        int q = Integer.parseInt(st.nextToken());
        int[] efficiencies = new int[n];
        st = new StringTokenizer(br.readLine(), " ");
        //int[] indexes = new int[1_000_000_001];
        Map<Integer, Integer> idxMap = new HashMap<>();
        for (int i = 0; i < n; i++) {
            efficiencies[i] = Integer.parseInt(st.nextToken());
        }
        bubbleSort(efficiencies, idxMap, 0, n-1);
//        System.out.println(Arrays.toString(efficiencies));
//        for (int i = 0; i < 8; i++) {
//            System.out.print(indexes[i] + " ");
//        }

        for (int i = 0; i < q; i++) {
            int mi = Integer.parseInt(br.readLine());
            // 만약 2, 3, 4, 5에서 4를 기준으로 보자면, 왼쪽은 2개(인덱스), 오른쪽은 전체에서 왼쪽의 개수와 자신을 뺀 값의 개수다.
            if (!idxMap.containsKey(mi)) {
                sb.append(0).append("\n");
                continue;
            }
            int leftCnt = idxMap.get(mi);
            int rightCnt = n - (leftCnt + 1);
            sb.append(leftCnt * rightCnt).append("\n");
        }
        System.out.println(sb.toString());

        br.close();
    }

    static void bubbleSort(int[] arr, Map<Integer, Integer> idxMap, int startIdx, int endIdx) {
        if (startIdx == endIdx) {
            return;
        }

        int mid = startIdx + (endIdx - startIdx) / 2;
        int leftIdx = startIdx;
        int rightIdx = mid + 1;
        bubbleSort(arr, idxMap, startIdx, mid);
        bubbleSort(arr, idxMap, rightIdx, endIdx);

        int[] tempArr = new int[startIdx + endIdx + 1];
        int tempIdx = 0;
        while(leftIdx <= mid || rightIdx <= endIdx) {
            if (leftIdx <= mid && rightIdx <= endIdx) {
                if (arr[leftIdx] < arr[rightIdx]) {
                    tempArr[tempIdx++] = arr[leftIdx];
                    leftIdx++;
                } else {
                    tempArr[tempIdx++] = arr[rightIdx];
                    rightIdx++;
                }
            } else if (leftIdx <= mid) {
                tempArr[tempIdx++] = arr[leftIdx++];
            } else {
                tempArr[tempIdx++] = arr[rightIdx++];
            }
        }
        for (int i = startIdx; i <= endIdx; i++) {
            arr[i] = tempArr[i - startIdx];
            idxMap.put(arr[i], i);
        }
    }
}

