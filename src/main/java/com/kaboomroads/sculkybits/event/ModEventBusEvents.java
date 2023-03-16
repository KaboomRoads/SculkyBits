package com.kaboomroads.sculkybits.event;

import com.kaboomroads.sculkybits.Sculkybits;
import com.kaboomroads.sculkybits.entity.ModEntityTypes;
import com.kaboomroads.sculkybits.entity.custom.SculkCrawler;
import com.kaboomroads.sculkybits.entity.custom.SculkSaprophyte;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = Sculkybits.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModEventBusEvents {
    @SubscribeEvent
    public static void onSetAttributes(EntityAttributeCreationEvent event) {
        event.put(ModEntityTypes.SCULK_SAPROPHYTE.get(), SculkSaprophyte.setAttributes());
        event.put(ModEntityTypes.SCULK_CRAWLER.get(), SculkCrawler.setAttributes());
    }
}
