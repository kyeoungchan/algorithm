package mincoding.연락처DB;

import java.util.*;

/**
 * 연락처는 이름과 전화번호로 이루어져 있다.
 * 등록된 연락처들 중 이름 또는 전화번호가 같은 경우는 없다.
 * 폰에서 전화를 거는데, 연락처의 전화번호일 수 있고, 아닐 수 있다.
 * 로그에 기록되는 경우
 * 1. 연락처 등록
 * 2. 전화를 걸 때 => 연락처 DB에 없는 전화번호의 경우 번호만 로그에 기록된다.
 * 주어진 문자열로 시작하는 이름을 가진 연락처 검색 가능
 *   주어진 문자열로 시작하는 이름을 가진 연락처들 중 로그에 가장 최근에 기록된 순서로 최대 5개 보여줌. (중복 제외)
 * 주어진 번호로 시작하는 전화번호 검색 가능
 *   이때 주어진 번호로 시작하는 전화번호 중 로그에 가장 최근에 기록된 순서로 최대 5개 보여준다.
 *   전화번호가 연락처로 등록된 경우 이름과 함께, 그렇지 않은 경우 전화번호만 보여준다.
 * 삭제하면 로그에서도 아예 삭제된다.
 */
public class Solution_min_연락처DB {

    static class Contactinfo {
        String name, telephone;
        boolean removed;

        public void setData(String name, String telephone) {
            this.name = name;
            this.telephone = telephone;
            removed = false;
        }
    }

    private Contactinfo[] contactinfoes = new Contactinfo[13_001];
    private Map<String, List<Contactinfo>> listMap = new HashMap<>();
    private Map<String, Contactinfo> nameToContactinfo = new HashMap<>();
    private Map<String, Contactinfo> telephoneToContactinfo = new HashMap<>();
    private int idx;

    public void init() {
        idx = 0;
        listMap.clear();
        nameToContactinfo.clear();
        telephoneToContactinfo.clear();
    }

    public void add(String mName, String mTelephone) {
        Contactinfo contactInfo = createContactInfo(mName, mTelephone);
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < mName.length(); i++) {
            sb.append(mName.charAt(i));
            String str = sb.toString();
            if (!listMap.containsKey(str)) listMap.put(str, new ArrayList<>());
            listMap.get(str).add(contactInfo);
        }
        nameToContactinfo.put(mName, contactInfo);
        sb = new StringBuilder();
        for (int i = 0; i < mTelephone.length(); i++) {
            sb.append(mTelephone.charAt(i));
            String str = sb.toString();
            if (!listMap.containsKey(str)) listMap.put(str, new ArrayList<>());
            listMap.get(str).add(contactInfo);
        }
        telephoneToContactinfo.put(mTelephone, contactInfo);
    }

    private Contactinfo createContactInfo(String mName, String mTelephone) {
        if (contactinfoes[idx] == null) contactinfoes[idx]  = new Contactinfo();
        contactinfoes[idx].setData(mName, mTelephone);
        return contactinfoes[idx++];
    }

    public void remove(String mStr) {
        char firstChar = mStr.charAt(0);
        if (firstChar >= 'a' && firstChar <= 'z') {
            nameToContactinfo.get(mStr).removed = true;
        } else {
            telephoneToContactinfo.get(mStr).removed = true;
        }
    }

    public void call(String mStr) {
        StringBuilder sb = new StringBuilder();
        if (telephoneToContactinfo.containsKey(mStr)) {
            Contactinfo contactinfo = telephoneToContactinfo.get(mStr);
            for (int i = 0; i < mStr.length(); i++) {
                sb.append(mStr.charAt(i));
                String str = sb.toString();
                listMap.get(str).add(contactinfo);
            }
            String name = contactinfo.name;
            if (name == null) return;
            sb = new StringBuilder();
            for (int i = 0; i < name.length(); i++) {
                sb.append(name.charAt(i));
                String str = sb.toString();
                listMap.get(str).add(contactinfo);
            }
            return;
        }
        Contactinfo contactinfo = createContactInfo(null, mStr);
        for (int i = 0; i < mStr.length(); i++) {
            sb.append(mStr.charAt(i));
            String str = sb.toString();
            if (!listMap.containsKey(str)) listMap.put(str, new ArrayList<>());
            listMap.get(str).add(contactinfo);
        }
        telephoneToContactinfo.put(mStr, contactinfo);
    }

    public Main.Result search(String mStr) {
        Main.Result res = new Main.Result();
        List<Contactinfo> temp = new ArrayList<>();
        int i = 0;
        List<Contactinfo> list = listMap.get(mStr);
        if (list == null) {
            res.size = 0;
            return res;
        }
        for (int j = list.size() - 1; j > -1 && i < 5; j--) {
            Contactinfo contactinfo = list.get(j);
            if (contactinfo.removed || temp.contains(contactinfo)) continue;
            temp.add(contactinfo);
            res.mNameList[i] = contactinfo.name;
            res.mTelephoneList[i] = contactinfo.telephone;
            i++;
        }
        res.size = i;
        return res;
    }
}
