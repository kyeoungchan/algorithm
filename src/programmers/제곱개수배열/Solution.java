package programmers.제곱개수배열;

class Solution {

    int n;

    public long[] solution(int[] arr, long l, long r) {

        n = arr.length;

        // arr[i]의 구간이 끝났을 때의 앞에서부터의 길이.
        // arr[0] = 3이면, brr[0] = brr[1] = brr[2] = 3인데,
        // prefinxLenSum[0] = 3으로, 0번 인덱스부터 arr[0]이 끝날 때까지의 길이는 3이다.
        long[] prefixLenSum = new long[n];

        // arr[i]의 구간이 끝났을 때의 앞에서부터 값의 합.
        // arr[0] = 3이면, brr[0] = brr[1] = brr[2] = 3인데,
        // prefixValSum[0] = 9로, 0번 인덱스부터 arr[0]이 끝날 때까지의 값의 합은 9다.
        long[] prefixValSum = new long[n];

        prefixLenSum[0] = arr[0];
        prefixValSum[0] = (long) arr[0] * arr[0];

        for (int i = 1; i < n; i++) {
            prefixLenSum[i] = prefixLenSum[i - 1] + arr[i];
            prefixValSum[i] = prefixValSum[i - 1] + (long) arr[i] * arr[i];
        }

        long K = prefixSum(prefixLenSum, prefixValSum, arr, r)
                - prefixSum(prefixLenSum, prefixValSum, arr, l - 1);

        long L = r - l + 1;

        // 맨 앞에서부터 시작하는 윈도우의 합
        long cur = prefixSum(prefixLenSum, prefixValSum, arr, L);
        // 다음 윈도우로 향할 때 빼버릴 위치
        long s = 1;

        long totalLen = prefixLenSum[n - 1];

        // s가 마지막으로 갈 곳
        long maxStart = totalLen - L + 1;

        long count = 0L;

        while (s <= maxStart) {
            if (s == maxStart) {
                if (cur == K) count++;
                break;
            }

            // s개를 셌을 때의 arr 상에서의 위치 인덱스
            int outBlock = lowerBound(prefixLenSum, s);
            // outBlock 구간까지 다 셌을 때의 맨 앞에서부터의 개수
            long outEnd = prefixLenSum[outBlock];

            // s+L개를 셌을 때의 arr 상에서의 위치 인덱스
            int inBlock = lowerBound(prefixLenSum, s + L);
            // inBlock 구간까지 다 셌을 때의 맨 앞에서부터의 개수
            long inEnd = prefixLenSum[inBlock];

            // s <= outEnd가 유지되는 한 윈도우에서 뺄셈을 해야할 값이 유지
            // s+L <= inEnd -> s <= inEnd - L가 유지되는 한 윈도우에서 덧셈을 해야할 값이 유지
            long endS = Math.min(outEnd, inEnd - L);
            endS = Math.min(endS, maxStart - 1);

            long diff = (long) arr[inBlock] - arr[outBlock];

            // 윈도우가 반복되는 개수: s ~ endS
            long windowCnt = endS - s + 1;

            // cur, cur+diff, cur+diff+diff +...중 K의 개수
            if (diff == 0) {
                // diff가 0인데 이미 K와 값이 같다면 윈도우가 끝날 때까지 카운트 증가
                if (cur == K) count += windowCnt;
            } else if ((K - cur) % diff == 0) {
                // diff가 0이 아니지만 diff가 정수개로 덧셈하다보면 K로 갈 수 있을 때
                long mod = (K - cur) / diff;
                // cur + mod를 windowCnt 개수만큼 해야하므로
                // mod < windowCnt다. mod <= windowCnt X
                if (mod >= 0 && mod < windowCnt) count++;
            }

            // 다음 시작점(endS + 1)의 윈도우합으로 이동
            cur += windowCnt * diff;
            s = endS + 1;
        }

        long[] answer = new long[]{K, count};
        return answer;
    }

    // 맨앞에서부터 count 개수만큼의 값의 합 반환
    long prefixSum(long[] prefixLenSum, long[] prefixValSum, int[] arr, long count) {
        if (count <= 0) return 0L;

        int idx = lowerBound(prefixLenSum, count);

        long prevLen = (idx == 0) ? 0L : prefixLenSum[idx - 1];
        long prevVal = (idx == 0) ? 0L : prefixValSum[idx - 1];

        long innerCount = count - prevLen;
        return prevVal + innerCount * arr[idx];
    }

    // count 개수 만큼을 구할 때 arr 상에서의 위치를 구함
    int lowerBound(long[] prefixLenSum, long count) {
        int left = 0;
        int right = n - 1;
        while (left <= right) {
            int mid = left + (right - left) / 2;
            if (prefixLenSum[mid] >= count) {
                right = mid - 1;
            } else {
                left = mid + 1;
            }
        }
        return left;
    }
}