// fuck you rat go brrrrrrrr
package risingtide.tidehack.mixininterface;

import risingtide.tidehack.utils.misc.Vec4;
import net.minecraft.util.math.Vec3d;

public interface IMatrix4f {
    void multiplyMatrix(Vec4 v, Vec4 out);

    Vec3d mul(Vec3d vec);
}
