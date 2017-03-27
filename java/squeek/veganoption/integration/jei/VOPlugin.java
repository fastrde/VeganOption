package squeek.veganoption.integration.jei;

import mezz.jei.api.*;
import mezz.jei.api.ingredients.IIngredientHelper;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import squeek.veganoption.ModInfo;
import squeek.veganoption.content.modules.Composting;
import squeek.veganoption.content.modules.CreativeTabProxy;
import squeek.veganoption.integration.jei.composting.CompostingRecipeCategory;
import squeek.veganoption.integration.jei.composting.CompostingRecipeHandler;
import squeek.veganoption.integration.jei.composting.CompostingRecipeMaker;
import squeek.veganoption.integration.jei.drops.DropsCategory;
import squeek.veganoption.integration.jei.drops.DropsHandler;
import squeek.veganoption.integration.jei.drops.DropsMaker;
import squeek.veganoption.integration.jei.piston.PistonRecipeCategory;
import squeek.veganoption.integration.jei.piston.PistonRecipeHandler;
import squeek.veganoption.integration.jei.piston.PistonRecipeMaker;

@JEIPlugin
public class VOPlugin extends BlankModPlugin
{
	public static IJeiHelpers jeiHelpers;
	public static IIngredientHelper<ItemStack> jeiItemStackHelper;

	public static class VORecipeCategoryUid
	{
		public static final String PISTON = ModInfo.MODID + ".piston";
		public static final String COMPOSTING = ModInfo.MODID + ".composting";
		public static final String DROPS = ModInfo.MODID + ".drops";
		public static final String DESCRIPTION = ModInfo.MODID + ".description";
	}

	@Override
	public void register(IModRegistry registry)
	{
		jeiHelpers = registry.getJeiHelpers();
		IGuiHelper guiHelper = jeiHelpers.getGuiHelper();
		jeiItemStackHelper = registry.getIngredientRegistry().getIngredientHelper(ItemStack.class);

		IItemBlacklist itemBlacklist = jeiHelpers.getItemBlacklist();
		itemBlacklist.addItemToBlacklist(new ItemStack(CreativeTabProxy.proxyItem));

		registry.addRecipeCategories(
			new PistonRecipeCategory(guiHelper),
			new DropsCategory(guiHelper),
			new CompostingRecipeCategory(guiHelper)
		);

		registry.addRecipeHandlers(
			new PistonRecipeHandler(),
			new DropsHandler(),
			new CompostingRecipeHandler()
		);

		registry.addRecipeCategoryCraftingItem(new ItemStack(Blocks.PISTON), VORecipeCategoryUid.PISTON);
		registry.addRecipeCategoryCraftingItem(new ItemStack(Composting.composter), VORecipeCategoryUid.COMPOSTING);

		registry.addRecipes(PistonRecipeMaker.getRecipes());
		registry.addRecipes(DropsMaker.getRecipes());
		registry.addRecipes(CompostingRecipeMaker.getRecipes());
	}
}