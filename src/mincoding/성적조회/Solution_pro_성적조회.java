package mincoding.성적조회;

import java.util.*;

public class Solution_pro_성적조회 {
    static class Student implements Comparable<Student> {
        private int id;
        private int grade;
        private String gender;
        private int score;

        public Student() {
        }

        public Student(int id, int score) {
            this.id = id;
            this.score = score;
        }

        public void setData(int id, int grade, String gender, int score) {
            this.id = id;
            this.grade = grade;
            this.gender = gender;
            this.score = score;
        }

        @Override
        public int compareTo(Student o) {
            return score == o.score ? id - o.id : score - o.score;
        }
    }

    private Student[] students = new Student[80_000]; // 각 함수는 8,000번 호출이므로 학생 수도 최대 8000명
    private int studentIdx;
    private Map<Integer, Integer> studentIdxMap = new HashMap<>();
    private TreeSet<Student>[][] ts = new TreeSet[3][2]; // 1~3학년, 남녀

    public void init() {
        studentIdx = -1;
        studentIdxMap.clear();
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 2; j++) {
                ts[i][j] = new TreeSet<>();
            }
        }
    }

    /**
     * 이미 점수가 기록되어있는 학생의 ID는 주어지지 않는다. 하지만 삭제된 학생은 다시 추가될 수 있다.
     * @param mId 학생 ID
     * @param mGrade 학년
     * @param mGender 성별 "male" 또는 "female"
     * @param mScore 점수
     * @return grade 학년 gender인 학생 중에서 가장 점수가 높은 학생의 ID. 같은 점수가 여러명이라면 ID가 가장 큰 값
     */
    public int addScore(int mId, int mGrade, char mGender[], int mScore) {
        int genderNumber = maleOrFemale(mGender[0]);
        String gender = genderNumber == 0 ? "male" : "female";
        ts[mGrade - 1][genderNumber].add(getStudent(mId, mGrade, gender, mScore));
        return ts[mGrade - 1][genderNumber].last().id;
    }

    private Student getStudent(int id, int grade, String gender, int score) {
        studentIdx++;
        studentIdxMap.put(id, studentIdx);
        if (students[studentIdx] == null) students[studentIdx] = new Student();
        students[studentIdx].setData(id, grade, gender, score);
        return students[studentIdx];
    }

    private int maleOrFemale(char gender) {
        if (gender == 'm') return 0;
        else return 1;
    }

    /**
     * 삭제
     * @param mId 학생 ID
     * @return 삭제된 id 학생의 학년과 성별이 동일한 학생 중에서 가장 점수가 낮은 학생의 ID 반환. 여러명이라면 그 중에서 ID가 가장 작은 값 반환. 기록되어있지 않은 학생이라면 0 반환
     */
    public int removeScore(int mId) {
        if (!studentIdxMap.containsKey(mId)) return 0;
        Student target = students[studentIdxMap.get(mId)];

        studentIdxMap.remove(mId);
//        students[studentIdxMap.get(mId)] = null;
        // new 연산자를 줄이기 위해서 삭제 관리는 HashMap으로만!

        int grade = target.grade;
        String gender = target.gender;
        ts[grade - 1][maleOrFemale(gender.charAt(0))].remove(target);
        if (ts[grade - 1][maleOrFemale(gender.charAt(0))].isEmpty()) return 0;
        return ts[grade - 1][maleOrFemale(gender.charAt(0))].first().id;
    }

    /**
     * 학년(1~3)과 성별(남/여)를 만족하면서, 특정 점수 이상의 학생 중에서 score가 가장 낮은 학생의 ID 반환
     * 없다면 0 반환
     * @param mGradeCnt
     * @param mGrade
     * @param mGenderCnt
     * @param mGender
     * @param mScore
     * @return
     */
    public int get(int mGradeCnt, int mGrade[], int mGenderCnt, char mGender[][], int mScore) {
        TreeSet<Student> temp = new TreeSet<>();
        Student fake = new Student(0, mScore);
        for (int i = 0; i < mGradeCnt; i++) {
            int grade = mGrade[i];
            for (int j = 0; j < mGenderCnt; j++) {
                char[] gender = mGender[j];
                Student addingStudent = ts[grade - 1][maleOrFemale(gender[0])].higher(fake);
                if (addingStudent != null) temp.add(addingStudent);
            }
        }
        if (temp.isEmpty()) return 0;
        return temp.first().id;
    }
}
