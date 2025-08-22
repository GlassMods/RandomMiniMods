package lmao.glassmods.iksm;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;
import org.lwjgl.input.Keyboard;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineEvent;
import java.net.URL;

@Mod(modid = "iksm", name = "KeySounds", version = "1.0", acceptedMinecraftVersions = "[1.8.9]", clientSideOnly = true)
public class IKSM {

    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {
        MinecraftForge.EVENT_BUS.register(this);
    }

    @SubscribeEvent
    public void onKeyEvent(InputEvent.KeyInputEvent event) {
        if(!Keyboard.getEventKeyState()) { return; }
        switch (Keyboard.getEventKey()) {
            case Keyboard.KEY_RETURN:
            case Keyboard.KEY_LSHIFT:
            case Keyboard.KEY_RSHIFT:
            case Keyboard.KEY_CAPITAL:
                play("key_mod.wav");
                break;
            case Keyboard.KEY_BACK:
                play("key_del.wav");
                break;
            default:
                play("key_reg.wav");
                break;
        }
    }

    public static void play(String fileName) {
        URL diskPath = IKSM.class.getClassLoader().getResource("assets/sounds/" + fileName);
        if (diskPath != null) {
            try {
                Clip clip = AudioSystem.getClip();
                AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(diskPath);
                clip.open(audioInputStream);
                clip.start();

                clip.addLineListener(event -> {
                    if (event.getType() == LineEvent.Type.STOP) {
                        clip.close();
                        try { audioInputStream.close(); } catch (Exception ignored) {}
                    }
                });
            } catch (Exception ignored) {}
        }
    }

}
