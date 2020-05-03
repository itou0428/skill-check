package q003;

import java.io.*;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Q003 集計と並べ替え
 *
 * 以下のデータファイルを読み込んで、出現する単語ごとに数をカウントし、アルファベット辞書順に並び変えて出力してください。
 * resources/q003/data.txt
 * 単語の条件は以下となります
 * - "I"以外は全て小文字で扱う（"My"と"my"は同じく"my"として扱う）
 * - 単数形と複数形のように少しでも文字列が異れば別単語として扱う（"dream"と"dreams"は別単語）
 * - アポストロフィーやハイフン付の単語は1単語として扱う（"isn't"や"dead-end"）
 *
 * 出力形式:単語=数
 *
[出力イメージ]
（省略）
highest=1
I=3
if=2
ignorance=1
（省略）

 * 参考
 * http://eikaiwa.dmm.com/blog/4690/
 */
public class Q003 {
    /**
     * データファイルを開く
     * resources/q003/data.txt
     */
    private static InputStream openDataFile() {
        return Q003.class.getResourceAsStream("data.txt");
    }

    public static void main(String[] args) {
        String line;
        StringBuilder sb = new StringBuilder();
        try (BufferedReader br = new BufferedReader(new InputStreamReader(openDataFile()))) {
            while ((line = br.readLine()) != null) {
                sb.append(line);
                sb.append(" ");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        List<String> words = Arrays.asList(sb.toString().split(" "));

        Map<String, Integer> resultMap = new TreeMap<>(String.CASE_INSENSITIVE_ORDER);
        for (String word : words) {
            String replaceWord = word.replace(",", "").replace(";", "").replace(".", "");
            if (! replaceWord.equals("I")) {
                replaceWord = replaceWord.toLowerCase();
            }
            if (resultMap.containsKey(replaceWord)) {
                Integer count = resultMap.get(replaceWord);
                resultMap.put(replaceWord, count + 1);
            } else {
                resultMap.put(replaceWord, 1);
            }
        }

        // output出力
        for (Map.Entry<String, Integer> entry : resultMap.entrySet()){
            System.out.println(entry.getValue() + "=" + entry.getKey());
        }
    }
}
// 完成までの時間: xx時間 xx分