package com.togetherweareone.fx.extensions;

import javafx.scene.Scene;
import org.pf4j.ExtensionPoint;

public interface CreateChecklistExtensionPoint extends ExtensionPoint {

    void loadCreateChecklistModifications(Scene scene);

}
