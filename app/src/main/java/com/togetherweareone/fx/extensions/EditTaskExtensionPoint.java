package com.togetherweareone.fx.extensions;

import javafx.scene.Scene;
import org.pf4j.ExtensionPoint;

public interface EditTaskExtensionPoint extends ExtensionPoint {

    void loadEditTaskModifications(Scene scene);
}
