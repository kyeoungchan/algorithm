package example;

public class BinaryCounting {
    public static void main(String[] args) {
        int[] arr = {3, 6, 1, 7, 1, 5, 4};
        int n = arr.length;

        for (int i = 0; i < (1 << n); i++) { // 1 << n: 부분집합의 개수
            for (int j = 0; j < n; j++) { // 원소의 수만큼 비트를 비교함
                // j는 마스크오프를 하는 역할을 한다.
                if ((i & (1 << j)) != 0) { // i의 j번째 비트가 1이면 j번째 원소 출력
                    System.out.print(arr[j] + " ");
                    /*
                    i라는 비트가 부분집합의 수만큼 주어졌을 때,
                    j는 i라는 비트 속에서 부분집합만큼 다시 판별을 해주는 것이다. (마스크 오프; Mask Off)
                     */
                }
            }
            System.out.println();
        }
    }
}
