package com.togetherweareone.fx.extensions;

import javafx.scene.Scene;
import org.pf4j.ExtensionPoint;

public interface CreateTaskExtensionPoint extends ExtensionPoint {

    void loadCreateTaskModifications(Scene scene);
}
