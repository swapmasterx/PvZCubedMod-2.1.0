package io.github.GrassyDev.pvzmod.block.entity;

import io.github.GrassyDev.pvzmod.PvZCubed;
import io.github.GrassyDev.pvzmod.block.ModBlocks;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import org.quiltmc.qsl.block.entity.api.QuiltBlockEntityTypeBuilder;

public class ModBlockEntities {
    public static final BlockEntityType<BotanyStationBlockEntity> BOTANY_STATION_BLOCK_ENTITY =
            Registry.register(Registries.BLOCK_ENTITY_TYPE, new Identifier(PvZCubed.MOD_ID, "botany_station"),
                    QuiltBlockEntityTypeBuilder.create(BotanyStationBlockEntity::new,
                            ModBlocks.BOTANY_STATION).build());

    public static void registerBlockEntities(){
        PvZCubed.LOGGER.info("Registering Block Entities for" + PvZCubed.MOD_ID);
    }
}
