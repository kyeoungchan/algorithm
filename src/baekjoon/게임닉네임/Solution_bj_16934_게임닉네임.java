package baekjoon.게임닉네임;

import java.util.*;
import java.io.*;

/**
 * 닉네임은 알파벳 소문자로만 이루어져있다.
 * 유저 닉네임을 이용해 저장할 별칭을 만든다.
 * 닉네임을 파싱해서 각각 별칭의 카운트 정보를 담은 객체를 Map에다가 저장한다.
 * 저장하면서 처음 저장하는 별칭이 나온다면 그것을 StringBuilder에 담는다.
 */
public class Solution_bj_16934_게임닉네임 {

    static class Data {
        String alias;
        int count;

        public Data(String alias) {
            this.alias = alias;
        }

        public void addAlias() {
            count++;
        }

        // 그리고 x가 2 이상이라면 {풀 닉네임x}로 별칭을 정한다.
        public String getFullNickName() {
            if (count == 1) return alias;
            return alias + count;
        }
    }

    static Map<String, Data> map;

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder sb = new StringBuilder();
        int N = Integer.parseInt(br.readLine());
        map = new HashMap<>();
        for (int i = 0; i < N; i++) {
            String nickName = br.readLine();
            parseAndGetAlias(nickName, sb);
        }
        System.out.print(sb);
        br.close();
    }

    static void parseAndGetAlias(String nickName, StringBuilder sb) {
        boolean gotAlias = false;
        for (int i = 1; i < nickName.length() + 1; i++) {
            String candidate = nickName.substring(0, i);
            // 하지만 이전에 가입한 유저의 닉네임의 접두사면 안 된다.
            if (!map.containsKey(candidate)) {
                // 접두사 중에서 가장 길이가 짧은 것.
                if (!gotAlias) {
                    sb.append(candidate).append("\n");
                    gotAlias = true;
                }
                map.put(candidate, new Data(candidate));
            }
        }

        // 지금까지 같은 닉네임으로 가입한 유저의 수 메모
        map.get(nickName).addAlias();

        // 가능한 별칭이 없는 경우, 가입한 시점에서 같은 닉네임으로 가입한 사람의 수 x를 계산한다.
        if (!gotAlias) {
            sb.append(map.get(nickName).getFullNickName()).append("\n");
        }
    }
}
