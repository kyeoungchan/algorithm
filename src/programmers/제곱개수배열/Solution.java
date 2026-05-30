package programmers.제곱개수배열;

class Solution {

    int n;
    int[] arr;
    long[] prefixLen, prefixSum;

    public long[] solution(int[] arr, long l, long r) {
        this.arr = arr;
        n = arr.length;

        prefixLen = new long[n];
        prefixSum = new long[n];
        prefixLen[0] = arr[0];
        prefixSum[0] = (long) arr[0] * arr[0];

        for (int i = 1; i < n; i++) {
            prefixLen[i] = prefixLen[i - 1] + arr[i];
            prefixSum[i] = prefixSum[i - 1] + (long) arr[i] * arr[i];
        }

        long K = getPrefixSum(r) - getPrefixSum(l - 1);

        long C = 0L;

        long L = r - l + 1;
        long s = 1L;
        long maxS = prefixLen[n - 1] - L + 1;

        long cur = getPrefixSum(L);

        while (s <= maxS) {
            if (s == maxS) {
                if (cur == K) C++;
                break;
            }

            int outBound = lowerBound(s);

            int inBound = lowerBound(s + L);

            long diff = arr[inBound] - arr[outBound];

            long endS = Math.min(prefixLen[outBound], prefixLen[inBound] - L);
            endS = Math.min(endS, maxS - 1);

            long windowCount = endS - s + 1;

            if (diff == 0) {
                if (cur == K) C += windowCount;
            } else if ((K - cur) % diff == 0) {
                // cur, cur + diff, cur + 2*diff +.. + cur + (windowCount-1) * diff
                long mod = (K - cur) / diff;
                if (mod >= 0 && mod < windowCount) C++;
            }

            cur += diff * windowCount;
            s = endS + 1;
        }

        return new long[] {K, C};
    }

    long getPrefixSum(long count) {
        int idx = lowerBound(count);
        if (idx == 0) return (long) arr[idx] * count;

        long innerCount = count - prefixLen[idx - 1];
        return prefixSum[idx - 1] + arr[idx] * innerCount;
    }

    int lowerBound(long count) {
        int left = 0;
        int right = n - 1;
        while (left <= right) {
            int mid = left + (right - left) / 2;
            if (prefixLen[mid] >= count) {
                right = mid - 1;
            } else {
                left = mid + 1;
            }
        }
        return left;
    }
}