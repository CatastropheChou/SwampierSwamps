package andrews.swampier_swamps;

import andrews.swampier_swamps.network.SSNetwork;
import andrews.swampier_swamps.registry.*;
import andrews.swampier_swamps.util.PotionRecipeChanger;
import andrews.swampier_swamps.util.Reference;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(value = Reference.MODID)
public class SwampierSwamps
{
    public SwampierSwamps()
    {
        final IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        SSFrogVariants.FROG_VARIANTS.register(modEventBus);
        SSItems.ITEMS.register(modEventBus);
        SSBlocks.BLOCKS.register(modEventBus);
        SSEntities.ENTITIES.register(modEventBus);
        SSTreeDecorators.TREE_DECORATORS.register(modEventBus);

        DistExecutor.runWhenOn(Dist.CLIENT, () -> () ->
        {
            modEventBus.addListener(this::setupClient);
        });
        modEventBus.addListener(this::setupCommon);
    }

    void setupCommon(final FMLCommonSetupEvent event)
    {
        event.enqueueWork(() ->
        {
            PotionRecipeChanger.alterLeapingPotionRecipe();
            PotionRecipeChanger.registerRecipes();
            SSEntities.registerSpawnPlacements();
        });
        //Thread Safe Stuff Bellow
        SSNetwork.setupMessages();
    }

    void setupClient(final FMLClientSetupEvent event)
    {
        event.enqueueWork(() ->
        {
//            SSBlockEntities.registerBlockEntityRenderers();
        });
        // Thread Safe Stuff Bellow
        SSBlocks.registerBlockRenderTypes();
    }
}