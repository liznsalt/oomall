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
     * 随机获取数字字符串
     * @param size 返回的字符串的位数
     * @return String
     * @since 2015-09-25
     */
    public static String getRandomDigital(int size){
        String str = "1234567890";
        StringBuffer sb = new StringBuffer();
        if(size < 1){
            return null;
        }else{
            for(int i=0; i<size; i++){
                int ran = (int) (Math.random()*10);
                sb.append(str.charAt(ran));
            }
            return sb.toString().trim();
        }
    }

    /**
     * 获取随机数字，同getRandomDigital
     * @param size
     * @return
     */
    public static String getNumber(int size){
        return getRandomDigital(size);
    }

    /**
     * 生成年月日时分秒毫秒（20120905050602000）
     * @return
     * @since 2015-09-25
     */
    public static String getYYYYMMDDHHmmssmilliSecond(){
        StringBuffer str = new StringBuffer("");
        String strMonth = "";
        String strDate = "";
        String strHour = "";
        String strMinute = "";
        String strSecond = "";
        String strMilliSecond = "";
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH)+1;
        int date = cal.get(Calendar.DATE);
        int hour = cal.get(Calendar.HOUR_OF_DAY);
        int minute = cal.get(Calendar.MINUTE);
        int second = cal.get(Calendar.SECOND);
        int milliSecond = cal.get(Calendar.MILLISECOND);
        if(month < 10){
            strMonth = "0" + month;
        }else{
            strMonth = String.valueOf(month);
        }
        if(date < 10){
            strDate = "0" + date;
        }else{
            strDate = String.valueOf(date);
        }
        if(hour < 10){
            strHour = "0" + hour;
        }else{
            strHour = String.valueOf(hour);
        }
        if(minute < 10){
            strMinute = "0" + minute;
        }else{
            strMinute = String.valueOf(minute);
        }
        if(second < 10){
            strSecond = "0" + second;
        }else{
            strSecond = String.valueOf(second);
        }
        if(milliSecond < 10){
            strMilliSecond = "00" + milliSecond;
        }else if(milliSecond < 100){
            strMilliSecond = "0" + milliSecond;
        }else{
            strMilliSecond = String.valueOf(milliSecond);
        }
        return str.append(String.valueOf(year).toString().trim()).append(strMonth.trim()).append(strDate.trim()).append(strHour.trim()).append(strMinute.trim()).append(strSecond.trim()).append(strMilliSecond.trim()).toString();
    }

    /**
     * 生成年月日（20120905050602000）
     * @return
     * @since 2015-09-25
     */
    public static String getYYYYMMDD(){
        StringBuffer str = new StringBuffer("");
        String strMonth = "";
        String strDate = "";
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH)+1;
        int date = cal.get(Calendar.DATE);
        if(month < 10){
            strMonth = "0" + month;
        }else{
            strMonth = String.valueOf(month);
        }
        if(date < 10){
            strDate = "0" + date;
        }else{
            strDate = String.valueOf(date);
        }
        return str.append(String.valueOf(year).toString().trim()).append(strMonth.trim()).append(strDate.trim()).toString();
    }

    /**
     * 获取uuid，有横杠（36位）
     * @return
     * @since 2015-10-14
     */
    public static String getUUID(){
        return UUID.randomUUID().toString();
    }

    /**
     * 获取uuid，无横杠(32位)
     * @return
     * @author lqyao
     * @since 2015-10-14
     */
    public static String getUUIDNumberOnly(){
        return UUID.randomUUID().toString().replaceAll("-", "");
    }

    /**
     * 移除字符串最后一个字符
     * @return
     * @since 2015-10-14
     */
    public static String removeLastCode(String str){
        if(str == null || str.length() < 1){
            return str;
        }
        return str.substring(0, str.length()-1);
    }

    /**
     * 第一个字符变大写
     * @param str
     * @return
     */
    public static String firstCodeToUpperCase(String str){
        if(isBlank(str)){
            return str;
        }
        String strTrim = str.trim();
        return String.valueOf(strTrim.charAt(0)).toUpperCase() + strTrim.substring(1,strTrim.length());
    }

    /**
     * 获取字符串最后一个字符
     * @return
     * @since 2016-01-13
     */
    public static String getLastCode(String str){
        if(str == null || str.length() < 1){
            return "";
        }
        return str.substring(str.length()-1);
    }

    /**
     * 获取第一个id
     * @param str 字符串
     * @return id
     */
    public static String getFirstId(String str, String spiltCode) {
        if (spiltCode == null) {
            spiltCode = ",";
        }
        if(!StrUtil.isEmpty(str)){
            if (str.indexOf(spiltCode) > -1) {
                return str.substring(0, str.indexOf(spiltCode)).trim();
            }
        }
        return str;
    }

    /**
     * 去相同部分
     * @param originalStr 原字符串
     * @param deleteStr 需要去掉的字符串
     * @return string
     * @author lqy
     */
    public static String removeSamePart(String originalStr, String deleteStr) {
        if (originalStr != null && deleteStr != null) {
            originalStr = originalStr.replaceAll("\\(", "（");
            originalStr = originalStr.replaceAll("\\)", "）");
            originalStr = originalStr.replaceAll(" |　", "");
            deleteStr = deleteStr.replaceAll("\\(", "（");
            deleteStr = deleteStr.replaceAll("\\)", "）");
            deleteStr = deleteStr.replaceAll(" |　", "");
            if (originalStr.indexOf(deleteStr) > -1) {
                originalStr = originalStr.replaceAll(deleteStr, "");
            }
        }
        return originalStr;
    }

    /**
     * 拆分字符串获取数组
     * @param str 字符串
     * @param spiltCode 拆分符号
     * @return String[]
     */
    public static String[] getArrayAfterSpilt(String str, String spiltCode) {
        if (str == null || str.trim().equals("")) {
            return null;
        }else{
            if (spiltCode == null || spiltCode.trim().equals("")) {
                spiltCode = ",";
            }
            return str.split(spiltCode);
        }
    }

    /**
     * 拆分字符串获取Ids
     * @param idsString id字符串
     * @param spiltCode 拆分符号
     * @return ids
     */
    public static int[] getIdsAfterSpilt(String idsString, String spiltCode) {
        List<Integer> idList = new ArrayList<Integer>();
        if (idsString == null || idsString.trim().equals("")) {
            return null;
        } else {
            if (spiltCode == null || spiltCode.trim().equals("")) {
                spiltCode = ",";
            }
            String[] idArray = idsString.split(spiltCode);
            if (idArray != null && idArray.length > 0) {
                for (String string : idArray) {
                    if (string != null && !string.trim().equals("")) {
                        idList.add(Integer.parseInt(string.trim()));
                    }
                }
            }
        }
        if (idList != null && idList.size() > 0) {
            int[] ids = new int[idList.size()];
            for (int j = 0; j < idList.size(); j++) {
                ids[j] = idList.get(j);
            }
            return ids;
        }
        return null;
    }

    /**
     *
     * @param obj
     * @return obj == null;
     */
    public static boolean isNull(Object obj) {
        return obj == null;
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
     * 判断list是否为空
     * @param list
     * @return (list == null) || (list.size() < 1)
     */
    public static <T> boolean isEmptyList(List<T> list) {
        return (list == null) || (list.size() < 1);
    }

    /**
     * 判断Map是否为Null
     * @param map
     * @return
     */
    public static <K, V> boolean isNullMap(Map<K, V> map) {
        return (map == null);
    }

    /**
     * 判断Map是否为空
     * @param map
     * @return
     */
    public static <K, V> boolean isEmptyMap(Map<K, V> map) {
        return (map == null || map.size() < 1);
    }

    /**
     * 判断数组是否为Null
     * @param obj
     * @return
     */
    public static boolean isNullArray(Object[] obj) {
        return (obj == null);
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
            if ((Character.isWhitespace(str.charAt(i)) == false)) {
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
            if ((Character.isWhitespace(str.charAt(i)) == false)) {
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
     * 根据idsString组装in后面带？的sql
     * @param idsString String
     * @return
     */
    public static String getInSql(String idsString){
        if(StrUtil.isBlank(idsString)){
            throw new RuntimeException("参数不能为空");
        }
        String[] ids = idsString.split(",");
        StringBuffer sb = new StringBuffer("").append("(");
        for (String id : ids) {
            if(!StrUtil.isBlank(id)){
                sb.append("?").append(",");
            }
        }
        if(sb.indexOf(",") > -1){
            sb.deleteCharAt(sb.length() - 1);
        }
        sb.append(")");
        return sb.toString();
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
     * 根据idsString组装in后面带？的sql，并设置params参数值
     * @param params List<Object>
     * @param idsString String
     * @return
     */
    public static String getInSql(List<Object> params, String idsString){
        if(StrUtil.isNullList(params) || StrUtil.isBlank(idsString)){
            throw new RuntimeException("参数不能为空");
        }
        StringBuffer sb = new StringBuffer("").append("(");
        String[] ids = idsString.split(",");
        for (String id : ids) {
            if(!StrUtil.isBlank(id)){
                sb.append("?").append(",");
                params.add(id);
            }
        }
        if(sb.indexOf(",") > -1){
            sb.deleteCharAt(sb.length() - 1);
        }
        sb.append(")");
        return sb.toString();
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
