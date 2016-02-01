package homhom.lib.emojiboard.core.parser;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.io.File;
import java.util.ArrayList;

import homhom.lib.emojiboard.bean.Emoji;
import homhom.lib.emojiboard.bean.EmojiPacket;
import homhom.lib.emojiboard.util.FileUtil;


/**
 * Created by linhonghong on 2016/2/1.
 */
public class XmlEmojiPacketParser extends BaseEmojiPacketParser{

    @Override
    public void onCreate() {

    }

    @Override
    public void onParse(ArrayList<File> emojiPacketDirectory) {

    }

    @Override
    public void onZip(ArrayList<File> emojiPacketZipPath) throws Exception{
        for(File file : emojiPacketZipPath){
            FileUtil.upZipFile(file, FileUtil.getFileCurrentDirectoryPath(file.getAbsolutePath()));
        }
    }

    class XmlEmojiPacketHandler extends DefaultHandler{

        private EmojiPacket mEmojiPacket;

        private Emoji mEmoji;

        private String mContent;

        public XmlEmojiPacketHandler(){
            mEmojiPacket = new EmojiPacket();
        }

        @Override
        public void startDocument() throws SAXException {
            super.startDocument();
        }

        @Override
        public void endDocument() throws SAXException {
            super.endDocument();
        }

        @Override
        public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
            super.startElement(uri, localName, qName, attributes);
        }

        @Override
        public void endElement(String uri, String localName, String qName) throws SAXException {
            super.endElement(uri, localName, qName);
        }

        @Override
        public void characters(char[] ch, int start, int length) throws SAXException {
            super.characters(ch, start, length);

            if(mContent!=null){
                String data = new String(ch,start,length);
                //判断标签是否为空
                if(mContent.equals("id")){
                    mEmojiPacket.mId = Integer.parseInt(data);
                }else if(mContent.equals("column")){
                    mEmojiPacket.mColumn = Integer.parseInt(data);
                }else if(mContent.equals("packetName")){
                    mEmojiPacket.mPacketName = data;
                }else if(mContent.equals("packetIcon")){
                    mEmojiPacket.mPacketIcon = data;
                }else if(mContent.equals("showDelete")){
                    mEmojiPacket.mShowDelete = Boolean.parseBoolean(data);
                }
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
