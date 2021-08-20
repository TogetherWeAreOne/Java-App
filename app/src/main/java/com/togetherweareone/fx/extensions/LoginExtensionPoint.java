package com.togetherweareone.fx.extensions;

import javafx.scene.Scene;
import org.pf4j.ExtensionPoint;

public interface LoginExtensionPoint extends ExtensionPoint {

    void loadLoginModifications(Scene scene);
}
