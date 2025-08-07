package org.microemu.midp.media.audio;

import javax.microedition.media.Control;
import javax.microedition.media.Manager;
import javax.microedition.media.MediaException;
import org.microemu.midp.media.BasicPlayer;
import org.microemu.midp.media.RunnableInterface;

/* loaded from: avacs.jar:org/microemu/midp/media/audio/PCTonePlayer.class */
public class PCTonePlayer extends BasicPlayer implements RunnableInterface {
    private PCToneControl pcToneControl;
    private int sequenceIndex;
    private boolean running;
    private ToneInfo toneInfo;

    public PCTonePlayer() {
        try {
            this.pcToneControl = new PCToneControl();
            setTimeBase(new PCTimeBase());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override // javax.microedition.media.Player
    public void close() {
    }

    @Override // javax.microedition.media.Player
    public String getContentType() {
        return null;
    }

    @Override // org.microemu.midp.media.BasicPlayer, javax.microedition.media.Player
    public synchronized void start() throws MediaException {
        try {
            new Thread(this).start();
            super.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override // org.microemu.midp.media.BasicPlayer, javax.microedition.media.Player
    public synchronized void stop() throws MediaException {
        try {
            setRunning(false);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override // javax.microedition.media.Controllable
    public Control getControl(String controlType) {
        if (controlType.equals(CONTROL_TYPE)) {
            return this.pcToneControl;
        }
        return null;
    }

    @Override // javax.microedition.media.Controllable
    public Control[] getControls() {
        return null;
    }

    @Override // org.microemu.midp.media.RunnableInterface
    public boolean isRunning() {
        return this.running;
    }

    @Override // org.microemu.midp.media.RunnableInterface
    public void setRunning(boolean running) {
        this.running = running;
    }

    private byte getNext() {
        byte[] bArr = this.pcToneControl.sequence;
        int i = this.sequenceIndex;
        this.sequenceIndex = i + 1;
        return bArr[i];
    }

    public void playBlock() throws Exception {
        byte currentByte = getNext();
        while (this.sequenceIndex < this.pcToneControl.sequence.length) {
            byte next = getNext();
            currentByte = next;
            if (next == -6) {
                break;
            }
            if (currentByte == -1) {
                Thread.sleep(this.toneInfo.getSleepDelay());
            } else if (currentByte == -7) {
                getNext();
            } else {
                double noteDelta = currentByte - 69;
                double power = noteDelta / 12.0d;
                double d_frequency = 440.0d * Math.pow(2.0d, power);
                this.toneInfo.setFrequency((int) d_frequency);
                Manager.playTone(this.toneInfo.getFrequency(), this.toneInfo.getLengthOfTime(), this.toneInfo.getVolume());
                Thread.sleep(this.toneInfo.getLengthOfTime() + 20);
            }
        }
        if (currentByte == -6) {
            getNext();
        }
    }

    @Override // java.lang.Runnable
    public void run() throws InterruptedException {
        try {
            this.sequenceIndex = 0;
            this.toneInfo = new ToneInfo();
            setRunning(true);
            while (isRunning() && this.sequenceIndex < this.pcToneControl.sequence.length) {
                byte currentControlCommand = getNext();
                if (currentControlCommand == -2) {
                    getNext();
                } else if (currentControlCommand == -8) {
                    this.toneInfo.setVolume(getNext());
                } else if (currentControlCommand == -3) {
                    byte tempo = getNext();
                    double durationOfNote = 240.0d / ((1.0d / 64.0d) * tempo);
                    this.toneInfo.setLengthOfTime(((int) durationOfNote) / 16);
                } else if (currentControlCommand == -1) {
                    this.toneInfo.setSleepDelay(getNext());
                    Thread.sleep(this.toneInfo.getSleepDelay());
                } else if (currentControlCommand == -7 || currentControlCommand == -5) {
                    playBlock();
                }
            }
            super.stop();
        } catch (Exception e) {
        }
    }
}
