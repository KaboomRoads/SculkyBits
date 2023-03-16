package com.kaboomroads.sculkybits.block.entity;

import com.kaboomroads.sculkybits.Sculkybits;
import com.kaboomroads.sculkybits.block.ModBlocks;
import com.kaboomroads.sculkybits.block.entity.custom.*;
import com.mojang.datafixers.types.Type;
import net.minecraft.Util;
import net.minecraft.util.datafix.fixes.References;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModBlockEntities {
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES =
            DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, Sculkybits.MOD_ID);
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES_OVERRIDE =
            DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, "minecraft");

    public static final RegistryObject<BlockEntityType<SculkJawBlockEntity>> SCULK_JAW =
            BLOCK_ENTITIES.register("sculk_jaw", () ->
                    BlockEntityType.Builder.of(SculkJawBlockEntity::new,
                            ModBlocks.SCULK_JAW.get()).build(null));

    public static final RegistryObject<BlockEntityType<SculkLurkerBlockEntity>> SCULK_LURKER =
            BLOCK_ENTITIES.register("sculk_lurker", () ->
                    BlockEntityType.Builder.of(SculkLurkerBlockEntity::new,
                            ModBlocks.SCULK_LURKER.get()).build(null));

    public static final RegistryObject<BlockEntityType<SculkTrapBlockEntity>> SCULK_TRAP =
            BLOCK_ENTITIES.register("sculk_trap", () ->
                    BlockEntityType.Builder.of(SculkTrapBlockEntity::new,
                            ModBlocks.SCULK_TRAP.get()).build(null));

    public static final RegistryObject<BlockEntityType<SculkLurerBlockEntity>> SCULK_LURER =
            BLOCK_ENTITIES.register("sculk_lurer", () ->
                    BlockEntityType.Builder.of(SculkLurerBlockEntity::new,
                            ModBlocks.SCULK_LURER.get()).build(null));

    public static final RegistryObject<BlockEntityType<SculkStabberBlockEntity>> SCULK_STABBER =
            BLOCK_ENTITIES.register("sculk_stabber", () ->
                    BlockEntityType.Builder.of(SculkStabberBlockEntity::new,
                            ModBlocks.SCULK_STABBER.get()).build(null));

    public static final RegistryObject<BlockEntityType<SculkAbsorberBlockEntity>> SCULK_ABSORBER =
            BLOCK_ENTITIES.register("sculk_absorber", () ->
                    BlockEntityType.Builder.of(SculkAbsorberBlockEntity::new,
                            ModBlocks.SCULK_ABSORBER.get()).build(null));

    public static final RegistryObject<BlockEntityType<SculkFeelerBlockEntity>> SCULK_FEELER =
            BLOCK_ENTITIES.register("sculk_feeler", () ->
                    BlockEntityType.Builder.of(SculkFeelerBlockEntity::new,
                            ModBlocks.SCULK_FEELER.get()).build(null));

    public static final RegistryObject<BlockEntityType<SculkNestBlockEntity>> SCULK_NEST =
            BLOCK_ENTITIES.register("sculk_nest", () ->
                    BlockEntityType.Builder.of(SculkNestBlockEntity::new,
                            ModBlocks.SCULK_NEST.get()).build(null));

    public static Type<?> getType(String name) {
        return Util.fetchChoiceType(References.BLOCK_ENTITY, name);
    }

    public static void register(IEventBus eventBus) {
        BLOCK_ENTITIES.register(eventBus);
        BLOCK_ENTITIES_OVERRIDE.register(eventBus);
    }
}
