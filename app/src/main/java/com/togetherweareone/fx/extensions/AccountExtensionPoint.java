package com.togetherweareone.fx.extensions;

import javafx.scene.Scene;
import org.pf4j.ExtensionPoint;

public interface AccountExtensionPoint extends ExtensionPoint {

    void loadAccountModifications(Scene scene);

}
