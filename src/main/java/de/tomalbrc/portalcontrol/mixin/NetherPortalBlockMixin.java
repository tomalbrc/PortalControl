package de.tomalbrc.portalcontrol.mixin;

import de.tomalbrc.portalcontrol.ModConfig;
import net.minecraft.core.BlockPos;
import net.minecraft.data.worldgen.DimensionTypes;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.players.PlayerList;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.NetherPortalBlock;
import net.minecraft.world.level.border.WorldBorder;
import net.minecraft.world.level.dimension.BuiltinDimensionTypes;
import net.minecraft.world.level.dimension.DimensionType;
import net.minecraft.world.level.portal.DimensionTransition;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(NetherPortalBlock.class)
public class NetherPortalBlockMixin {
    @Inject(method = "getPortalDestination", at = @At("HEAD"), cancellable = true)
    public void getPortalDestination(ServerLevel serverLevel, Entity entity, BlockPos blockPos, CallbackInfoReturnable<DimensionTransition> cir) {
        if (serverLevel.dimension().location().equals(ResourceLocation.parse(ModConfig.getInstance().sourceDimension))) {
            var serverLevel2 = pc$getWorld(serverLevel.getServer(), ResourceLocation.parse(ModConfig.getInstance().targetDimension));

            if (serverLevel2 != null) {
                Vec3 speed = new Vec3(0,0,0);
                Vec3 pos = new Vec3(ModConfig.getInstance().targetPosition.get(0),ModConfig.getInstance().targetPosition.get(1),ModConfig.getInstance().targetPosition.get(2));
                DimensionTransition dt = new DimensionTransition(serverLevel2, pos, speed, ModConfig.getInstance().yRot, ModConfig.getInstance().xRot, DimensionTransition.DO_NOTHING);
                cir.setReturnValue(dt);
            }
        }
    }

    @Unique
    private ServerLevel pc$getWorld(MinecraftServer server, ResourceLocation resourceLocation) {
        for (ServerLevel level : server.getAllLevels()) {
            boolean ok = level.dimension().location().equals(resourceLocation);
            if (ok) {
                return level;
            }
        }

        return null;
    }
}
