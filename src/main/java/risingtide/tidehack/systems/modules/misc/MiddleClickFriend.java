// fuck you rat go brrrrrrrr
package risingtide.tidehack.systems.modules.misc;

import risingtide.tidehack.events.meteor.MouseButtonEvent;
import risingtide.tidehack.settings.BoolSetting;
import risingtide.tidehack.settings.Setting;
import risingtide.tidehack.settings.SettingGroup;
import risingtide.tidehack.systems.friends.Friend;
import risingtide.tidehack.systems.friends.Friends;
import risingtide.tidehack.systems.modules.Categories;
import risingtide.tidehack.systems.modules.Module;
import risingtide.tidehack.utils.misc.input.KeyAction;
import meteordevelopment.orbit.EventHandler;
import net.minecraft.entity.player.PlayerEntity;

import static org.lwjgl.glfw.GLFW.GLFW_MOUSE_BUTTON_MIDDLE;

public class MiddleClickFriend extends Module {

    private final SettingGroup sgGeneral = settings.getDefaultGroup();

    private final Setting<Boolean> message = sgGeneral.add(new BoolSetting.Builder()
            .name("message")
            .description("Sends a message to the player when you add them as a friend.")
            .defaultValue(false)
            .build()
    );

    public MiddleClickFriend() {
        super(Categories.Misc, "middle-click-friend", "Adds or removes a player as a friend via middle click.");
    }

    @EventHandler
    private void onMouseButton(MouseButtonEvent event) {
        if (event.action == KeyAction.Press && event.button == GLFW_MOUSE_BUTTON_MIDDLE && mc.currentScreen == null && mc.targetedEntity != null && mc.targetedEntity instanceof PlayerEntity) {
            if (!Friends.get().isFriend((PlayerEntity) mc.targetedEntity)) {
                Friends.get().add(new Friend((PlayerEntity) mc.targetedEntity));
                if (message.get()) mc.player.sendChatMessage("/msg " + mc.targetedEntity.getEntityName() + " I just friended you on Meteor.");
            } else {
                Friends.get().remove(Friends.get().get((PlayerEntity) mc.targetedEntity));
            }
        }
    }
}
