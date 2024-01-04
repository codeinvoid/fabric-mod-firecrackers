package moe.pie.hoige.firecrackers

import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput
import net.fabricmc.fabric.api.datagen.v1.provider.FabricLanguageProvider
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider
import net.minecraft.data.server.recipe.RecipeJsonProvider
import net.minecraft.data.server.recipe.ShapelessRecipeJsonBuilder
import net.minecraft.recipe.book.RecipeCategory
import java.util.function.Consumer


object FirecrackersDataGenerator : DataGeneratorEntrypoint {
	override fun onInitializeDataGenerator(fabricDataGenerator: FabricDataGenerator) {
		val pack = fabricDataGenerator.createPack()

		pack.addProvider(::RecipeGenerator)
		pack.addProvider(::LanguageGenerator)
	}

	private class RecipeGenerator(generator: FabricDataOutput) : FabricRecipeProvider(generator) {
		override fun generate(exporter: Consumer<RecipeJsonProvider>) {
			ShapelessRecipeJsonBuilder.create(RecipeCategory.DECORATIONS, Items.GUNPOWDER_CHIPS, 9)
				.input(net.minecraft.item.Items.GUNPOWDER)
				.criterion(hasItem(Items.GUNPOWDER_CHIPS),
					conditionsFromItem(Items.GUNPOWDER_CHIPS))
				.criterion(hasItem(net.minecraft.item.Items.GUNPOWDER),
					conditionsFromItem(net.minecraft.item.Items.GUNPOWDER))
				.offerTo(exporter);

			ShapelessRecipeJsonBuilder.create(RecipeCategory.DECORATIONS, net.minecraft.item.Items.GUNPOWDER)
				.input(Items.GUNPOWDER_CHIPS, 9)
				.criterion(hasItem(net.minecraft.item.Items.GUNPOWDER),
					conditionsFromItem(net.minecraft.item.Items.GUNPOWDER))
				.criterion(hasItem(Items.GUNPOWDER_CHIPS),
					conditionsFromItem(Items.GUNPOWDER_CHIPS))
				.offerTo(exporter);
		}
	}

	private class LanguageGenerator(val generator: FabricDataOutput) : FabricLanguageProvider(generator, "zh_cn") {
		override fun generateTranslations(translationBuilder: TranslationBuilder) {
			translationBuilder.add(Items.GUNPOWDER_CHIPS, "火药屑")
		}
	}
}

