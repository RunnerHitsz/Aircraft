package edu.hitsz.manager;

import javax.sound.sampled.*;
import java.io.*;
import java.util.HashMap;
import java.util.Map;

/**
 * 音频管理器（单例模式）
 * 统一管理所有音效和背景音乐
 * @author hitsz
 */
public class AudioManager {

    private static AudioManager instance;

    // 音效映射
    private Map<String, Clip> soundEffects;

    // 背景音乐播放器
    private Clip bgmClip;
    private String currentBgm;
    private boolean bgmLooping = true;

    private AudioManager() {
        soundEffects = new HashMap<>();
    }

    public static AudioManager getInstance() {
        if (instance == null) {
            instance = new AudioManager();
        }
        return instance;
    }

    /**
     * 播放音效（短音频，只播放一次）
     * @param filePath 音频文件路径
     */
    public void playSound(String filePath) {
        try {
            // 检查是否已加载，避免重复加载
            Clip clip = soundEffects.get(filePath);
            if (clip == null) {
                // 加载音频文件
                File file = new File(filePath);
                if (!file.exists()) {
                    System.out.println("音频文件不存在: " + filePath);
                    return;
                }

                AudioInputStream audioStream = AudioSystem.getAudioInputStream(file);
                clip = AudioSystem.getClip();
                clip.open(audioStream);
                soundEffects.put(filePath, clip);
            }

            // 重置到开头并播放
            clip.setFramePosition(0);
            clip.start();

        } catch (Exception e) {
            System.out.println("播放音效失败: " + filePath);
            e.printStackTrace();
        }
    }

    /**
     * 播放背景音乐（支持循环）
     * @param filePath 音频文件路径
     */
    public void playBgm(String filePath) {
        playBgm(filePath, true);
    }

    /**
     * 播放背景音乐
     * @param filePath 音频文件路径
     * @param loop 是否循环播放
     */
    public void playBgm(String filePath, boolean loop) {
        // 停止当前播放的音乐
        stopBgm();

        try {
            File file = new File(filePath);
            if (!file.exists()) {
                System.out.println("背景音乐文件不存在: " + filePath);
                return;
            }

            bgmLooping = loop;
            currentBgm = filePath;

            AudioInputStream audioStream = AudioSystem.getAudioInputStream(file);
            bgmClip = AudioSystem.getClip();
            bgmClip.open(audioStream);

            if (loop) {
                bgmClip.loop(Clip.LOOP_CONTINUOUSLY);
            } else {
                bgmClip.start();
            }

        } catch (Exception e) {
            System.out.println("播放背景音乐失败: " + filePath);
            e.printStackTrace();
        }
    }

    /**
     * 停止背景音乐
     */
    public void stopBgm() {
        if (bgmClip != null && bgmClip.isRunning()) {
            bgmClip.stop();
            bgmClip.close();
            bgmClip = null;
        }
    }

    /**
     * 暂停背景音乐
     */
    public void pauseBgm() {
        if (bgmClip != null && bgmClip.isRunning()) {
            bgmClip.stop();
        }
    }

    /**
     * 恢复背景音乐
     */
    public void resumeBgm() {
        if (bgmClip != null && !bgmClip.isRunning()) {
            if (bgmLooping) {
                bgmClip.loop(Clip.LOOP_CONTINUOUSLY);
            } else {
                bgmClip.start();
            }
        }
    }

    /**
     * 释放所有音频资源
     */
    public void releaseAll() {
        stopBgm();
        for (Clip clip : soundEffects.values()) {
            if (clip != null) {
                clip.close();
            }
        }
        soundEffects.clear();
    }
}