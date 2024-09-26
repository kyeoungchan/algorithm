package mincoding.단어관라;

import java.util.*;

public class Test {
    public static void main(String[] args) {
        List<String> list = new ArrayList<>();
        list.add("a");
        list.add("ac");
        System.out.println("getIndex(\"ab\", list) = " + getIndex("ab", list));
        System.out.println("getIndex(\"a\", list) = " + getIndex("a", list));
        System.out.println("getIndex(\"ac\", list) = " + getIndex("ac", list));
        System.out.println("getIndex(\"d\", list) = " + getIndex("d", list));

        list.add("ab");
        Collections.sort(list);
        System.out.println("getIndex(\"ab\", list) = " + getIndex("ab", list));
        System.out.println("list = " + list);
    }

    private static int getIndex(String word, List<String> wordList) {
        int left = 0;
        int right = wordList.size() - 1;
        while (left <= right) {
            int mid = left + (right - left) / 2;
            // mid = (left + right) / 2와 로직은 같지만 가끔 left + right 연산 자체만으로 int형 범위를 벗어나는 경우가 발생하여 저는 이렇게 코드를 짜는 편입니다.
            String target = wordList.get(mid);
            if (target.compareTo(word) < 0) {
                // 만약 찾고자 하는 인덱스가 word가 들어가야할 자리보다 앞이라면
                left = mid + 1;
            } else {
                // 만약 찾고자 하는 인덱스가 word가 들어가야할 자리보다 뒤라면
                right = mid - 1;
            }
        }
        // 만약 찾고자 하는 word가 리스트에 존재한다면 그 인덱스를 반환하고
        // 존재하지 않는다면 해당 word가 넣어져야할 인덱스를 반환한다.
        // 즉, 만약 ab라는 단어를 넣으려고 하는데 wordList = [a, ac]인 상태라면 1을 반환한다.
        return left;
    }
}
