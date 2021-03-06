package com.example.withpet.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public enum SDF {

    /** Wed, 14 Jan 2015 05:46:36 GMT */
    _long("EEE, d MMM yyyy HH:mm:ss z", Locale.US),
    yy("yy"),
    yyyymm("yyyyMM"),
    yyyy("yyyy"),
    yyyy__("yyyy년"),

    weekofmonth("W"),

    mmdd("MMdd"),
    mmdd__("MM월dd일"),
    mmdd_2("MM.dd"),

    mm("MM"),
    m__("M월"),
    mm__("MM월"),

    d("d"),
    dd("dd"),
    dd__("dd일"),

    EEE_US("EEE", Locale.US),
    EEEE_US("EEEE", Locale.US),
    E("E"),
    E__("E요일"),

    emdhs("(E)M/d HH:mm"),
    mdeahmm("M/d(E) a h:mm"),

    mde("M/d(E)"),

    yyyy_mmdd("yyyy\nMM.dd"),
    yyyy__mmdd("yyyy.\nMM.dd"),

    yyyymmddE__("yyyy년 MM월 dd일 E요일"),

    yyyymmdd("yyyyMMdd"),
    yyyymmdd_("yyyy/MM/dd"),
    yyyymmdd__("yyyy년 MM월 dd일"),
    yyyymmdd_1("yyyy-MM-dd"),
    yyyymmdd_2("yyyy.MM.dd"),

    yyyymmddhhmm("yyyyMMddHHmm"),
    yyyymmddhhmm_1("yyyy-MM-dd HH:mm"),
    yyyymmddhhmm_2("yyyy.MM.dd HH:mm"),

    yyyymmddahmm_("yyyy/MM/dd a h:mm"),
    yyyymmddahmm_2("yyyy.MM.dd a h:mm"),
    yyyymmddhmma_2("yyyy.MM.dd h:mm a", Locale.US),

    yyyymmddhhmm__("yyyy년 MM월 dd일 HH:mm"),

    yyyymmddhhmmss("yyyyMMddHHmmss"),
    yyyymmddhhmmss_("yyyy/MM/dd HH:mm:ss"),
    yyyymmddhhmmss_1("yyyy-MM-dd HH:mm:ss"),
    yyyymmddhhmmss_2("yyyy.MM.dd HH:mm:ss"),
    ddmmyyyyhhmmss_2("dd/MM/yy HH:mm:ss"),

    yyyymm_("yyyy/MM"),
    yyyymm_2("yyyy.MM"),
    yyyymm__("yyyy년 MM월"),
    yyyymmddhhmmsssss_("yyyy/MM/dd HH:mm:ss.SSS"),
    yyyymmddhhmmsssss_1("yyyy-MM-dd HH:mm:ss.SSS"),

    mmddhhmm__("MM월dd일 HH:mm"),
    mmddhhmm_("MM/dd HH:mm"),

    hhmmss("HHmmss"),
    hhmmss_("HH:mm:ss"),
//    hhmmss_utc("HH:mm:ss", Locale.UK),
    hhmmssSS_("HH:mm:ss.SS"),

    ahm__("a h시 m분"),
    hmma_("h:mm a", Locale.US),
    ahmm_("a h:mm"),

    hhmm("HHmm"),
    hhmm_("HH:mm"),

    mmss("mm:ss"),
    mmssSSS("mm:ss.SSS"),
    mmssSS("mm:ss.SS"),
    mmssS("mm:ss.S"),
    ms("m:s"),
    hh("hh"),
    HH("HH"),
    mm2("mm"),
    yyyymmdd_hhmmss("yyyyMMdd-HHmmss"),

    ;

    public SimpleDateFormat sdf;
    public String format;

    SDF(String format) {
        this(format, Locale.getDefault());
    }

    SDF(String format, Locale locale) {
        this.format = format;
        sdf = new SimpleDateFormat(format, locale);
    }

    public String format(Date date) {
        if (date == null)
            return "";
        return sdf.format(date);
    }

    public String format(long milliseconds) {
        return format(new Date(milliseconds));
    }

    public String now() {
        return format(System.currentTimeMillis());
    }

//    public Date parseDate(String date) throws ParseException {
//        return sdf.parse(date);
//    }

    public long parse(String date) throws ParseException {
        return sdf.parse(date).getTime();
    }

    public long opt_parse(String date) {
        return opt_parse(date, 0L);
    }

    public long opt_parse(String date, long default_date) {
        try {
            return sdf.parse(date).getTime();
        } catch (Exception e) {
            return default_date;
        }
    }
}