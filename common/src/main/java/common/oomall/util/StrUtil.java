package common.oomall.util;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;
import java.util.UUID;


/**
 * @author ???
 */
public class StrUtil {

    /**
     * 随机获取数字和大写英文字母组合的字符串
     * @param size 返回的字符串的位数，如果小于1，则默认是6
     * @return String
     * @since 2015-09-25
     */
    public static String getRandomLetterAndDigital(int size){
        //去掉容易混淆字符：0与O,1与I
        String str = "23456789ABCDEFGHJKLMNPQRSTUVWXYZ";
        StringBuffer sb = new StringBuffer();
        if(size < 1){
            size = 6;
        }
        for(int i=0; i<size; i++){
            int ran = (int) (Math.random()*str.length());
            sb.append(str.charAt(ran));
        }
        return sb.toString().trim();
    }

    /**
     * 随机获取大/小写英文字母组合的字符串
     * @param size 返回的字符串的位数，如果小于1，则默认是6
     * @return String
     * @since 2015-09-25
     */
    public static String getLetter(int size){
        String str = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
        StringBuffer sb = new StringBuffer();
        if(size < 1){
            size = 6;
        }
        for(int i=0; i<size; i++){
            int ran = (int) (Math.random()*str.length());
            sb.append(str.charAt(ran));
        }
        return sb.toString().trim();
    }

    /**
     * 随机获取大写英文字母组合的字符串
     * @param size 返回的字符串的位数，如果小于1，则默认是6
     * @return String
     * @since 2015-09-25
     */
    public static String getUpperLetter(int size){
        String str = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        StringBuffer sb = new StringBuffer();
        if(size < 1){
            size = 6;
        }
        for(int i=0; i<size; i++){
            int ran = (int) (Math.random()*str.length());
            sb.append(str.charAt(ran));
        }
        return sb.toString().trim();
    }

    /**
     * 判断list是否为Null
     * @param list
     * @return
     */
    public static <T> boolean isNullList(List<T> list) {
        return (list == null);
    }


    /**
     * 判断数组是否为空
     * @param obj
     * @return
     */
    public static boolean isEmptyArray(Object[] obj) {
        return (obj == null || obj.length < 1);
    }

    /**
     * <p>Checks if a String is empty ("") or null.</p>
     *
     * <pre>
     * StringUtils.isEmpty(null)      = true
     * StringUtils.isEmpty("")        = true
     * StringUtils.isEmpty(" ")       = false
     * StringUtils.isEmpty("bob")     = false
     * StringUtils.isEmpty("  bob  ") = false
     * </pre>
     *
     * <p>NOTE: This method changed in Lang version 2.0.
     * It no longer trims the String.
     * That functionality is available in isBlank().</p>
     *
     * @param str  the String to check, may be null
     * @return <code>true</code> if the String is empty or null
     */
    public static boolean isEmpty(String str) {
        return str == null || str.length() == 0;
    }

    /**
     * <p>Checks if a String is whitespace, empty ("") or null.</p>
     *
     * <pre>
     * StringUtils.isBlank(null)      = true
     * StringUtils.isBlank("")        = true
     * StringUtils.isBlank(" ")       = true
     * StringUtils.isBlank("bob")     = false
     * StringUtils.isBlank("  bob  ") = false
     * </pre>
     *
     * @param str  the String to check, may be null
     * @return <code>true</code> if the String is null, empty or whitespace
     * @since 2.0
     */
    public static boolean isBlank(String str) {
        int strLen;
        if (str == null || (strLen = str.length()) == 0) {
            return true;
        }
        for (int i = 0; i < strLen; i++) {
            if ((!Character.isWhitespace(str.charAt(i)))) {
                return false;
            }
        }
        return true;
    }


    /**
     * <p>Checks if the String contains only whitespace.</p>
     *
     * <p><code>null</code> will return <code>false</code>.
     * An empty String ("") will return <code>true</code>.</p>
     *
     * <pre>
     * StringUtils.isWhitespace(null)   = false
     * StringUtils.isWhitespace("")     = true
     * StringUtils.isWhitespace("  ")   = true
     * StringUtils.isWhitespace("abc")  = false
     * StringUtils.isWhitespace("ab2c") = false
     * StringUtils.isWhitespace("ab-c") = false
     * </pre>
     *
     * @param str  the String to check, may be null
     * @return <code>true</code> if only contains whitespace, and is non-null
     * @since 2.0
     */
    public static boolean isWhitespace(String str) {
        if (str == null) {
            return false;
        }
        int sz = str.length();
        for (int i = 0; i < sz; i++) {
            if ((!Character.isWhitespace(str.charAt(i)))) {
                return false;
            }
        }
        return true;
    }


    /**
     * 变成中文括号
     * @param str
     * @return
     */
    public static String bracketToChinese(String str){
        if(isBlank(str)){
            return str;
        }
        String strTrim = str.trim();
        strTrim = strTrim.replaceAll("\\(", "（").replaceAll("\\)", "）");
        return strTrim;
    }

    /**
     * 变成英文括号
     * @param str
     * @return
     */
    public static String bracketToEnglish(String str){
        if(isBlank(str)){
            return str;
        }
        String strTrim = str.trim();
        strTrim = strTrim.replaceAll("（", "(").replaceAll("）", ")");
        return strTrim;
    }

    /**
     * 替换字符串
     * @param str
     * @param sourceStr，如果是特殊字符，如英文()、[]等，要使用\\(
     * @param targetStr
     * @return
     */
    public static String replaceStr(String str, String sourceStr, String targetStr){
        if(isBlank(str)){
            return str;
        }
        String strTrim = str.trim();
        strTrim = strTrim.replaceAll(sourceStr, targetStr);
        return strTrim;
    }

    /**
     * 根据idsString字符串，返回组装后的参数params
     * @param params List<Object>
     * @param idsString String
     * @return
     */
    public static List<Object> setIdsParams(List<Object> params, String idsString){
        if(StrUtil.isNullList(params) || StrUtil.isBlank(idsString)){
            throw new RuntimeException("参数不能为空");
        }
        String[] ids = idsString.split(",");
        for (String id : ids) {
            if(!StrUtil.isBlank(id)){
                params.add(id);
            }
        }
        return params;
    }

    /**
     * 把大写字母转换成_加小写字母（例：Q变为_q）
     * @param str 必须为一个字符
     * @return
     * @author lqy
     */
    public static String replaceUpperCase(String str){
        if(isEmpty(str)){
            return str;
        }
        return "_" + str.trim().toLowerCase();
    }

    public static void main(String[] args) throws Exception {
        /*System.out.println("getUUID = "+getUUID());
        System.out.println("getUUID = "+getUUID());
        System.out.println("getUUID = "+getUUID());
        System.out.println("getUUIDNumberOnly = "+getUUIDNumberOnly());*/
        //System.out.println(removeLastCode("     ab "));
    }


}
