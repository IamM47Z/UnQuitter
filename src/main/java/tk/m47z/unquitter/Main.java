package tk.m47z.unquitter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.fabricmc.api.ModInitializer;

public
class Main extends UnQuitter implements ModInitializer
{
	public static final Logger LOGGER = LoggerFactory.getLogger( "unquitter" );
	
	@Override
	public
	void onInitialize( )
	{
		LOGGER.info( "Initializing UnQuitter" );
	}
}
