package com.unease.fishrigging;

import org.apache.logging.log4j.LogManager;

import net.fabricmc.api.ModInitializer;

public class FishRigging implements ModInitializer {
	
	public static final org.apache.logging.log4j.Logger LOGGER = LogManager.getFormatterLogger("FishRigging");

	@Override
	public void onInitialize() {
		
		LOGGER.info("FishRigging-1.16.1 is initalized correctly!");
	}
}
