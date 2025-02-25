package org.barrelmc.barrel.network.translator.bedrock;

import com.github.steveice10.mc.protocol.data.game.entity.player.Animation;
import com.github.steveice10.mc.protocol.packet.ingame.server.entity.ServerEntityAnimationPacket;
import com.nukkitx.protocol.bedrock.BedrockPacket;
import org.barrelmc.barrel.network.translator.interfaces.BedrockPacketTranslator;
import org.barrelmc.barrel.player.Player;

public class AnimatePacket implements BedrockPacketTranslator {

    @Override
    public void translate(BedrockPacket pk, Player player) {
        com.nukkitx.protocol.bedrock.packet.AnimatePacket packet = (com.nukkitx.protocol.bedrock.packet.AnimatePacket) pk;

        switch (packet.getAction()) {
            case SWING_ARM: {
                player.getJavaSession().send(new ServerEntityAnimationPacket((int) packet.getRuntimeEntityId(), Animation.SWING_ARM));
                break;
            }
            case WAKE_UP: {
                player.getJavaSession().send(new ServerEntityAnimationPacket((int) packet.getRuntimeEntityId(), Animation.LEAVE_BED));
                break;
            }
            case CRITICAL_HIT: {
                player.getJavaSession().send(new ServerEntityAnimationPacket((int) packet.getRuntimeEntityId(), Animation.CRITICAL_HIT));
                break;
            }
            case MAGIC_CRITICAL_HIT: {
                player.getJavaSession().send(new ServerEntityAnimationPacket((int) packet.getRuntimeEntityId(), Animation.ENCHANTMENT_CRITICAL_HIT));
                break;
            }
        }
    }
}
