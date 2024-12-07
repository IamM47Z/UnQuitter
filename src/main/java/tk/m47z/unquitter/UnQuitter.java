package tk.m47z.unquitter;

import tk.m47z.unquitter.mixin.MacWindowUtil;

import ca.weblite.objc.Proxy;

import net.minecraft.client.MinecraftClient;

import java.util.Objects;
import java.util.NoSuchElementException;

public
class UnQuitter
{
	private static       boolean paused = true;
	private static final Proxy   app    = ca.weblite.objc.Client.getInstance().sendProxy("NSApplication", "sharedApplication");
	
	private static final long default_opts = ( long ) app.send( "presentationOptions" );
	
	private static
	long getPresentationOptions( )
	{
		return ( long ) app.send( "presentationOptions" );
	}
	
	private static
	void setPresentationOptions( long opts )
	{
		app.send( "setPresentationOptions:", opts );
		Client.LOGGER.info("Set presentation options: " + app.send("presentationOptions"));
	}
	
	private static
	long getStyleMask( long hwnd_id ) throws NoSuchElementException
	{
		return ( long ) MacWindowUtil.getCocoaWindow(hwnd_id).orElseThrow().send("styleMask");
	}
	
	private static
	void setStyleMask( long hwnd_id, long style_mask ) throws NoSuchElementException
	{
		MacWindowUtil.getCocoaWindow(hwnd_id).orElseThrow().send("setStyleMask:", style_mask);
	}
	
	public static
	boolean isPaused( )
	{
		return paused;
	}
	
	public static
	void onPause( )
	{
		if ( isPaused( ) )
			return;
		
		paused = true;
		Client.LOGGER.info("UnQuitter: onPause");
		
		long opts = getPresentationOptions( );
		if ( !Objects.equals( opts, default_opts ) )
			setPresentationOptions( default_opts );
		
		long style_mask = getStyleMask( MinecraftClient.getInstance( ).getWindow( ).getHandle( ) );
		
		// ignore if the button is present
		if ( ( style_mask & 4 ) != 0 )
			return;
		
		setStyleMask( MinecraftClient.getInstance( ).getWindow( ).getHandle( ), style_mask + 4 );
	}
	
	public static
	void onResume( )
	{
		if ( !isPaused( ) )
			return;
		
		paused = false;
		Client.LOGGER.info("UnQuitter: onResume");
		
		long opts = getPresentationOptions( );
		if ( Objects.equals( opts, default_opts ) )
			setPresentationOptions( 1 + 4 + 2048 + 16 + 64 + 256 + 32 + 128 );
		
		long style_mask = getStyleMask( MinecraftClient.getInstance( ).getWindow( ).getHandle( ) );
		
		// ignore if the button is removed
		if ( ( style_mask & 4 ) == 0 )
			return;
		
		setStyleMask( MinecraftClient.getInstance( ).getWindow( ).getHandle( ), style_mask - 4 );
	}
}
