package org.microemu.midp.media.audio;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.SourceDataLine;

/* loaded from: avacs.jar:org/microemu/midp/media/audio/PCTone.class */
public class PCTone {
    private static final int MAX_TIME = 4;
    private static final int sampleSizeInBits = 16;
    private static final int channels = 1;
    private static final boolean signed = true;
    private static final boolean bigEndian = true;
    private static final int sampleRate = 16000;
    private static final AudioFormat audioFormat = new AudioFormat(16000.0f, 16, 1, true, true);
    private static final DataLine.Info dataLineInfo = new DataLine.Info(SourceDataLine.class, audioFormat);
    private final byte[] audioData = new byte[64000];

    public void play(double frequency, double duration, double volume) {
        try {
            InputStream byteArrayInputStream = new ByteArrayInputStream(this.audioData);
            AudioInputStream audioInputStream = new AudioInputStream(byteArrayInputStream, audioFormat, this.audioData.length / audioFormat.getFrameSize());
            SourceDataLine sourceDataLine = AudioSystem.getLine(dataLineInfo);
            double d = volume / 100.0d;
            int size = (int) ((16000.0d * (duration / 1000.0d)) / 2.0d);
            for (int index = 0; index < size; index++) {
                double currentTime = index / 16000.0d;
                Math.sin(6.283185307179586d * frequency * currentTime);
            }
            PCToneRunnable pcToneRunnable = PCToneRunnablePoolFactory.getInstance(sourceDataLine, audioInputStream, audioFormat, 64000);
            new Thread(pcToneRunnable).start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
