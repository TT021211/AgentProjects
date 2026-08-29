package com.cuixing.common.core.utils.file;

/**
 * 媒体类型工具类
 * 
 * @author cuixing
 */
public class MimeTypeUtils
{
    public static final String IMAGE_PNG = "image/png";

    public static final String IMAGE_JPG = "image/jpg";

    public static final String IMAGE_JPEG = "image/jpeg";

    public static final String IMAGE_BMP = "image/bmp";

    public static final String IMAGE_GIF = "image/gif";

    public static final String[] IMAGE_EXTENSION = { "bmp", "gif", "jpg", "jpeg", "png" };

    public static final String[] FLASH_EXTENSION = { "swf", "flv" };

    public static final String[] MEDIA_EXTENSION = { "swf", "flv", "mp3", "wav", "wma", "wmv", "mid", "avi", "mpg",
            "asf", "rm", "rmvb" };

    public static final String[] VIDEO_EXTENSION = { "mp4", "avi", "rmvb" };

    public static final String[] DEFAULT_ALLOWED_EXTENSION = {
            // 图片
            "bmp", "gif", "jpg", "jpeg", "png",
            // word excel powerpoint
            "doc", "docx", "xls", "xlsx","xlsm", "ppt", "pptx", "html", "htm", "txt","csv", "tsv", "dotm", "xlt", "xltm", "dot", "dotx", "xlam", "xla", "pages",
            //国产办公软件/OpenOffice、LibreOffice 办公文档
            "wps", "dps", "et", "ett", "wpt","odt", "ods", "ots", "odp", "otp", "six", "ott", "fodt", "fods",
            // 压缩文件
            "rar", "zip", "gz", "bz2",
            //流程图/软件模型等文件
            "vsd", "vsdx","xmind","bpmn",
            // 视频格式
            "mp4", "avi", "rmvb","mp3","wav","flv",
            // pdf
            "pdf","ofd", "rtf" ,
    };

    public static String getExtension(String prefix)
    {
        switch (prefix)
        {
            case IMAGE_PNG:
                return "png";
            case IMAGE_JPG:
                return "jpg";
            case IMAGE_JPEG:
                return "jpeg";
            case IMAGE_BMP:
                return "bmp";
            case IMAGE_GIF:
                return "gif";
            default:
                return "";
        }
    }
}
