package com.togetherweareone.fx.extensions;

import javafx.scene.Scene;
import org.pf4j.ExtensionPoint;

public interface CreateProjectExtensionPoint extends ExtensionPoint {

    void loadCreateProjectModifications(Scene scene);
}
