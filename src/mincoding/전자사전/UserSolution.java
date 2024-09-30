package mincoding.전자사전;

import java.util.*;
import java.io.*;

public class UserSolution {

    static class Word implements Comparable<Word> {
        String word;
        boolean isRemoved;

        public void setData(String word) {
            this.word = word;
            isRemoved = false;
        }

        @Override
        public int compareTo(Word o) {
            return word.compareTo(o.word);
        }
    }

    Map<String, Word> wordMap = new HashMap<>(); // String으로 해당 단어 객체 조회를 위한 해시맵
    List<Word> wordList = new ArrayList<>();
    int[] frontIdx; // a~z까지 정렬된 리스트에서 각각 가장 첫번째 단어가 몇번째 인덱스인지 저장하는 int형 배열

    Word[] wordArr = new Word[40_001];
    // Word 객체에 각 테케를 돌릴 때마다 new 연산 사용을 최소화시키기 위한 배열. new연산을 적게하면 성능 개선에 도움을 줍니다.
    // init에서 N의 범위가 3만 이하였고, add() 메서드 호출 제한이 10,000이었기 때문에 40,000 그리고 혹시 모를 1정도의 여분을 줬다.
    int wordArrIdx, alphabetLen = 'z' - 'a' + 2;

    public void init(int N, String[] words) {
        wordMap.clear();
        wordList.clear();
        frontIdx = new int[alphabetLen];

        wordArrIdx = 0;
        for (int i = 0; i < N; i++) {
            wordList.add(createWord(words[i]));
            frontIdx[getAlphabetIndex(words[i].charAt(0)) + 1]++;
            // 누적합 사용을 위해서 그 다음 문자 인덱스를 1만큼 증가시켜준다.
            // 예를 들어, a로 시작하는 단어만 2개가 들어온다면, b로 시작하는 frontIndex가 2로 바껴있다.
        }

        Collections.sort(wordList); // 단어순 정렬
        for (int i = 1; i < alphabetLen; i++) {
            // 누적합 사용.
            // 위의 예시를 들어 b로 시작하는 인덱스만 2가 돼있는 상태라면, 이 반복문을 통해 b부터 z+1까지 전부 2로 바뀌게 됩니다.
            // 그러면 b부터 z까지는 모두 2번 인덱스의 위치부터 단어가 추가될 수 있다는 뜻이 됩니다.
            frontIdx[i] += frontIdx[i - 1];
        }
    }

    private Word createWord(String word) {
        if (wordArr[wordArrIdx] == null) wordArr[wordArrIdx] = new Word();
        wordArr[wordArrIdx].setData(word);
        // 이런 식으로 테스트케이스를 반복하더라도 new 연산을 필요한 만큼만 사용할 수 있다.
        wordMap.put(word, wordArr[wordArrIdx]);
        return wordArr[wordArrIdx++];
    }

    private int getAlphabetIndex(char alphabet) {
        return alphabet - 'a';
    }

    public int add(String mWord) {
/*
        System.out.println("\nadd");
        System.out.println("mWord = " + mWord);
*/
        if (wordMap.containsKey(mWord)) return 0;
        wordList.add(getIndex(mWord, wordList), createWord(mWord)); // 해당 인덱스에 단어를 넣는다.
        // 만약 1번 wordList.add(1, 넣고자하는 단어) 연산을 수행하면 1번 인덱스에 그 단어가 들어가고 원래 1번이었던 단어부터 뒤의 단어들은 모두 한칸씩 뒤로 밀려난다.
        for (int i = getAlphabetIndex(mWord.charAt(0)) + 1; i < alphabetLen; i++) {
            // a로 시작하는 단어가 들어왔으면 a의 frontIndex는 고정이지만, 그 다음 b부터 z까지의 frontIndex는 뒤로 한칸씩 밀려나야한다.
            frontIdx[i]++;
        }
        return 1;
    }

    private int getIndex(String word, List<Word> wordList) {
        int left = 0;
        int right = wordList.size() - 1;
        while (left <= right) {
            int mid = left + (right - left) / 2;
            // mid = (left + right) / 2와 로직은 같지만 가끔 left + right 연산 자체만으로 int형 범위를 벗어나는 경우가 발생하여 저는 이렇게 코드를 짜는 편입니다.
            String target = wordList.get(mid).word;
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

    public int erase(String mWord) {
/*
        System.out.println("\nerase");
        System.out.println("mWord = " + mWord);
*/
        if (!wordMap.containsKey(mWord) || wordMap.get(mWord).isRemoved) return 0;
        Word target = wordMap.get(mWord);
        target.isRemoved = true;
        wordList.remove(getIndex(mWord, wordList));
        for (int i = getAlphabetIndex(mWord.charAt(0)) + 1; i < alphabetLen; i++) {
            // add할 때랑 마찬가지로 a로 시작하는 단어가 빠졌다면 a의 frontIndex는 고정이지만, 그 다음 b부터 z까지의 frontIndex는 앞으로 한칸씩 당겨온다.
            frontIdx[i]--;
        }
        return 1;
    }

    public Main.Result find(char mInitial, int mIndex) {
/*
        System.out.println("\nfind");
        System.out.println("mInitial = " + mInitial);
        System.out.println("mIndex = " + mIndex);
*/
        Main.Result ret = new Main.Result();
        int alphabetIndex = getAlphabetIndex(mInitial);
        if (frontIdx[alphabetIndex + 1] - frontIdx[alphabetIndex] < mIndex) {
            // 만약 fontIdx[0] == 0, frontIdx[1] == 2라면, 즉 a로 시작하는 단어의 첫번째 인덱스가 0이고, b로 시작하는 단어의 첫번째 인덱스가 2라면 a로 시작하는 단어가 2개가 있다는 뜻입니다.
            // 즉, frontIdx[alphabetIndex + 1] - frontIdx[alphabetIndex] 자체가 alphabet으로 시작하는 단어의 개수를 의미합니다.
            // z로 시작하는 단어의 개수도 카운트를 미리 해야했기 때문에 alphabetLen = 'z' - 'a' + 2;로 두었습니다.

            ret.success = 0;
            return ret;
        }
        ret.success = 1;

        mIndex--;
        // 3번째 단어라면 2번째 인덱스를 나타내므로 -1이 필요하다.

        ret.word = wordList.get(frontIdx[alphabetIndex] + mIndex).word;
        return ret;
    }

    public int getIndex(String mWord) {
/*
        System.out.println("\nrgetIndex");
        System.out.println("mWord = " + mWord);
*/
        // 주어진 단어가 몇 번째 단어인지 구하라는 메서드였던 것 같습니다.
        if (!wordMap.containsKey(mWord) || wordMap.get(mWord).isRemoved) return 0; // 존재하지 않다면 0을 반환하라고 했던 것 같습니다 아마..

        // 인덱스를 구하는 게 아니라 몇 번째인지를 구하는 문제기 때문에 +1을 해서 반환
        return getIndex(mWord, wordList) + 1;
    }
}
