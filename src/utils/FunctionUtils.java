/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

import com.kyt.framework.config.LogUtil;
import com.kyt.framework.util.ConvertUtils;
import com.kyt.framework.util.DateTimeUtils;
import com.kyt.framework.util.StringUtils;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import model.UserAdminDA;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang.WordUtils;
import org.apache.log4j.Logger;

/**
 *
 * @author Y Sa
 */
public class FunctionUtils {

    private static Logger logger = LogUtil.getLogger(FunctionUtils.class);
    private static final char[] _arrAfterUpperCase = new char[]{' ', '(', '"', ':', ';', '<', '>', '?', ',', '/', '\\', '&', '%', '$', '#', '@', '!'};
    public static final List<Integer> listRightViews = listActionNotCheckFromDB();
    
    private static List<Integer> listActionNotCheckFromDB() {
        List<Integer> ls = new ArrayList<>();
        ls.add(PermissionDefine.UserAdminView);
        ls.add(PermissionDefine.UserView);
        ls.add(PermissionDefine.ArticleView);
        return ls;
    }
    
    static public String DoMD5(String md5) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] array = md.digest(md5.getBytes());
            StringBuffer sb = new StringBuffer();
            for (int i = 0; i < array.length; ++i) {
                sb.append(Integer.toHexString((array[i] & 0xFF) | 0x100).substring(1, 3));
            }
            return sb.toString();
        } catch (Exception e) {
            logger.error(LogUtil.stackTrace(e));
        }
        return "";
    }

    
    static public String ToStringView(long number) {
        return String.format("%,8d%n", number);
    }


    public static List<Long> ParseStringToListLong(String str) {
        List<Long> result = new ArrayList<>();
        if (StringUtils.isEmpty(str)) {
            return result;
        }
        String[] arr = str.split(",");
        for (int i = 0; i < arr.length; ++i) {
            if (!StringUtils.isEmpty(arr[i])) {
                result.add(ConvertUtils.toLong(arr[i].trim()));
            }
        }
        return result;
    }

    public static List<Short> ParseStringToListShort(String str) {
        List<Short> result = new ArrayList<>();
        if (StringUtils.isEmpty(str)) {
            return result;
        }
        String[] arr = str.split(",");
        for (int i = 0; i < arr.length; ++i) {
            if (!StringUtils.isEmpty(arr[i])) {
                result.add(ConvertUtils.toShort(arr[i].trim()));
            }
        }
        return result;
    }

    public static String ParseListLongToString(List<Long> ls) {
        String result = "";
        if (ls == null || ls.size() <= 0) {
            return result;
        }
        for (int i = 0; i < ls.size(); ++i) {
            result += ConvertUtils.toString(ls.get(i)) + ",";
        }
        return result.substring(0, result.length() - 1);
    }

    public static long GetDateTimeFromString(String strDate, boolean isEndDate) {
        long dateReturn = DateTimeUtils.getMilisecondsMin();

        if (!StringUtils.isEmpty(strDate)) {
            int dateNum = ConvertUtils.toInt(strDate.substring(0, 2));
            int monthNum = ConvertUtils.toInt(strDate.substring(3, 5)) - 1;
            int yearNum = ConvertUtils.toInt(strDate.substring(6, 10)) - 1900;
            Date tmpDate = new Date(yearNum, monthNum, dateNum);
            dateReturn = tmpDate != null ? DateTimeUtils.getMiliseconds(tmpDate) : DateTimeUtils.getMilisecondsMin();
            dateReturn = isEndDate ? dateReturn + ((24 * 60) - 1) * 60 * 1000 : dateReturn;
        }
        return dateReturn;
    }

    public static Date GetDateTimeFromString(String strDate) {
        Date dateReturn = DateTimeUtils.MINDATE;

        if (!StringUtils.isEmpty(strDate)) {
            int dateNum = ConvertUtils.toInt(strDate.substring(0, 2));
            int monthNum = ConvertUtils.toInt(strDate.substring(3, 5)) - 1;
            int yearNum = ConvertUtils.toInt(strDate.substring(6, 10)) - 1900;
            Date tmpDate = new Date(yearNum, monthNum, dateNum);
            dateReturn = tmpDate != null ? DateTimeUtils.getDateTime(tmpDate) : DateTimeUtils.MINDATE;
        }

        return dateReturn;
    }

    public static Date GetDateTimeFromString(String strDate, String time) {
        Date dateReturn = DateTimeUtils.MINDATE;

        if (!StringUtils.isEmpty(strDate)) {
            int dateNum = ConvertUtils.toInt(strDate.substring(0, 2));
            int monthNum = ConvertUtils.toInt(strDate.substring(3, 5)) - 1;
            int yearNum = ConvertUtils.toInt(strDate.substring(6, 10)) - 1900;
            int h = 0;
            int m = 0;
            int s = 0;
            if (!StringUtils.isEmpty(time)) {
                h = ConvertUtils.toInt(time.substring(0, 2), 0);
                m = ConvertUtils.toInt(time.substring(3, 5), 0);
            }
            Date tmpDate = new Date(yearNum, monthNum, dateNum, h, m, 0);
            dateReturn = tmpDate != null ? DateTimeUtils.getDateTime(tmpDate) : DateTimeUtils.MINDATE;
        }
        return dateReturn;
    }

    public static long GetDateFromStringWithHour(String date) {
        return DateTimeUtils.getDateTime(date, "dd/MM/yyyy HH:mm").getTime();
    }

    public static String DateToStringViewWithHour(long date) {
        SimpleDateFormat ad = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        return ad.format(date);
    }

    public static String DateToStringView(long date) {
        SimpleDateFormat ad = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        return ad.format(date);
    }

    public static String DateToStringView(Date date) {
        SimpleDateFormat ad = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        return ad.format(date);
    }

    public static String DateToVNStringView(long date) {
        Date _date = DateTimeUtils.getDateTime(date);
        return "ngày " + DateTimeUtils.getDay(_date) + " tháng " + DateTimeUtils.getMonth(_date) + " năm " + DateTimeUtils.getYear(_date);

    }

    public static String uppercaseFirstLetters(String str) {
        if (IsNullOrEmpty(str)) {
            return "";
        }
        return WordUtils.capitalizeFully(str, _arrAfterUpperCase);
    }

    public static String ConvertToUTF8(String str) throws UnsupportedEncodingException {
        if (StringUtils.isEmpty(str)) {
            return "";
        }
        return new String(str.getBytes("ISO-8859-1"), "UTF-8");
    }

    

    public static String StringTrim(String inputString, int lenght) {

        if (inputString != null && inputString.length() > lenght) {
            return inputString.substring(0, lenght) + "...";
        } else {
            return inputString;
        }

    }

    public static String TimeToStringView(long inTime) {
        String strMinutes = (inTime / 60) >= 10 ? ConvertUtils.toString(inTime / 60) : "0" + ConvertUtils.toString(inTime / 60);
        String strSecond = (inTime % 60) >= 10 ? ConvertUtils.toString(inTime % 60) : "0" + ConvertUtils.toString(inTime % 60);
        return strMinutes + ":" + strSecond;
    }

    public static String decodeBase64(String text) {
        byte[] decodedByte = Base64.decodeBase64(text);
        return new String(decodedByte);
    }

    public static String encodeBase64(String text) {
        byte[] encodedByte = Base64.encodeBase64(text.getBytes());
        return new String(encodedByte);
    }

    public static List<Long> GetSubListLong(List<Long> list, int pageIndex, int pageSize) {
        int iStart = (pageIndex - 1) * pageSize;
        int iEnd = (pageIndex * pageSize);
        if (iEnd >= list.size()) {
            iEnd = list.size();
        }
        if (iStart >= list.size()) {
            iStart = list.size();
        }
        return list.subList(iStart, iEnd);
    }

    public static String getShortString(String str, int num) {
        if (str.length() <= num) {
            return str;
        }
        return str.substring(0, num) + "...";
    }

    public static String UrlEncode(String url) {
        try {
            if (url == null || url.isEmpty()) {
                return "";
            }
            return URLEncoder.encode(url, "UTF-8");
        } catch (Exception ex) {
            logger.error(LogUtil.stackTrace(ex));
        }
        return "";
    }

    public static String UrlDecode(String url) {
        try {
            if (url == null || url.isEmpty()) {
                return "";
            }
            return URLDecoder.decode(url, "UTF-8");
        } catch (Exception ex) {
            logger.error(LogUtil.stackTrace(ex));
        }
        return "";
    }

    public static String QuoteEncode(String text) {
        if (text == null) {
            return "";
        }
        text = text.replace("&", "&#38;");
        text = text.replace("'", "&#39;");
        text = text.replace("\"", "&#34;");
        text = text.replace(">", "&#62;");
        text = text.replace("<", "&#60;");

        return text;
    }

    public static String QuoteDecode(String text) {
        if (text == null) {
            return "";
        }
        text = text.replace("&amp;", "&");
        text = text.replace("&gt;", ">");
        text = text.replace("&lt;", "<");
        text = text.replace("&apos;", "'");
        text = text.replace("&#quot;", "\"");

        text = text.replace("&#38;", "&");
        text = text.replace("&#62;", ">");
        text = text.replace("&#60;", "<");
        text = text.replace("&#39;", "'");
        text = text.replace("&#34;", "\"");

        return text;
    }

    

    public static boolean IsNullOrEmpty(String str) {
        return str == null || str.length() == 0;
    }

    // playlist
    public static String RestfulPostMethod(String strUrl, String paramQuery) {
        String result = "";
        try {
            URL url = new URL(strUrl);
            HttpURLConnection urlcon = (HttpURLConnection) url.openConnection();
            urlcon.setRequestMethod("POST");
            urlcon.setRequestProperty("Content-type", "application/x-www-form-urlencoded");
            urlcon.setDoOutput(true);
            urlcon.setDoInput(true);
            PrintWriter pout = new PrintWriter(new OutputStreamWriter(urlcon.getOutputStream(), "utf-8"), true);
            pout.print(paramQuery);
            pout.flush();
            BufferedReader reader = new BufferedReader(new InputStreamReader(urlcon.getInputStream()));

            String line;
            while ((line = reader.readLine()) != null) {
                result += line + "\n";
            }
        } catch (Exception e) {
            logger.error(LogUtil.stackTrace(e));
        }
        return result;
    }

    private static final SimpleDateFormat[] SupportedFormats = new SimpleDateFormat[]{
        new SimpleDateFormat("dd/MM/yyyy HH:mm"), new SimpleDateFormat("dd/MM/yyyy"), new SimpleDateFormat("MM/yyyy"), new SimpleDateFormat("yyyy"),
        new SimpleDateFormat("dd-MM-yyyy"), new SimpleDateFormat("MM-yyyy")
    };

    public static long getTime(String data) {
        Date d = null;
        for (int i = 0; i < SupportedFormats.length; i++) {
            try {
                d = SupportedFormats[i].parse(data);
                break;
            } catch (ParseException ex) {
            }
        }
        if (d == null) {
            return 0;
        }
        return d.getTime();
    }

    public static List<String> ParseStringToListString(String str) {
        List<String> result = new ArrayList<>();
        if (IsNullOrEmpty(str)) {
            return result;
        }
        String[] arr = str.split(",");
        for (int i = 0; i < arr.length; ++i) {
            if (!IsNullOrEmpty(arr[i])) {
                result.add(ConvertUtils.toString(arr[i].trim()));
            }
        }
        return result;
    }

    
    public static List<Short> getWeekAndYear(String str) {
        List<Short> result = new ArrayList<>();
        if (FunctionUtils.IsNullOrEmpty(str)) {
            Calendar cal = Calendar.getInstance();
            int curWeek = cal.get(Calendar.WEEK_OF_YEAR);
            int curMonth = cal.get(Calendar.MONTH);
            int curYear = cal.get(Calendar.YEAR);
            if (curMonth == 11 && curWeek == 1) {
                curYear += 1;
            }
            result.add((short) curWeek);
            result.add((short) curYear);
        } else {
            Pattern p = Pattern.compile("-?\\d+");
            Matcher m = p.matcher(str);

            while (m.find()) {
                if (Short.valueOf(m.group()) == 53) {
                    result.add((short) 1);
                } else {
                    result.add(Short.valueOf(m.group()));
                }
            }
        }
        return result;
    }

    public static boolean IsValidEmailAddress(String emailAddress) {
        String expression = "^[\\w\\-]([\\.\\w])+[\\w]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
        CharSequence inputStr = emailAddress;
        Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(inputStr);
        return matcher.matches();

    }    
    
    public static String generatePassword(String password){                
        return FunctionUtils.DoMD5(Const.User.SALT + password);
    }
    
    static public String GetLoginUserName(HttpServletRequest request) {
        HttpSession session = request.getSession();
        return session != null ? ConvertUtils.toString(session.getAttribute(EnumUtils.SESSION_USER_LOGIN)) : "";
    }

    
    static public Long GetLoginUser(HttpServletRequest req) {
        HttpSession session = req.getSession();
        return session != null ? ConvertUtils.toLong(session.getAttribute(EnumUtils.SESSION_USERID_LOGIN)) : 0L;
    }
    
    
    public static void main(String[] args) {
        System.out.println(generatePassword("123456"));
    }
    
    
     static public boolean IsPermit(HttpServletRequest request, int action) {
//        Long UserLogin = GetLoginUser(request);
//        if (UserLogin <= 0) {
//            return false;
//        }
//        List<Integer> listRight = new ArrayList<>();
//        if (listRightViews.contains(action)) {
//            HttpSession session = request.getSession();
//            listRight = session != null ? StringUtils.toList(ConvertUtils.toString(session.getAttribute(EnumUtils.SESSION_USER_RIGHT)), ",", Integer.class) : listRight;
//        } else {
//            listRight = UserAdminDA.utils.GetUserPermisionFromDB(UserLogin);
//        }
//        return listRight.contains(action);
        return true;
    }

    static public boolean IsPermit(HttpServletRequest request, List<Integer> actions) {
//        Long UserLogin = GetLoginUser(request);
//        if (UserLogin <= 0) {
//            return false;
//        }
//        List<Integer> listRight = new ArrayList<>();
//        if (listRightViews.containsAll(actions)) {
//            HttpSession session = request.getSession();
//            listRight = session != null ? StringUtils.toList(ConvertUtils.toString(session.getAttribute(EnumUtils.SESSION_USER_RIGHT)), ",", Integer.class) : listRight;
//        } else {
//            listRight = UserAdminDA.utils.GetUserPermisionFromDB(UserLogin);
//        }
//        return listRight.containsAll(actions);
        return true;
    }
    
    
}
