package q005;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Q005 データクラスと様々な集計
 *
 * 以下のファイルを読み込んで、WorkDataクラスのインスタンスを作成してください。
 * resources/q005/data.txt
 * (先頭行はタイトルなので読み取りをスキップする)
 *
 * 読み込んだデータを以下で集計して出力してください。
 * (1) 役職別の合計作業時間
 * (2) Pコード別の合計作業時間
 * (3) 社員番号別の合計作業時間
 * 上記項目内での出力順は問いません。
 *
 * 作業時間は "xx時間xx分" の形式にしてください。
 * また、WorkDataクラスは自由に修正してください。
 *
[出力イメージ]
部長: xx時間xx分
課長: xx時間xx分
一般: xx時間xx分
Z-7-31100: xx時間xx分
I-7-31100: xx時間xx分
T-7-30002: xx時間xx分
（省略）
194033: xx時間xx分
195052: xx時間xx分
195066: xx時間xx分
（省略）
 */
public class Q005 {
    private static InputStream openDataFile() {
        return Q005.class.getResourceAsStream("data.txt");
    }

    public static void main(String[] args) throws IOException {
        List<WorkData> workList = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new InputStreamReader(openDataFile()))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] dataArray = line.split(",");
                WorkData workData = createWorkData(dataArray);
                if (workData != null) {
                    workList.add(workData);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        // 役職別
        Map<String, Integer> positonMap = new HashMap<>();
        List<String> postionList = workList.stream().map(WorkData::getPosition).distinct().collect(Collectors.toList());
        for (String targetPositon : postionList) {
            int sum = workList.stream()
                    .filter(v -> v.getPosition().equals(targetPositon))
                    .mapToInt(v -> v.getWorkTime())
                    .sum();
            positonMap.put(targetPositon, sum);
        }
        for (String key : positonMap.keySet()) {
            System.out.println(key + "=" + convertTimeFormat(positonMap.get(key)));
        }

        // Pコード別
        Map<String, Integer> pCodeMap = new HashMap<>();
        List<String> pCodeList = workList.stream().map(WorkData::getPCode).distinct().collect(Collectors.toList());
        for (String target : pCodeList) {
            int sum = workList.stream()
                    .filter(v -> v.getPCode().equals(target))
                    .mapToInt(v -> v.getWorkTime())
                    .sum();
            pCodeMap.put(target, sum);
        }
        for (String key : pCodeMap.keySet()) {
            System.out.println(key + "=" + convertTimeFormat(pCodeMap.get(key)));
        }

        // 社員番号別
        Map<String, Integer> numberMap = new HashMap<>();
        List<String> memberList = workList.stream().map(WorkData::getNumber).distinct().collect(Collectors.toList());
        for (String target : memberList) {
            int sum = workList.stream()
                    .filter(v -> v.getNumber().equals(target))
                    .mapToInt(v -> v.getWorkTime())
                    .sum();
            numberMap.put(target, sum);
        }
        for (String key : numberMap.keySet()) {
            System.out.println(key + "=" + convertTimeFormat(numberMap.get(key)));
        }
    }

    private static WorkData createWorkData(String[] dataArray) {
        try {
            return WorkData.builder()
                    .number(dataArray[0])
                    .department(dataArray[1])
                    .position(dataArray[2])
                    .pCode(dataArray[3])
                    .workTime(Integer.parseInt(dataArray[4]))
                    .build();
        // フォーマットがミスしてる行などの配列参照タイミングでくる
        } catch (Exception ex) {
            return null;
        }
    }

    private static String convertTimeFormat(int time) {
        return (time / 60) + "時間" + (time - ((time / 60) * 60)) + "分";
    }
}
// 完成までの時間: xx時間 xx分