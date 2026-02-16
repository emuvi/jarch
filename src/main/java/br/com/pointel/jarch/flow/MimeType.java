package br.com.pointel.jarch.flow;

import org.apache.commons.io.FilenameUtils;

public enum MimeType {

    PDF("application/pdf"),
    DOCX("application/vnd.openxmlformats-officedocument.wordprocessingml.document"),
    DOC("application/msword"),
    PPTX("application/vnd.openxmlformats-officedocument.presentationml.presentation"),
    PPT("application/vnd.ms-powerpoint"),
    XLS("application/vnd.ms-excel"),
    XLSX("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"),
    TXT("text/plain"),
    HTML("text/html"),
    XML("application/xml"),
    JSON("application/json"),
    ZIP("application/zip"),
    RAR("application/x-rar-compressed"),
    GZIP("application/gzip"),
    TAR("application/x-tar"),
    GZ("application/x-gzip"),
    CSV("text/csv"),
    BMP("image/bmp"),
    GIF("image/gif"),
    JPG("image/jpeg"),
    JPEG("image/jpeg"),
    PNG("image/png"),
    TIFF("image/tiff"),
    PSD("image/vnd.adobe.photoshop"),
    AI("application/postscript"),
    EPS("application/postscript"),
    PS("application/postscript"),
    SVG("image/svg+xml"),
    SWF("application/x-shockwave-flash"),
    FLV("video/x-flv"),
    MP4("video/mp4"),
    MOV("video/quicktime"),
    MKV("video/x-matroska"),
    WEBM("video/webm"),
    AVI("video/x-msvideo"),
    MPEG("video/mpeg"),
    MPG("video/mpeg"),
    WMV("video/x-ms-wmv"),
    WMA("audio/x-ms-wma"),
    WAV("audio/wav"),
    MP3("audio/mpeg"),
    OGG("audio/ogg"),
    FLAC("audio/flac"),
    AAC("audio/aac"),
    M4A("audio/x-m4a"),
    AMR("audio/amr");

    private final String code;

    private MimeType(String code) {
        this.code = code;
    }

    public String code() {
        return code;
    }

    public static MimeType of(String path) {
        var extension = FilenameUtils.getExtension(path).toLowerCase();
        for (MimeType mimeType : MimeType.values()) {
            if (mimeType.toString().toLowerCase().equals(extension)) {
                return mimeType;
            }
        }
        return null;
    }
    
}
