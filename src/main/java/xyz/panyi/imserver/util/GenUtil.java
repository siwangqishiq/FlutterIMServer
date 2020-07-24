package xyz.panyi.imserver.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

/**
 * 生成唯一标识
 */
public final class GenUtil {
    private final static SimpleDateFormat sdf = new SimpleDateFormat("MMdd");

    public final static String GEN_BY_SERVER = "11";
    public final static String GEN_BY_MOBILE = "22";

    public static long genUuid(){
        // 1.开头两位，标识业务代码或机器代码（可变参数）
        String machineId = GEN_BY_SERVER;
        // 2.中间四位整数，标识日期
        String dayTime = sdf.format(new Date());
        // 3.生成uuid的hashCode值
        int hashCode = UUID.randomUUID().toString().hashCode();
        // 4.可能为负数
        if(hashCode < 0){
            hashCode = -hashCode;
        }
        // 5.算法处理: 0-代表前面补充0; 10-代表长度为10; d-代表参数为正数型
        long value = Long.parseLong(machineId + dayTime + String.format("%010d", hashCode));
        return value;
    }
}
