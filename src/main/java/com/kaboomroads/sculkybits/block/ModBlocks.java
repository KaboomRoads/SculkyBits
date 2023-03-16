package com.kaboomroads.sculkybits.block;

import com.kaboomroads.sculkybits.Sculkybits;
import com.kaboomroads.sculkybits.block.custom.*;
import com.kaboomroads.sculkybits.block.custom.override.ModSculkVeinBlock;
import com.kaboomroads.sculkybits.block.custom.override.SpreadingSculkBlock;
import com.kaboomroads.sculkybits.item.ModItems;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.Material;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;

public class ModBlocks {
    public static final DeferredRegister<Block> BLOCKS_OVERRIDE = DeferredRegister.create(ForgeRegistries.BLOCKS, "minecraft");
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, Sculkybits.MOD_ID);

    public static final RegistryObject<Block> SCULK = overrideBlockAndItem("sculk", () ->
            new SpreadingSculkBlock(BlockBehaviour.Properties.copy(Blocks.SCULK)));
    public static final RegistryObject<Block> SCULK_VEIN = overrideBlockAndItem("sculk_vein", () ->
            new ModSculkVeinBlock(BlockBehaviour.Properties.copy(Blocks.SCULK_VEIN)));
    public static final RegistryObject<Block> SCULK_FLESH = registerBlockAndItem("sculk_flesh", () ->
            new SpreadingSculkBlock(BlockBehaviour.Properties.copy(Blocks.SCULK)));
    public static final RegistryObject<Block> SCULK_GROWTH = registerBlockAndItem("sculk_growth", () ->
            new SculkGrowthBlock(BlockBehaviour.Properties.copy(Blocks.SCULK).speedFactor(0.2F).isValidSpawn(Blocks::always).isRedstoneConductor(Blocks::always).isViewBlocking(Blocks::always).isSuffocating(Blocks::always)));
    public static final RegistryObject<Block> SCULK_JAW = registerBlockAndItem("sculk_jaw", () ->
            new SculkJawBlock(BlockBehaviour.Properties.copy(Blocks.SCULK).strength(5f, 5f)));
    public static final RegistryObject<Block> SCULK_TRAP = registerBlockAndItem("sculk_trap", () ->
            new SculkTrapBlock(BlockBehaviour.Properties.copy(Blocks.SCULK).strength(5f, 5f)));
    public static final RegistryObject<Block> SCULK_LURKER = registerBlockAndItem("sculk_lurker", () ->
            new SculkLurkerBlock(BlockBehaviour.Properties.copy(Blocks.SCULK).strength(5f, 5f)));
    public static final RegistryObject<Block> SCULK_STABBER = registerBlockAndItem("sculk_stabber", () ->
            new SculkStabberBlock(BlockBehaviour.Properties.copy(Blocks.SCULK).strength(5f, 5f)));
    public static final RegistryObject<Block> SCULK_ABSORBER = registerBlockAndItem("sculk_absorber", () ->
            new SculkAbsorberBlock(BlockBehaviour.Properties.copy(Blocks.SCULK).strength(5f, 5f)));
    public static final RegistryObject<Block> SCULK_LURER = registerBlockAndItem("sculk_lurer", () ->
            new SculkLurerBlock(BlockBehaviour.Properties.copy(Blocks.SCULK).strength(5f, 5f)));
    public static final RegistryObject<Block> SCULK_FEELER = registerBlockAndItem("sculk_feeler", () ->
            new SculkFeelerBlock(BlockBehaviour.Properties.copy(Blocks.SCULK).strength(5f, 5f).noOcclusion().noCollission().lightLevel(b -> 5)));
    public static final RegistryObject<Block> SCULK_NEST = registerBlockAndItem("sculk_nest", () ->
            new SculkNestBlock(BlockBehaviour.Properties.copy(Blocks.SCULK).strength(5f, 5f).isSuffocating(Blocks::never)));
    public static final RegistryObject<Block> SCULK_CAGE = registerBlockAndItem("sculk_cage", () ->
            new SculkCageBlock(BlockBehaviour.Properties.of(Material.SCULK).strength(3f, 3f).sound(SoundType.SCULK_CATALYST).noOcclusion()));
    public static final RegistryObject<Block> SCULK_BONE_BLOCK = registerBlockAndItem("sculk_bone_block", () ->
            new SculkRotatedPillarBlock(BlockBehaviour.Properties.of(Material.SCULK).strength(6f, 6f).sound(SoundType.SCULK_CATALYST)));
    public static final RegistryObject<Block> SCULK_RIBS = registerBlockAndItem("sculk_ribs", () ->
            new SculkRibsBlock(BlockBehaviour.Properties.of(Material.SCULK).strength(3f, 3f).sound(SoundType.SCULK_CATALYST).noOcclusion()));
    public static final RegistryObject<Block> SCULK_BONES = registerBlockAndItem("sculk_bones", () ->
            new SculkBonesBlock(BlockBehaviour.Properties.of(Material.SCULK).strength(3f, 3f).sound(SoundType.SCULK_CATALYST).noOcclusion()));

    public static <T extends Block> RegistryObject<T> registerBlock(String name, Supplier<T> block) {
        return BLOCKS.register(name, block);
    }

    public static <T extends Block> RegistryObject<T> registerBlockAndItem(String name, Supplier<T> block) {
        RegistryObject<T> toReturn = BLOCKS.register(name, block);
        registerBlockItem(name, toReturn);
        return toReturn;
    }

    public static <T extends Block> RegistryObject<T> overrideBlockAndItem(String name, Supplier<T> block) {
        RegistryObject<T> toReturn = BLOCKS_OVERRIDE.register(name, block);
        overrideBlockItem(name, toReturn);
        return toReturn;
    }

    public static <T extends Block> RegistryObject<Item> registerBlockItem(String name, RegistryObject<T> block) {
        return ModItems.registerItem(name, () -> new BlockItem(block.get(), new Item.Properties()));
    }

    public static <T extends Block> RegistryObject<Item> overrideBlockItem(String name, RegistryObject<T> block) {
        return ModItems.overrideItem(name, () -> new BlockItem(block.get(), new Item.Properties()));
    }

    public static <T extends Block> RegistryObject<Item> registerBlockItem(String name, BlockItem item) {
        return ModItems.registerItem(name, () -> item);
    }

    public static void register(IEventBus eventBus) {
        BLOCKS.register(eventBus);
        BLOCKS_OVERRIDE.register(eventBus);
    }
}
