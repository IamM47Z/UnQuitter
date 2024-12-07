package tk.m47z.unquitter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.fabricmc.api.ClientModInitializer;

public
class Client extends UnQuitter implements ClientModInitializer
{
	public static final Logger LOGGER = LoggerFactory.getLogger( "unquitter" );
	
	@Override
	public
	void onInitializeClient( )
	{
		LOGGER.info( "Initializing UnQuitter" );
	}
}
