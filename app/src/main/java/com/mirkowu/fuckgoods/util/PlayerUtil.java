package com.mirkowu.fuckgoods.util;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;

import java.util.List;

/**
 * Created by Administrator on 2016/10/17 0017.
 */

public class PlayerUtil {
    private MediaPlayer player;

    public PlayerUtil() {
      //  player = new MediaPlayer();
    }

    /**
     * 获取铃声列表
     *
     * @return
     */
    public List<String> getRingList() {
        return FileUtil.getRingFileName(FileUtil.getRingCacheDir());
    }

    /**
     * 播放启动音乐
     *
     * @param context
     */
    public void playStarRing(Context context) {
        Uri uri = Uri.parse(FileUtil.getRingCacheDir().getAbsolutePath() + "/starRing.mp3");
        playRing(context, uri);
    }

    /**
     * 播放铃声
     *
     * @param context
     * @param uri
     */
    private void playRing(Context context, Uri uri) {
        //播放另一首前先暂停前一首
        stop();
        if (player == null)
            player = new MediaPlayer();
        try {
            if (player != null)
                player.setDataSource(context, uri);
        } catch (Exception e) {
            e.printStackTrace();
        }
        final AudioManager audioManager = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
        if (audioManager.getStreamVolume(AudioManager.STREAM_ALARM) != 0) {
            if (player != null) {
                player.setAudioStreamType(AudioManager.STREAM_ALARM);
                player.setLooping(false);//不循环播放
                player.setVolume(0.3f, 0.3f);
                try {
                    player.prepare();
                } catch (Exception e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                player.start();
            }
        }
    }

    public void stop() {
        if (player != null && player.isPlaying()) {
            player.stop();//如果正在播放就停下来
            player=null;
        }
    }

    public void destory() {
        if (player != null) {
            player.reset();
            player = null;
        }
        System.gc();
    }


}
