package javax.microedition.media;

import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;
import java.util.Vector;
import javax.microedition.media.control.VolumeControl;
import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MetaEventListener;
import javax.sound.midi.MetaMessage;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.Receiver;
import javax.sound.midi.Sequence;
import javax.sound.midi.Sequencer;
import javax.sound.midi.Synthesizer;
import javax.sound.midi.Transmitter;
import org.microemu.MIDletBridge;

/* loaded from: avacs.jar:javax/microedition/media/MidiAudioPlayer.class */
class MidiAudioPlayer implements Player, MetaEventListener {
    private int state;
    private Sequence sequence = null;
    private Sequencer sequencer = null;
    private Vector vListeners = null;
    private int iLoopCount = 1;

    MidiAudioPlayer() {
    }

    public boolean open(InputStream stream, String type) {
        try {
            this.sequencer = MidiSystem.getSequencer();
            this.sequencer.open();
            Synthesizer synth = MidiSystem.getSynthesizer();
            synth.open();
            Transmitter transmitter = this.sequencer.getTransmitter();
            Receiver receiver = synth.getReceiver();
            transmitter.setReceiver(receiver);
            this.sequence = MidiSystem.getSequence(stream);
            this.sequencer.setSequence(this.sequence);
            this.state = 100;
            return false;
        } catch (UnsatisfiedLinkError e) {
            e.printStackTrace();
            return false;
        } catch (MidiUnavailableException e2) {
            e2.printStackTrace();
            return false;
        } catch (InvalidMidiDataException e3) {
            e3.printStackTrace();
            return false;
        } catch (IOException e4) {
            e4.printStackTrace();
            return false;
        }
    }

    @Override // javax.microedition.media.Player
    public void addPlayerListener(PlayerListener playerListener) {
        if (this.vListeners == null) {
            this.vListeners = new Vector();
        }
        this.vListeners.add(playerListener);
    }

    @Override // javax.microedition.media.Player
    public void close() {
        if (this.state == 0) {
            return;
        }
        MIDletBridge.removeMediaPlayer(this);
        if (this.sequencer != null) {
            this.sequencer.close();
        }
        this.state = 0;
    }

    @Override // javax.microedition.media.Player
    public void deallocate() {
        this.state = 200;
    }

    @Override // javax.microedition.media.Player
    public String getContentType() {
        return "audio/midi";
    }

    @Override // javax.microedition.media.Player
    public long getDuration() {
        return 0L;
    }

    @Override // javax.microedition.media.Player
    public long getMediaTime() {
        if (this.sequencer != null) {
            return this.sequencer.getMicrosecondPosition();
        }
        return 0L;
    }

    @Override // javax.microedition.media.Player
    public int getState() {
        return this.state;
    }

    @Override // javax.microedition.media.Player
    public void prefetch() throws MediaException {
        this.state = 300;
    }

    @Override // javax.microedition.media.Player
    public void realize() throws MediaException {
        this.state = 200;
    }

    @Override // javax.microedition.media.Player
    public void removePlayerListener(PlayerListener playerListener) {
        if (this.vListeners == null) {
            return;
        }
        Iterator it = this.vListeners.iterator();
        while (it.hasNext()) {
            PlayerListener listener = (PlayerListener) it.next();
            if (listener == playerListener) {
                this.vListeners.remove(listener);
                return;
            }
        }
    }

    @Override // javax.microedition.media.Player
    public void setLoopCount(int count) {
        this.iLoopCount = count;
    }

    @Override // javax.microedition.media.Player
    public long setMediaTime(long now) throws MediaException {
        if (this.sequencer != null) {
            this.sequencer.setMicrosecondPosition(now);
        }
        return now;
    }

    @Override // javax.microedition.media.Player
    public void start() throws MediaException {
        if (this.sequencer != null) {
            this.sequencer.addMetaEventListener(this);
            this.sequencer.start();
        }
        this.state = 400;
    }

    @Override // javax.microedition.media.Player
    public void stop() throws MediaException {
        if (this.sequencer != null) {
            this.sequencer.stop();
        }
        this.state = 300;
    }

    @Override // javax.microedition.media.Controllable
    public Control getControl(String controlType) {
        if (controlType.equals("VolumeControl")) {
            return new VolumeControl() { // from class: javax.microedition.media.MidiAudioPlayer.1
                @Override // javax.microedition.media.control.VolumeControl
                public int getLevel() {
                    return 0;
                }

                @Override // javax.microedition.media.control.VolumeControl
                public boolean isMuted() {
                    return false;
                }

                @Override // javax.microedition.media.control.VolumeControl
                public int setLevel(int level) {
                    return 0;
                }

                @Override // javax.microedition.media.control.VolumeControl
                public void setMute(boolean mute) {
                }
            };
        }
        return null;
    }

    @Override // javax.microedition.media.Controllable
    public Control[] getControls() {
        return null;
    }

    public void meta(MetaMessage event) {
        if (event.getType() == 47) {
            if (this.iLoopCount > 0) {
                this.iLoopCount--;
            }
            if (this.iLoopCount > 0 || this.iLoopCount == -1) {
                this.sequencer.setMicrosecondPosition(0L);
                try {
                    start();
                    return;
                } catch (MediaException e) {
                    e.printStackTrace();
                    return;
                }
            }
            close();
            if (this.vListeners != null) {
                Iterator it = this.vListeners.iterator();
                while (it.hasNext()) {
                    PlayerListener listener = (PlayerListener) it.next();
                    listener.playerUpdate(this, PlayerListener.END_OF_MEDIA, null);
                }
            }
        }
    }
}
