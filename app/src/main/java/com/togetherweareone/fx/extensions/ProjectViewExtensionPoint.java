package com.togetherweareone.fx.extensions;

import javafx.scene.Scene;
import org.pf4j.ExtensionPoint;

public interface ProjectViewExtensionPoint extends ExtensionPoint {

    void loadProjectViewModifications(Scene scene);
}
