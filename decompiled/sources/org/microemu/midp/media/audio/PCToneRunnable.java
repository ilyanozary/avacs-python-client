package org.microemu.midp.media.audio;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.SourceDataLine;
import org.microemu.midp.media.RunnableInterface;

/* loaded from: avacs.jar:org/microemu/midp/media/audio/PCToneRunnable.class */
class PCToneRunnable extends PCToneHelper implements RunnableInterface {
    private boolean running;

    public PCToneRunnable(SourceDataLine sourceDataLine, AudioInputStream audioInputStream, AudioFormat audioFormat, int size) {
        super(sourceDataLine, audioInputStream, audioFormat, size);
    }

    public void init() throws Exception {
        getSourceDataLine().open(getAudioFormat());
        getSourceDataLine().start();
    }

    public void close() {
        getSourceDataLine().drain();
        getSourceDataLine().stop();
        getSourceDataLine().close();
    }

    @Override // org.microemu.midp.media.RunnableInterface
    public synchronized boolean isRunning() {
        return this.running;
    }

    @Override // org.microemu.midp.media.RunnableInterface
    public synchronized void setRunning(boolean running) {
        this.running = running;
    }

    @Override // java.lang.Runnable
    public void run() {
        try {
            setRunning(true);
            init();
            while (true) {
                int cnt = getAudioInputStream().read(this.playBuffer, 0, this.playBuffer.length);
                if (cnt != -1) {
                    if (cnt > 0) {
                        getSourceDataLine().write(this.playBuffer, 0, cnt);
                    }
                } else {
                    close();
                    setRunning(false);
                    return;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
