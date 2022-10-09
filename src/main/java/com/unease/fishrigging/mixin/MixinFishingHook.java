package com.unease.fishrigging.mixin;

import java.util.Random;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import net.minecraft.world.entity.projectile.FishingHook;

@Mixin(FishingHook.class)
public abstract class MixinFishingHook {
	
	@Shadow
	private int lureSpeed;
	
	@Redirect(method = "catchingFish()V", at = @At(value = "INVOKE", target = "Ljava/util/Random;nextFloat()F", ordinal = 0))
	private float redirectRainCheck(Random random) {
		return 0; // Makes the float less than 0.25 to always increment certain values
	}
	
	@Redirect(method = "catchingFish()V", at = @At(value = "INVOKE", target = "Ljava/util/Random;nextFloat()F", ordinal = 1))
	private float redirectOpenSkyCheck(Random random) {
		return 1.0f; // Is always greater than 0.5F to prevent decrements in values
	}
	
	@Redirect(method = "catchingFish()V", at = @At(value = "INVOKE", target = "Lnet/minecraft/util/Mth;nextInt(Ljava/util/Random;II)I", ordinal = 1))
	private int redirectFastestFishAppear(Random random, int minimum, int maximum) {
		return 20; // The time it takes from when a item appears to when it goes to your rod can vary between 1 and 4 seconds so here we force it to be one second.
	}
	
	/**
	 * Okay so the minimum time it takes for a item to appear on screen can range between 5 and 30 seconds without lure.
	 * The final return statement will return 100 ticks or 5 seconds to force a minimum wait without lure.
	 * How lure works is that it takes the level of your lure enchantment and then multiplies it by 20 (to get the number in ticks), then by 5 (to add 5 more seconds).
	 * Whatever the difference is from your initial wait is now your new wait.
	 * 
	 * This how it looks: 
	 * 
	 * this.timeUntilLured = Mth.nextInt(this.random, 100, 600);
	 * this.timeUntilLured -= this.lureSpeed * 20 * 5;
	 * 
	 * This poses a problem because lets say you get a perfect 100 tick wait, so when you have for example lure 1, you get a tick delay of 0 ticks.
	 * If this happens you will be forced to wait until the next tick for a new RNG number to determine your next wait time.
	 * This also happens if your tick wait delay goes into the negatives.
	 * To fix this, we can add an extra tick to however many ticks we get if we see that we have lure.
	 * 
	 * For example, if you have lure 1 and get a 101 tick wait, you can still have a perfect 1 tick wait for the fish.
	 */
	@Redirect(method = "catchingFish()V", at = @At(value = "INVOKE", target = "Lnet/minecraft/util/Mth;nextInt(Ljava/util/Random;II)I", ordinal = 2))
	private int redirectFastestFishWait(Random random, int minimum, int maximum) {
		if (lureSpeed >= 1) {
			return lureSpeed * 20 * 5 + 1;
		}
		
		return 100;
	}
}
