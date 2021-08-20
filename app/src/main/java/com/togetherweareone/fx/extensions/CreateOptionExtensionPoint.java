package com.togetherweareone.fx.extensions;

import javafx.scene.Scene;
import org.pf4j.ExtensionPoint;

public interface CreateOptionExtensionPoint extends ExtensionPoint {

    void loadCreateOptionModifications(Scene scene);
}
