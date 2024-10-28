import java.io.*;
import java.util.*;

public class Main {
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        int N = Integer.parseInt(st.nextToken());
        int M = Integer.parseInt(st.nextToken());
        List<Integer> plusList = new ArrayList<>();
        List<Integer> minusList = new ArrayList<>();

        st = new StringTokenizer(br.readLine());
        for (int i = 0; i < N; i++) {
            int val = Integer.parseInt(st.nextToken());
            if (val > 0) plusList.add(val);
            else minusList.add(val);
        }

        int plusIdx = plusList.size() - 1;
        int minusIdx = minusList.size() - 1;
        Collections.sort(plusList);
        Collections.sort(minusList, Collections.reverseOrder());
        int result = 0;

        if (!plusList.isEmpty() && !minusList.isEmpty()) {
            if (plusList.get(plusIdx) > -minusList.get(minusIdx)) {
                result += plusList.get(plusIdx);
                for (int i = 0; i < M && plusIdx >= 0; i++) plusIdx--;
//                System.out.println("result = " + result);
            } else {
                result -= minusList.get(minusIdx);
                for (int i = 0; i < M && minusIdx >= 0; i++) minusIdx--;
//                System.out.println("result = " + result);
            }
        } else if (!plusList.isEmpty()) {
            result += plusList.get(plusIdx);
            for (int i = 0; i < M && plusIdx >= 0; i++) plusIdx--;
//            System.out.println("result = " + result);
        } else {
            result -= minusList.get(minusIdx);
            for (int i = 0; i < M && minusIdx >= 0; i++) minusIdx--;
//            System.out.println("result = " + result);
        }

        while (plusIdx >= 0) {
            result += 2 * plusList.get(plusIdx);
            for (int i = 0; i < M && plusIdx >= 0; i++) plusIdx--;
//            System.out.println("result = " + result);
        }
        while (minusIdx >= 0) {
            result -= 2 * minusList.get(minusIdx);
            for (int i = 0; i < M && minusIdx >= 0; i++) minusIdx--;
//            System.out.println("result = " + result);
        }

        System.out.println(result);
        br.close();
    }
}