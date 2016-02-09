package homhom.lib.emojiboard.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipException;
import java.util.zip.ZipFile;

/**
 * Created by linhonghong on 2016/2/1.
 */
public class FileUtil {

    private static final int BUFF_SIZE = 1024; // 1K Byte

    public static final String separator;
    public static final char separatorChar;

    static {
        separatorChar = System.getProperty("file.separator", "/").charAt(0);
        separator = String.valueOf(separatorChar);
    }

    /**
     * 获得文件后缀名
     * @param path
     * @return
     */
    public static String getExtensions(String path){
        String extensions = path.substring(path.lastIndexOf(".") + 1);
        return extensions;
    }

    public static String getFileName(String path){
        int separatorIndex = path.lastIndexOf(separator);
        return (separatorIndex < 0) ? path : path.substring(separatorIndex + 1, path.length());
    }

    /**
     * 获得当前文件所在文件夹路径
     * @param path
     * @return
     */
    public static String getFileCurrentDirectoryPath(String path){
        int separatorIndex = path.lastIndexOf(separator);
        return (separatorIndex < 0) ? null : path.substring(0, separatorIndex + 1);
    }

    /**
     * 解压文件
     * @param zipFile 需要解压的文件
     * @param folderPath 解压后放置的文件夹位置
     * @throws ZipException
     * @throws IOException
     */
    public static void upZipFile(File zipFile, String folderPath)
            throws ZipException, IOException {
        //创建解压放置的文件夹路径
        File desDir = new File(folderPath);
        if (!desDir.exists()) {
            desDir.mkdirs();
        }
        ZipFile zf = new ZipFile(zipFile);
        for (Enumeration<?> entries = zf.entries(); entries.hasMoreElements();) {
            ZipEntry entry = ((ZipEntry) entries.nextElement());
            InputStream in = zf.getInputStream(entry);
            String str = folderPath + File.separator + entry.getName();
            str = new String(str.getBytes("8859_1"), "GB2312");
            File desFile = new File(str);
            if (!desFile.exists()) {
                File fileParentDir = desFile.getParentFile();
                if (!fileParentDir.exists()) {
                    fileParentDir.mkdirs();
                }
                desFile.createNewFile();
            }
            OutputStream out = new FileOutputStream(desFile);
            byte buffer[] = new byte[BUFF_SIZE];
            int realLength;
            while ((realLength = in.read(buffer)) > 0) {
                out.write(buffer, 0, realLength);
            }
            in.close();
            out.close();
        }
        zf.close();
    }
}
