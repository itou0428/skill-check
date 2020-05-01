package q005;

import lombok.Builder;
import lombok.Data;

/**
 * 作業時間管理クラス
 * 自由に修正してかまいません
 */
@Builder
@Data
public class WorkData {
    /** 社員番号 */
    private String number;

    /** 部署 */
    private String department;

    /** 役職 */
    private String position;

    /** Pコード */
    private String pCode;

    /** 作業時間(分) */
    private int workTime;

}
