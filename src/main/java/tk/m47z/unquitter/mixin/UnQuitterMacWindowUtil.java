package tk.m47z.unquitter.mixin;

import ca.weblite.objc.NSObject;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

import java.util.Optional;

@Mixin( net.minecraft.client.util.MacWindowUtil.class )
public
interface UnQuitterMacWindowUtil
{
	@Invoker( "getCocoaWindow" )
	static
	Optional< NSObject > getCocoaWindow( long handle )
	{
		throw new AssertionError( "UnQuitter: failed to invoke getCocoaWindow" );
	}
}
