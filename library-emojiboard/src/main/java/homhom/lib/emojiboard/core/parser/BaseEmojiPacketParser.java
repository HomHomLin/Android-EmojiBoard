package homhom.lib.emojiboard.core.parser;

import android.text.TextUtils;

import java.io.File;
import java.util.ArrayList;

import homhom.lib.emojiboard.core.EmojiBoardConfiguration;
import homhom.lib.emojiboard.mgr.EmojiBoardFixer;
import homhom.lib.emojiboard.util.FileUtil;

/**
 * 表情包解析器
 * 1.先查找hem后缀的zip文件
 * 2.解压后查找带有hem_开头的文件夹
 * 3.查找带有js后缀的文件并读取配置文件
 * 我们默认这样考虑，简单化：目录下有zip，我们就解压出来，默认当作解压完就会删掉文件，所以不存在没有被解压的zip
 * 表情包格式有好多种
 * Created by linhonghong on 2016/2/1.
 */
public abstract class BaseEmojiPacketParser {

    public EmojiBoardConfiguration mEmojiBoardConfiguration;
    public String mRootPath;
    //为什么要设计成两个呢，因为这样加载速度明显可以直接跳过判断是否zip和已经解压的
    public ArrayList<File> mEmojiPacketZipPath;//保存该目录下所有emojipacketZip的路径
    public ArrayList<File> mEmojiPacketDirectoryPath;//保存该目录下所有emojipacket的路径

    public BaseEmojiPacketParser(){

    }

    public void init(){
        try {
            mEmojiBoardConfiguration = EmojiBoardFixer.getInstance().getEmojiBoardConfiguration();
            onCreate();
            loadFiles();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void loadFiles() throws Exception{
        if(mEmojiBoardConfiguration == null){
            throw new Exception("EmojiBoardConfiguration is null");
        }
        mRootPath = mEmojiBoardConfiguration.getEmojiDirectory();
        if(TextUtils.isEmpty(mRootPath)){
            throw new Exception("RootPath is null");
        }
        findEmojiPacketsZip(getFileList());
        parser();
    }

    private File[] getFileList() throws Exception{
        File root = new File(mRootPath);
        if(!root.exists()){
            throw new Exception("Emoji Directory is not exist");
        }
        if(!root.isDirectory()){
            throw new Exception("Emoji Directory which set is not a directory");
        }
        File[] files = root.listFiles();
        return files;
    }

    /**
     * 寻找没有解压的表情包
     */
    private void findEmojiPacketsZip(File[] files){
        if(mEmojiPacketZipPath != null){
            mEmojiPacketZipPath.clear();
        }else{
            mEmojiPacketZipPath = new ArrayList<>();
        }
        for(File file : files){
            String extensions = FileUtil.getExtensions(file.getAbsolutePath());
            if(!file.exists()){
                //不存在了
                continue;
            }
            if(!file.isDirectory()){
                if (extensions.equalsIgnoreCase(mEmojiBoardConfiguration.EMOJI_PACKET_EXTENSIONS)) {
                    //如果后缀是zip包后缀，则需要解压，加入到zip列表
                    mEmojiPacketZipPath.add(file);
                }
            }
        }
    }

    /**
     * 开始解析，解析器
     */
    private void parser(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    onZip(mEmojiPacketZipPath);
                    findEmojiPacketDirectory(getFileList());
                    onParse(mEmojiPacketDirectoryPath);
                }catch (Exception e){
                    e.printStackTrace();
                }
                release();
            }
        }).start();
    }

    /**
     * 查找解压完的emojipacket
     * @param files
     */
    private void findEmojiPacketDirectory(File[] files){
        if(mEmojiPacketDirectoryPath != null){
            mEmojiPacketDirectoryPath.clear();
        }else{
            mEmojiPacketDirectoryPath = new ArrayList<>();
        }
        for(File file : files){
            if(!file.exists()){
                //不存在了
                continue;
            }
            if(file.isDirectory()){
                if(file.getName().startsWith(mEmojiBoardConfiguration.EMOJI_PACKET_PRE_FIX)) {
                    //如果是文件夹，并且前缀是hem
                    mEmojiPacketDirectoryPath.add(file);
                }

            }
        }
    }

    /**
     * 最初始化
     */
    public abstract void onCreate();

    /**
     * 解析
     */
    public abstract void onParse(ArrayList<File> emojiPacketDirectory);

    /**
     * 解压表情包
     */
    public abstract void onZip(ArrayList<File> emojiPacketZipPath) throws Exception;

    /**
     * 释放
     */
    public abstract void release();
}
