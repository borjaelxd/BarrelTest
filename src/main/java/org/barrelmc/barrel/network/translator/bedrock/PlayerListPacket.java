package org.barrelmc.barrel.network.translator.bedrock;

import com.github.steveice10.mc.auth.data.GameProfile;
import com.github.steveice10.mc.protocol.data.game.PlayerListEntry;
import com.github.steveice10.mc.protocol.data.game.PlayerListEntryAction;
import com.github.steveice10.mc.protocol.data.game.entity.player.GameMode;
import com.github.steveice10.mc.protocol.packet.ingame.server.ServerPlayerListEntryPacket;
import com.nukkitx.protocol.bedrock.BedrockPacket;
import net.kyori.adventure.text.Component;
import org.barrelmc.barrel.network.translator.interfaces.BedrockPacketTranslator;
import org.barrelmc.barrel.player.Player;
import org.barrelmc.barrel.utils.Utils;

import java.util.ArrayList;

public class PlayerListPacket implements BedrockPacketTranslator {

    @Override
    public void translate(BedrockPacket pk, Player player) {
        com.nukkitx.protocol.bedrock.packet.PlayerListPacket packet = (com.nukkitx.protocol.bedrock.packet.PlayerListPacket) pk;
        ArrayList<PlayerListEntry> playerListEntries = new ArrayList<>();

        for (com.nukkitx.protocol.bedrock.packet.PlayerListPacket.Entry entry : packet.getEntries()) {
            GameProfile gameProfile = new GameProfile(entry.getUuid(), Utils.lengthCutter(entry.getName(), 16));
            playerListEntries.add(new PlayerListEntry(gameProfile, GameMode.SURVIVAL, 0, Component.text(Utils.lengthCutter(entry.getName(), 16))));
        }

        PlayerListEntry[] playerListEntriesL = playerListEntries.toArray(new PlayerListEntry[0]);
        switch (packet.getAction()) {
            case ADD: {
                player.getJavaSession().send(new ServerPlayerListEntryPacket(PlayerListEntryAction.ADD_PLAYER, playerListEntriesL));
                break;
            }
            case REMOVE: {
                player.getJavaSession().send(new ServerPlayerListEntryPacket(PlayerListEntryAction.REMOVE_PLAYER, playerListEntriesL));
                break;
            }
        }
    }
}
