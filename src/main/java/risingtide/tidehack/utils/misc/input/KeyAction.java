// fuck you rat go brrrrrrrr
package risingtide.tidehack.utils.misc.input;

import org.lwjgl.glfw.GLFW;

public enum KeyAction {
    Press,
    Repeat,
    Release;

    public static KeyAction get(int action) {
        if (action == GLFW.GLFW_PRESS) return Press;
        else if (action == GLFW.GLFW_RELEASE) return Release;
        else return Repeat;
    }
}
