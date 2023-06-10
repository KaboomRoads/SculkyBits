package com.kaboomroads.sculkybits.fluid;

import com.kaboomroads.sculkybits.Sculkybits;
import com.kaboomroads.sculkybits.block.ModBlocks;
import com.kaboomroads.sculkybits.item.ModItems;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fluids.ForgeFlowingFluid;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModFluids {
    public static final DeferredRegister<Fluid> FLUIDS =
            DeferredRegister.create(ForgeRegistries.FLUIDS, Sculkybits.MOD_ID);

    public static final RegistryObject<ForgeFlowingFluid> SCULK_FLUID
            = FLUIDS.register("sculk_fluid_still", () -> new ForgeFlowingFluid.Source(ModFluids.SCULK_PROPERTIES));

    public static final RegistryObject<ForgeFlowingFluid> SCULK_FLUID_FLOWING
            = FLUIDS.register("sculk_fluid_flowing", () -> new ForgeFlowingFluid.Flowing(ModFluids.SCULK_PROPERTIES));

    public static final RegistryObject<LiquidBlock> SCULK_FLUID_BLOCK = ModBlocks.registerBlock("sculk_fluid", () -> new LiquidBlock(SCULK_FLUID, BlockBehaviour.Properties.of().mapColor(MapColor.WATER).replaceable().noCollission().strength(100.0F).pushReaction(PushReaction.DESTROY).noLootTable().liquid().sound(SoundType.EMPTY)));

    public static final ForgeFlowingFluid.Properties SCULK_PROPERTIES = new ForgeFlowingFluid.Properties(ModFluidTypes.SCULK_TYPE,
            SCULK_FLUID, SCULK_FLUID_FLOWING).bucket(ModItems.SCULK_BUCKET).block(SCULK_FLUID_BLOCK).tickRate(10).levelDecreasePerBlock(2);

    public static void register(IEventBus eventBus) {
        FLUIDS.register(eventBus);
    }
}
