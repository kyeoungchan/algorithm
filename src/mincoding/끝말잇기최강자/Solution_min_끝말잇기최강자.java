package mincoding.끝말잇기최강자;

import java.io.*;
import java.util.*;

/**
 * N명의 플레이어가 M개의 단어를 사용하여 끝말잇기 게임을 한다.
 * 3 <= N <= 50_000, N <= M <= 50_000
 * 플레이어의 ID: 1~N
 * 단어 목록에서 각 단어는 2~10의 길이를 가진 영어 알파벳 소문자. '\0'로 끝나는 문자열
 * 단어 목록 내 단어는 중복되지 않는다.
 * 각 라운드마다 ID 순으로 각 플레이어는 진행한다.
 * 각 라운드를 시작하기 전 첫 번째 턴을 진행할 플레이어 ID와 첫 단얼르 정하기 위한 하나의 문자가 주어진다.
 * 플레이어는 자신의 턴이 되면 조건에 맞는 단어를 골라야하고, 선택된 단어는 다시 선택되지 않도록 단어 목록에서 삭제한다.
 * 단어 선택 조건
 * 1. 이전 턴에 선택된 단어의 마지막 문자로 시작하는 단어.
 * 2. 게임을 진행하는 동안 단 한번도 선택된 적이 없는 단어
 * 3. 사전순으로 가장 빠른 단어.
 * 위 조건에 맞는 단어가 더이상 없는 경우 해당 턴의 플레이어는 게임에서 탈락하고 라운드가 종료된다.
 * 탈락한 플레이어는 다음 라운드부터 턴 진행에서 제외된다.
 * 다음 라운드를 시작하기 전에 이전 라운드에서 선택된 단어를 뒤집은 후 단어 목록에 추가한다.
 * "뒤집은 단어가 이미 선택된 단어라면 단어 목록에 추가하지 않는다."
 */
public class Solution_min_끝말잇기최강자 {
    private final static int MAXN = 50000;
    private final static int WORD_MAXLEN = 11;

    private Map<Character, PriorityQueue<String>> pqMap = new HashMap<>();
    private Map<String, Boolean> usedmap = new HashMap<>();
    private boolean[] loosed;
    private int N;

    public void init(int N, int M, char[][] words) {
        this.N = N;
        loosed = new boolean[N + 1];
        pqMap.clear();
        usedmap.clear();
        for (int i = 0; i < M; i++) {
            if (!pqMap.containsKey(words[i][0])) pqMap.put(words[i][0], new PriorityQueue<>());
            PriorityQueue<String> pq = pqMap.get(words[i][0]);
            pq.offer(getString(words[i]));
        }
    }

    private String getString(char[] wordArr) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < wordArr.length; i++) {
            sb.append(wordArr[i]);
        }
        return sb.toString();
    }


    public int playRound(int pid, char ch) {
        System.out.println("\nplayRound");
        System.out.println("pid = " + pid);
        System.out.println("ch = " + ch);
        List<String> reverseList = new ArrayList<>();
        while (true) {
            if (!pqMap.containsKey(ch) || pqMap.get(ch).isEmpty()) break;
            String word = pqMap.get(ch).poll();
            if (usedmap.containsKey(word)) continue;
            System.out.println("using Word = " + word);
            System.out.println("pid = " + pid);
            usedmap.put(word, true);
            String reversedWord = reverseWord(word);
            System.out.println("reversedWord = " + reversedWord);
            if (!usedmap.containsKey(reversedWord)) reverseList.add(reversedWord);
            if (++pid > N) pid = 1;
            ch = reversedWord.charAt(0);
        }
        for (String word : reverseList) {
            char key = word.charAt(0);
            if (!pqMap.containsKey(key)) pqMap.put(key, new PriorityQueue<>());
            PriorityQueue<String> pq = pqMap.get(key);
            pq.offer(word);
        }
        return pid;
    }

    private String reverseWord(String word) {
        StringBuilder sb = new StringBuilder();
        for (int i = word.length() - 1; i > -1; i--) sb.append(word.charAt(i));
        return sb.toString();
    }
}
