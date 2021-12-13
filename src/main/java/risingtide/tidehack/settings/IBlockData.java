// fuck you rat go brrrrrrrr
package risingtide.tidehack.settings;

import risingtide.tidehack.gui.GuiTheme;
import risingtide.tidehack.gui.WidgetScreen;
import risingtide.tidehack.utils.misc.IChangeable;
import risingtide.tidehack.utils.misc.ICopyable;
import risingtide.tidehack.utils.misc.ISerializable;
import net.minecraft.block.Block;

public interface IBlockData<T extends ICopyable<T> & ISerializable<T> & IChangeable & IBlockData<T>> {
    WidgetScreen createScreen(GuiTheme theme, Block block, BlockDataSetting<T> setting);
}
