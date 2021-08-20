package com.togetherweareone.fx.extensions;

import javafx.scene.Scene;
import org.pf4j.ExtensionPoint;

public interface EditProjectExtensionPoint extends ExtensionPoint {

    void loadEditProjectModifications(Scene scene);
}
