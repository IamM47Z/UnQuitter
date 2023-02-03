package tk.m47z.unquitter;

import ca.weblite.objc.Client;
import ca.weblite.objc.Proxy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.fabricmc.api.ModInitializer;

public class Main implements ModInitializer {
	public static final Logger LOGGER = LoggerFactory.getLogger("unquitter");
	
	@Override
	public void onInitialize() {
		LOGGER.info("Initializing UnQuitter");
		
		Proxy app = Client.getInstance( ).sendProxy( "NSApplication", "sharedApplication" );
		
		Long  opts = ( Long ) app.send( "presentationOptions" );
		Main.LOGGER.info( "UnQuitter: presentationOptions: " + opts );
		
		app.send( "setPresentationOptions:", 1 + 4 + 16 + 64 + 128 + 256 + 512 + 2048 );
		
		opts = ( Long ) app.send( "presentationOptions" );
		Main.LOGGER.info( "UnQuitter: presentationOptions: " + opts );
		
		// todo: disable cmd + space and propagate cmd key to game (propagation will most-likely be the only fix needed ( using NSWindow event handlers perhaps ))
	}
}
