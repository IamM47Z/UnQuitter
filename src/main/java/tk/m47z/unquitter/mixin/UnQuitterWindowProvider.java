package tk.m47z.unquitter.mixin;

import tk.m47z.unquitter.Main;

import net.minecraft.client.util.Window;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import static org.lwjgl.glfw.GLFW.*;

@Mixin( net.minecraft.client.util.WindowProvider.class )
public abstract
class UnQuitterWindowProvider
{
	@Inject( method = "createWindow", at = @At( "TAIL" ) )
	private
	void createWindowTail( CallbackInfoReturnable< Window > cir )
	{
		if (cir.getReturnValue() == null || cir.getReturnValue().getHandle() == 0)
			return;
		
		long hwnd_id = cir.getReturnValue().getHandle();
		
		Main.LOGGER.info( "UnQuitter: patching window " + hwnd_id );
		
		glfwSetWindowCloseCallback( hwnd_id, (hwnd) ->
		{
			Main.LOGGER.info( "UnQuitter: window close callback called" );
			
			glfwSetWindowShouldClose( hwnd, false );
		});
		
		UnQuitterMacWindowUtil.getCocoaWindow( hwnd_id ).ifPresent( (hwnd) ->
		{
			Long style_mask = ( Long ) hwnd.send( "styleMask" );
			Main.LOGGER.info( "UnQuitter: styleMask: " + style_mask );
			
			hwnd.send( "setStyleMask:",style_mask - 4 );
			
			style_mask = ( Long ) hwnd.send( "styleMask" );
			Main.LOGGER.info( "UnQuitter: styleMask: " + style_mask );
		});
	}
}
