package de.tomalbrc.portalcontrol;

import de.tomalbrc.portalcontrol.ModConfig;
import net.fabricmc.api.ModInitializer;
import net.minecraft.world.level.block.NetherPortalBlock;

public class PortalControl implements ModInitializer {

    @Override
    public void onInitialize() {
        ModConfig.load();
    }
}
