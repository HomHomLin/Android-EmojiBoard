package homhom.lib.emojiboard.core.parser;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FilenameFilter;
import java.io.InputStreamReader;
import java.util.ArrayList;

import homhom.lib.emojiboard.bean.Emoji;
import homhom.lib.emojiboard.bean.EmojiPacket;
import homhom.lib.emojiboard.core.EmojiBoardConfiguration;
import homhom.lib.emojiboard.mgr.EmojiBoardFixer;
import homhom.lib.emojiboard.util.FileUtil;

/**
 * Created by linhonghong on 2016/2/1.
 */
public class JsonEmojiPacketParser extends BaseEmojiPacketParser{

    public class FileFilter implements FilenameFilter {
        @Override
        public boolean accept(File dir, String filename) {
            return filename.endsWith(EmojiBoardConfiguration.EMOJI_PACKET_PARSER_EXTENSIONS);
        }
    }

    @Override
    public void onCreate() {

    }

    @Override
    public void onParse(ArrayList<File> emojiPacketDirectory) {
        for(File file : emojiPacketDirectory){
            File[] files = file.listFiles(new FileFilter());
            for(File parser_file : files){
                String json_str = null;
                try {
                    json_str = readJsonFile(parser_file);
                    EmojiBoardFixer.getInstance().
                            getEmojiManager().
                            addEmojiPacket(parserEmojiPacket(FileUtil.getFileCurrentDirectoryPath(parser_file.getAbsolutePath()),json_str));
                    break;
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }
    }

    public EmojiPacket parserEmojiPacket(String path, String json) throws Exception{
        EmojiPacket emojiPacket = new EmojiPacket();
        JSONObject obj = new JSONObject(json);
        if(obj.has("id")){
            emojiPacket.mId = obj.getInt("id");
        }
        if(obj.has("column")){
            emojiPacket.mColumn = obj.getInt("column");
        }
        if(obj.has("packetName")){
            emojiPacket.mPacketInfo.mPacketName = obj.getString("packetName");
        }
        if(obj.has("packetIcon")){
            emojiPacket.mPacketInfo.mPacketIcon = obj.getString("packetIcon");
        }

        if(obj.has("showDelete")){
            emojiPacket.mShowDelete = obj.getBoolean("showDelete");
        }

        if(obj.has("emojis")){
            JSONArray emojis = obj.getJSONArray("emojis");
            for(int i = 0 ; i < emojis.length(); i ++){
                JSONObject emoji_obj = emojis.getJSONObject(i);
                Emoji emoji = new Emoji();
                if(emoji_obj.has("id")){
                    emoji.mId = emoji_obj.getInt("id");
                }
                if(emoji_obj.has("key")){
                    emoji.mName = emoji_obj.getString("key");
                }
                if(emoji_obj.has("value")){
                    emoji.mPath = path + emoji_obj.getString("value");
                }
                emojiPacket.mEmojis.add(emoji);
            }
        }

        return emojiPacket;

    }

    public String readJsonFile(File file) throws Exception{
        StringBuffer stringBuffer = new StringBuffer();
        String line = null ;
        BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file),"UTF-8"));
        while( (line = br.readLine())!= null ) {
            stringBuffer.append(line);
        }
        br.close();
        return stringBuffer.toString();
    }

    @Override
    public void onZip(ArrayList<File> emojiPacketZipPath) throws Exception{
        for(File file : emojiPacketZipPath){
            try {
                FileUtil.upZipFile(file, FileUtil.getFileCurrentDirectoryPath(file.getAbsolutePath()) + EmojiBoardConfiguration.EMOJI_PACKET_PRE_FIX + file.hashCode());
                if(file.exists()){
                    file.delete();
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    @Override
    public void release() {

        if(mEmojiPacketZipPath != null){
            mEmojiPacketZipPath.clear();
            mEmojiPacketZipPath = null;
        }

        if(mEmojiPacketDirectoryPath != null){
            mEmojiPacketDirectoryPath.clear();
            mEmojiPacketDirectoryPath = null;
        }

        mRootPath = null;

    }
}
