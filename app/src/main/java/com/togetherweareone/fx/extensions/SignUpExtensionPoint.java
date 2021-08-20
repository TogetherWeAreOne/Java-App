package com.togetherweareone.fx.extensions;

import javafx.scene.Scene;
import org.pf4j.ExtensionPoint;

public interface SignUpExtensionPoint extends ExtensionPoint {

    void loadSignUpModifications(Scene scene);
}
