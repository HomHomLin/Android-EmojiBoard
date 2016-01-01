package homhom.lib.emojiboard.util;

import homhom.lib.emojiboard.mgr.EmojiBoardFixer;

/**
 * Created by Linhh on 16/1/1.
 */
public class EmojiPacketUtil {

    public static int splitGetLine(int column){
        int width = EmojiBoardFixer.getInstance().getEmojiViewManager().getBoardWidth();

        int height = EmojiBoardFixer.getInstance().getEmojiViewManager().getBoardHeight();

        int dpEach = width / column;

        int line = height / dpEach;//公能容纳多少行

        return line;
    }

    public static int itemSizeInPager(int column){

        int line = splitGetLine(column);

        int itemSizeWithoutDelete = line * column;

        return itemSizeWithoutDelete - 1;
    }
}
