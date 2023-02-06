package tk.m47z.unquitter;

import ca.weblite.objc.Client;
import ca.weblite.objc.Proxy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.fabricmc.api.ModInitializer;

public
class Main implements ModInitializer
{
	public static final Logger LOGGER = LoggerFactory.getLogger( "unquitter" );
	
	public static final Long original_opts = ( Long ) Client.getInstance( ).sendProxy( "NSApplication",
	                                                                                   "sharedApplication" ).send(
			"presentationOptions" );
	
	@Override
	public
	void onInitialize( )
	{
		LOGGER.info( "Initializing UnQuitter" );
		LOGGER.info( "Original presentation options: " + original_opts );
	}
}
