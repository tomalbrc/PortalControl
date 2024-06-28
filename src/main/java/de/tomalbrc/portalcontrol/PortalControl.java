package de.tomalbrc.portalcontrol;

import net.fabricmc.api.ModInitializer;

public class PortalControl implements ModInitializer {

    @Override
    public void onInitialize() {
        ModConfig.load();
    }
}
