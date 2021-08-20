package com.togetherweareone.fx.extensions;

import javafx.scene.Scene;
import org.pf4j.ExtensionPoint;

public interface EditChecklistExtensionPoint extends ExtensionPoint {

    void loadEditChecklistModifications(Scene scene);
}
