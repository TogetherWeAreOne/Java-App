package com.togetherweareone.fx.extensions;

import javafx.scene.Scene;
import org.pf4j.ExtensionPoint;

public interface TaskViewExtensionPoint extends ExtensionPoint {

    void loadTaskViewModifications(Scene scene);
}
