package com.kaboomroads.sculkybits;

import com.kaboomroads.sculkybits.block.ModBlocks;
import com.kaboomroads.sculkybits.block.entity.ModBlockEntities;
import com.kaboomroads.sculkybits.effect.ModEffects;
import com.kaboomroads.sculkybits.entity.ModEntityTypes;
import com.kaboomroads.sculkybits.fluid.ModFluidTypes;
import com.kaboomroads.sculkybits.fluid.ModFluids;
import com.kaboomroads.sculkybits.gameevent.ModGameEvent;
import com.kaboomroads.sculkybits.gamerule.ModGameRules;
import com.kaboomroads.sculkybits.item.ModItems;
import com.kaboomroads.sculkybits.networking.ModMessages;
import com.kaboomroads.sculkybits.potion.ModPotions;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(Sculkybits.MOD_ID)
public class Sculkybits {
    public static final String MOD_ID = "sculkybits";

    public Sculkybits() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        ModItems.register(modEventBus);
        ModBlocks.register(modEventBus);
        ModBlockEntities.register(modEventBus);
        ModEffects.register(modEventBus);
        ModPotions.register(modEventBus);
        ModFluidTypes.register(modEventBus);
        ModFluids.register(modEventBus);
        ModEntityTypes.register(modEventBus);
        modEventBus.addListener(this::commonSetup);
        new ModGameRules();
        MinecraftForge.EVENT_BUS.register(this);
    }

    private void commonSetup(final FMLCommonSetupEvent event) {
        event.enqueueWork(() -> {
            ModGameEvent.register();
            ModMessages.register();
        });
    }
}
